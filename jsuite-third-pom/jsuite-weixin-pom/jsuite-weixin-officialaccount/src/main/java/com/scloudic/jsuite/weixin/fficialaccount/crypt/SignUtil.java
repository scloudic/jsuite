package com.scloudic.jsuite.weixin.fficialaccount.crypt;

import com.scloudic.jsuite.weixin.core.crypt.PKCS7Encoder;
import com.scloudic.jsuite.weixin.core.exception.CryptException;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Arrays;

public class SignUtil {
    private static Logger logger = LoggerFactory.getLogger(SignUtil.class);
    private static Charset CHARSET = Charset.forName("utf-8");

    public static String getSHA1(String token, String timestamp, String nonce) {
        try {
            String[] array = new String[]{token, timestamp, nonce};
            StringBuffer sb = new StringBuffer();
            // 字符串排序
            Arrays.sort(array);
            for (int i = 0; i < 3; i++) {
                sb.append(array[i]);
            }
            String str = sb.toString();
            // SHA1签名生成
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();

            StringBuffer hexstr = new StringBuffer();
            String shaHex = "";
            for (int i = 0; i < digest.length; i++) {
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexstr.append(0);
                }
                hexstr.append(shaHex);
            }
            return hexstr.toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CryptException(CryptException.ComputeSignatureError);
        }
    }

    /**
     * aes
     *
     * @param encodingAESKey
     * @param content
     */
    public static String[] aesDecrypt(String encodingAESKey, String content) {
        try {
            // 设置解密模式为AES的CBC模式
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            byte[] aesKey = Base64.decodeBase64(encodingAESKey);
            SecretKeySpec key_spec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
            cipher.init(Cipher.DECRYPT_MODE, key_spec, iv);
            // 使用BASE64对密文进行解码
            byte[] encrypted = Base64.decodeBase64(content);
            // 解密
            byte[] original = cipher.doFinal(encrypted);
            byte[] bytes = PKCS7Encoder.decode(original);
            // 分离16位随机字符串,网络字节序和AppId
            byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);
            int xmlLength = recoverNetworkBytesOrder(networkOrder);
            String xmlContent = new String(Arrays.copyOfRange(bytes, 20, 20 + xmlLength), CHARSET);
            String fromAppId = new String(Arrays.copyOfRange(bytes, 20 + xmlLength, bytes.length),
                    CHARSET);
            String[] result = new String[]{fromAppId, xmlContent};
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CryptException(CryptException.DecryptAESError);
        }
    }

    private static int recoverNetworkBytesOrder(byte[] orderBytes) {
        int sourceNumber = 0;
        for (int i = 0; i < 4; i++) {
            sourceNumber <<= 8;
            sourceNumber |= orderBytes[i] & 0xff;
        }
        return sourceNumber;
    }
}

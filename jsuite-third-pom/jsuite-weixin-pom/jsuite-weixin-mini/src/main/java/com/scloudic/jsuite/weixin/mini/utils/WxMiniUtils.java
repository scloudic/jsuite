package com.scloudic.jsuite.weixin.mini.utils;

import com.alibaba.fastjson.JSONObject;
import com.scloudic.jsuite.weixin.mini.model.UserInfo;
import com.scloudic.rabbitframework.core.utils.Base64Utils;
import com.scloudic.rabbitframework.core.utils.JsonUtils;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;

public class WxMiniUtils {
    private static final Logger logger = LoggerFactory.getLogger(WxMiniUtils.class);

    /**
     * 获取用户信息
     *
     * @param encryptedData
     * @param sessionKey
     * @param iv
     * @return
     * @throws Exception
     */
    public static UserInfo getUserInfo(String encryptedData, String sessionKey, String iv) {
        String result = parserUserInfo(encryptedData, sessionKey, iv);
        if (StringUtils.isBlank(result)) {
            throw new NullPointerException("获取微信用户信息为空!");
        }
        return JsonUtils.getObject(result, UserInfo.class);
    }

    public static String getOldVersionPhone(String encryptedData, String sessionKey, String iv) {
        String result = parserUserInfo(encryptedData, sessionKey, iv);
        if (StringUtils.isBlank(result)) {
            return "";
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject.getString("purePhoneNumber");
    }

    private static String parserUserInfo(String encryptedData, String sessionKey, String iv) {
        String result = "";
        // 被加密的数据
        byte[] dataByte = Base64Utils.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64Utils.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64Utils.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
            int base = 16;
            int keylength = keyByte.length;
            if (keylength != base) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                result = new String(resultByte, "UTF-8");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
        return result;
    }
}

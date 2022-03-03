package com.scloudic.jsuite.weixin.pay.utils;

import com.scloudic.jsuite.weixin.core.exception.WeiXinException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AesUtils {
    private static final Logger logger = LoggerFactory.getLogger(AesUtils.class);
    private static final int TAG_LENGTH_BIT = 128;

    public static String v3DecryptToStr(String aesKey, String associatedData,
                                        String nonce, String ciphertext) {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            SecretKeySpec key = new SecretKeySpec(aesKey.getBytes(StandardCharsets.UTF_8), "AES");
            GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH_BIT, nonce.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, key, spec);
            cipher.updateAAD(associatedData.getBytes(StandardCharsets.UTF_8));
            return new String(cipher.doFinal(Base64.getDecoder().decode(ciphertext)), "utf-8");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new WeiXinException("aes解密报错");
        }
    }
}
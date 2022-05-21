package com.scloudic.jsuite.weixin.pay.utils;

import java.security.SecureRandom;

public class PayUtils {
    //支付V3版本认证类型
    public static final String V3SCHEMA = "WECHATPAY2-SHA256-RSA2048";
    protected static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    protected static final SecureRandom RANDOM = new SecureRandom();

    public static String generateNonceStr() {
        char[] nonceChars = new char[32];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }


    public static long generateTimestamp() {
        return System.currentTimeMillis() / 1000;
    }
}

package com.scloudic.jsuite.weixin.pay.v3.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EncryptCertificate {
    private String algorithm;
    private String associated_data;
    private String ciphertext;
    private String nonce;
}

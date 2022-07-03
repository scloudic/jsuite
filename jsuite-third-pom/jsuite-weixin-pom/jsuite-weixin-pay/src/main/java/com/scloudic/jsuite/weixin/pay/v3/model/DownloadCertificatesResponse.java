package com.scloudic.jsuite.weixin.pay.v3.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class DownloadCertificatesResponse {
    private Date effective_time;
    private EncryptCertificate encrypt_certificate;
    private Date expire_time;
    private String serial_no;
}

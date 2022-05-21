package com.scloudic.jsuite.weixin.pay.v3.model;

import java.util.Date;

public class DownloadCertificatesResponse {
    private Date effective_time;
    private EncryptCertificate encrypt_certificate;
    private Date expire_time;
    private String serial_no;

    public Date getEffective_time() {
        return effective_time;
    }

    public void setEffective_time(Date effective_time) {
        this.effective_time = effective_time;
    }


    public EncryptCertificate getEncrypt_certificate() {
        return encrypt_certificate;
    }

    public void setEncrypt_certificate(EncryptCertificate encrypt_certificate) {
        this.encrypt_certificate = encrypt_certificate;
    }

    public Date getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(Date expire_time) {
        this.expire_time = expire_time;
    }

    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }
}

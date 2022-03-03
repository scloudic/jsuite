package com.scloudic.jsuite.weixin.pay.utils;

public class WeiXinEnums {
    public enum WeiXinPayStatus {
        SUCCESS(1, "成功"),
        CERTIFICATE_ERROR(2, "证书下载失败"),
        VERIFY_ERROR(3, "验证失败"),
        FAIL(4, "请求失败"),
        ERROR(5, "系统报错"),
        ;
        private int status;
        private String message;

        WeiXinPayStatus(int status, String message) {
            this.status = status;
            this.message = message;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "WeiXinPayStatus{" +
                    "status=" + status +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}

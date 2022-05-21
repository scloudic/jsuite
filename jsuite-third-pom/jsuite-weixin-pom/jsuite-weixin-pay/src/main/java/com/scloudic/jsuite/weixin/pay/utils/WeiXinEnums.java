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

    public enum RelationType {
        STORE("STORE", "门店"),
        STAFF("STAFF", "员工"),
        STORE_OWNER("STORE_OWNER", "店主"),
        PARTNER("PARTNER", "合作伙伴"),
        HEADQUARTER("HEADQUARTER", "总部"),
        BRAND("BRAND", "品牌方"),
        DISTRIBUTOR("DISTRIBUTOR", "分销商"),
        USER("USER", "用户"),
        SUPPLIER("SUPPLIER", "供应商"),
        CUSTOM("CUSTOM", "自定义"),
        ;
        private String value;
        
        private String message;

        RelationType(String value, String message) {
            this.value = value;
            this.message = message;
        }

        public String getValue() {
            return value;
        }

        public String getMessage() {
            return message;
        }
    }
}

package com.scloudic.jsuite.core.utils;

public class Enums {
    public enum DelStatus {
        NORMAL(1, "正常"),
        DEL(2, "删除");
        private int value;
        private String message;

        DelStatus(int value, String message) {
            this.value = value;
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public int getValue() {
            return value;
        }
    }

    public enum ActiveStatus {
        OPEN(1, "启用"),
        STOP(2, "停用");
        private int value;
        private String message;

        ActiveStatus(int value, String message) {
            this.value = value;
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public int getValue() {
            return value;
        }

        public static ActiveStatus getActiveStatus(Integer value) {
            ActiveStatus resultStatus = null;
            if (value == null) {
                return resultStatus;
            }

            ActiveStatus[] activeStatus = ActiveStatus.values();
            for (ActiveStatus status : activeStatus) {
                int aStatus = status.getValue();
                if (aStatus == value.intValue()) {
                    resultStatus = status;
                    break;
                }
            }
            return resultStatus;
        }
    }

    public enum Target {
        CURRRPAGE(1, "当前页"),
        NEWPAGE(2, "新页面"),
        MODELPAG(3, "模式对话框");
        private int value;
        private String message;

        Target(int value, String message) {
            this.value = value;
            this.message = message;
        }

        public int getValue() {
            return value;
        }

        public String getMessage() {
            return message;
        }
    }

    public enum BtnFlag {
        BUTTON(1, "按钮"), MENU(2, "菜单");
        private int value;
        private String message;

        BtnFlag(int value, String message) {
            this.message = message;
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public String getMessage() {
            return message;
        }
    }

    public enum UserSource {
        SYS(1, "系统创建"), WXMIN(2, "小程序"),
        PC(3, "PC端"), APP(4, "APP");
        private int value;
        private String message;

        UserSource(int value, String message) {
            this.message = message;
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public String getMessage() {
            return message;
        }
    }

    public enum Status {
        YES(1, "是"), NO(2, "否");
        private int value;
        private String message;

        Status(int value, String message) {
            this.message = message;
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public String getMessage() {
            return message;
        }
    }
}

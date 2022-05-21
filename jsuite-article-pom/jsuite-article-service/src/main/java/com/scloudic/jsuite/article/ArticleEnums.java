package com.scloudic.jsuite.article;

public class ArticleEnums {
    public enum ArticleStatus {
        PUBLISH(1, "已发布"),
        DRAFT(2, "草稿");
        private int value;
        private String message;

        ArticleStatus(int value, String message) {
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
}

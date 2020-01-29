package com.zqj.blog.entity.bo;

public enum ArticleStatus {

    PUBLISHED("P"),
    DRAFT("D"),
    REMOVED("R");

    private String code;

    ArticleStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

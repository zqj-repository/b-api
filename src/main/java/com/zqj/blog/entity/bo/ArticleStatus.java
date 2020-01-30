package com.zqj.blog.entity.bo;

public enum ArticleStatus {

    PUBLISHED("P", "已发布"),
    DRAFT("D", "草稿"),
    REMOVED("R", "已移除");

    private String code;
    private String name;

    ArticleStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ArticleStatus fromCode(String code) {
        for (ArticleStatus status: ArticleStatus.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}

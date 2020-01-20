package com.zqj.blog.entity;

import java.util.Date;

public class Comment {
    private Integer id;

    private Integer user;

    private Integer article;

    private Integer parentComment;

    private String content;

    private Date createdWhen;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getArticle() {
        return article;
    }

    public void setArticle(Integer article) {
        this.article = article;
    }

    public Integer getParentComment() {
        return parentComment;
    }

    public void setParentComment(Integer parentComment) {
        this.parentComment = parentComment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreatedWhen() {
        return createdWhen;
    }

    public void setCreatedWhen(Date createdWhen) {
        this.createdWhen = createdWhen;
    }
}
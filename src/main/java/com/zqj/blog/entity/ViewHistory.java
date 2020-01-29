package com.zqj.blog.entity;

import java.util.Date;

public class ViewHistory {
    private Integer id;

    private Integer user;

    private Integer article;

    private Date viewWhen;

    private String countRecord;

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

    public Date getViewWhen() {
        return viewWhen;
    }

    public void setViewWhen(Date viewWhen) {
        this.viewWhen = viewWhen;
    }

    public String getCountRecord() {
        return countRecord;
    }

    public void setCountRecord(String countRecord) {
        this.countRecord = countRecord == null ? null : countRecord.trim();
    }
}
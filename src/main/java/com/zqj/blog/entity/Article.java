package com.zqj.blog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Article {
    private Integer id;

    private String title;

    private Integer categoryId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdWhen;

    private Date modifiedWhen;

    private String text;
}
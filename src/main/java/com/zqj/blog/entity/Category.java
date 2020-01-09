package com.zqj.blog.entity;

import lombok.Data;

@Data
public class Category {
    private Integer id;

    private String name;

    private String description;

    private String defaultCategory;
}
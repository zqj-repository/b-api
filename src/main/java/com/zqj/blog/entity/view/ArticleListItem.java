package com.zqj.blog.entity.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zqj.blog.entity.Article;
import com.zqj.blog.entity.Category;
import lombok.Data;

import java.util.Date;

@Data
public class ArticleListItem {

    private Article article;

    private Category category;

}

package com.zqj.blog.entity.view;

import com.zqj.blog.entity.Article;
import com.zqj.blog.entity.Category;
import lombok.Data;

@Data
public class ArticleListItem {

    private Article article;

    private Category category;

}

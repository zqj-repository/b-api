package com.zqj.blog.service;

import com.zqj.blog.dao.ArticleMapper;
import com.zqj.blog.entity.Article;
import com.zqj.blog.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ArticleService {

    private ArticleMapper articleMapper;

    private CategoryService categoryService;

    @Autowired
    public ArticleService(ArticleMapper articleMapper, CategoryService categoryService) {
        this.articleMapper = articleMapper;
    }

    @Transactional
    public void publishArticle(Article article) {
        article.setId(null);
        article.setCreatedWhen(new Date());
        article.setModifiedWhen(null);
        articleMapper.insert(article);
    }

    @Transactional
    public Article getArticle(Integer id) {
        return articleMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public List<Article> getArticles() {

    }

}

package com.zqj.blog.service;

import com.zqj.blog.dao.ArticleMapper;
import com.zqj.blog.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class ArticleService {

    private ArticleMapper articleMapper;

    @Autowired
    public ArticleService(ArticleMapper articleMapper) {
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

}

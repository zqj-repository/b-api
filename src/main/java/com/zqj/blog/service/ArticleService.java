package com.zqj.blog.service;

import com.zqj.blog.dao.ArticleMapper;
import com.zqj.blog.entity.Article;
import com.zqj.blog.entity.Category;
import com.zqj.blog.entity.view.ArticleListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ArticleService {

    private ArticleMapper articleMapper;

    private CategoryService categoryService;

    @Autowired
    public ArticleService(ArticleMapper articleMapper, CategoryService categoryService) {
        this.articleMapper = articleMapper;
        this.categoryService = categoryService;
    }

    @Transactional
    public void createArticle(Article article) {
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
    public List<ArticleListItem> getArticles() {
        List<Article> articles = articleMapper.selectAll();
        List<Category> categories = categoryService.getCategories();
        List<ArticleListItem> articleListItems = new ArrayList<>();
        for (Article article: articles) {
            ArticleListItem articleListItem = new ArticleListItem();
            for (Category category: categories) {
                if (category.getId().equals(article.getCategoryId())) {
                    articleListItem.setArticle(article);
                    articleListItem.setCategory(category);
                    articleListItems.add(articleListItem);
                    break;
                }
            }
        }
        return articleListItems;
    }

    @Transactional
    public void updateArticle(Article article) {
        articleMapper.updateByPrimaryKey(article);
    }

    @Transactional
    public void deleteArticle(Integer id) {
        articleMapper.deleteByPrimaryKey(id);
    }

}

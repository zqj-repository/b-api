package com.zqj.blog.service;

import com.zqj.blog.dao.*;
import com.zqj.blog.entity.Article;
import com.zqj.blog.entity.Category;
import com.zqj.blog.entity.User;
import com.zqj.blog.entity.bo.ArticleFormBO;
import com.zqj.blog.entity.bo.ArticleStatus;
import com.zqj.blog.entity.vo.AdminArticleListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ViewHistoryMapper viewHistoryMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Transactional
    public void createArticle(ArticleFormBO articleFormBO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        User poUser = userMapper.selectByUserName(user.getUsername());
        Category category = categoryMapper.selectByPrimaryKey(articleFormBO.getCategory());
        Article article = articleFormBO.toPO();
        article.setUser(poUser);
        article.setId(null);
        article.setCreatedWhen(new Date());
        article.setCategory(category);
        if (ArticleStatus.PUBLISHED.getCode().equals(article.getStatus())) {
            article.setPublishTime(new Date());
        }
        articleMapper.insert(article);
    }

    @Transactional
    public void updateArticle(ArticleFormBO articleFormBO) {
        Article article = articleFormBO.toPO();
        Category category = categoryMapper.selectByPrimaryKey(articleFormBO.getCategory());
        article.setCategory(category);
        article.setLastModify(new Date());

        if (ArticleStatus.PUBLISHED.getCode().equals(article.getStatus())) {
            article.setPublishTime(new Date());
        }
        articleMapper.updateByPrimaryKeySelective(article);
    }

    @Transactional
    public void removeArticle(Integer id) {
        Article article = articleMapper.selectByPrimaryKey(id);
        article.setStatus(ArticleStatus.REMOVED.getCode());
        articleMapper.updateByPrimaryKeySelective(article);
    }

    @Transactional
    public Article getArticle(Integer id) {
        return articleMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public List<AdminArticleListItem> getAllArticle() {
        List<Article> articles = articleMapper.selectAllArticles();
        List<AdminArticleListItem> adminArticleList = new ArrayList<>();
        for (Article article: articles) {
            Integer viewCount = viewHistoryMapper.selectCountOfArticleViews(article.getId());
            Integer commentCount = commentMapper.selectCountOfArticleComment(article.getId());
            AdminArticleListItem adminArticleListItem = AdminArticleListItem.builder()
                    .id(article.getId())
                    .title(article.getTitle())
                    .categoryName(article.getCategory().getName())
                    .viewCount(viewCount)
                    .commentCount(commentCount)
                    .lastModify(article.getLastModify())
                    .publishTime(article.getPublishTime())
                    .build();
            adminArticleList.add(adminArticleListItem);
        }
        return adminArticleList;
    }

}

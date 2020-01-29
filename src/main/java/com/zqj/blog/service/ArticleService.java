package com.zqj.blog.service;

import com.zqj.blog.dao.ArticleMapper;
import com.zqj.blog.dao.UserMapper;
import com.zqj.blog.entity.Article;
import com.zqj.blog.entity.User;
import com.zqj.blog.entity.bo.ArticleStatus;
import com.zqj.blog.entity.vo.AdminArticleListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void createArticle(Article article) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        User poUser = userMapper.selectByUserName(user.getUsername());
        article.setUser(poUser.getId());
        article.setId(null);
        article.setCreatedWhen(new Date());
        if (ArticleStatus.PUBLISHED.getCode().equals(article.getStatus())) {
            article.setPublishTime(new Date());
        }
        articleMapper.insert(article);
    }

    @Transactional
    public void updateArticle(Article article) {
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

    public Article getArticle(Integer id) {
        return articleMapper.selectByPrimaryKey(id);
    }

    public List<AdminArticleListItem> getAllArticle() {
        return null;
    }
}

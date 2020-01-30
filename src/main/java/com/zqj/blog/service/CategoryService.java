package com.zqj.blog.service;

import com.zqj.blog.dao.ArticleMapper;
import com.zqj.blog.dao.CategoryMapper;
import com.zqj.blog.entity.Article;
import com.zqj.blog.entity.Category;
import com.zqj.blog.entity.bo.CategoryConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Transactional
    public void createCategory(Category category) {
        category.setId(null);
        categoryMapper.insert(category);
    }

    @Transactional
    public void updateCategory(Category category) {
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Transactional
    public void deleteCategory(Integer id) {
        if (CategoryConstants.DEFAULT_ID == id) {
            throw new RuntimeException("默认分类不能删除");
        }
        List<Article> articles = articleMapper.selectByCategoryId(id);
        if (articles != null && articles.size() > 0) {
            for (Article article: articles) {
                article.setCategory(CategoryConstants.DEFAULT_ID);
                articleMapper.updateByPrimaryKeySelective(article);
            }
        }
        categoryMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public Category getCategory(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public List<Category> getAllCategory() {
        return categoryMapper.selectAllCategory();
    }
}

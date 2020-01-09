package com.zqj.blog.service;

import com.zqj.blog.dao.ArticleMapper;
import com.zqj.blog.dao.CategoryMapper;
import com.zqj.blog.entity.Article;
import com.zqj.blog.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private CategoryMapper categoryMapper;

    private ArticleMapper articleMapper;

    @Autowired
    public CategoryService(CategoryMapper categoryMapper, ArticleMapper articleMapper) {
        this.categoryMapper = categoryMapper;
        this.articleMapper = articleMapper;
    }

    public void createCategory(Category category) {
        category.setId(null);
        categoryMapper.insert(category);
    }

    public Category getCategory(int id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    public List<Category> getCategories() {
        return categoryMapper.selectAll();
    }

    public void updateCategory(Category category) {
        categoryMapper.updateByPrimaryKey(category);
    }

    @Transactional
    public void deleteCategory(Integer id) {
        Category defaultCategory = categoryMapper.selectDefaultCategory();
        List<Article> articles = articleMapper.selectByCategoryId(id);
        if (articles != null && articles.size() > 0) {
            for (Article article: articles) {
                article.setCategoryId(defaultCategory.getId());
                articleMapper.updateByPrimaryKey(article);
            }
        }
        categoryMapper.deleteByPrimaryKey(id);
    }
}

package com.zqj.blog.service;

import com.zqj.blog.dao.CategoryMapper;
import com.zqj.blog.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryMapper categoryMapper;

    @Autowired
    public CategoryService(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
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
}

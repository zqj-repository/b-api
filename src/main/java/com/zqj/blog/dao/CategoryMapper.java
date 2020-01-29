package com.zqj.blog.dao;

import com.zqj.blog.entity.Category;

import java.util.List;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKeyWithBLOBs(Category record);

    int updateByPrimaryKey(Category record);

    List<Category> selectAllCategory();
}
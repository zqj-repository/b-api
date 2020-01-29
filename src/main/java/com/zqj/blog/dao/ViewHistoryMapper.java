package com.zqj.blog.dao;

import com.zqj.blog.entity.ViewHistory;

public interface ViewHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ViewHistory record);

    int insertSelective(ViewHistory record);

    ViewHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ViewHistory record);

    int updateByPrimaryKey(ViewHistory record);
}
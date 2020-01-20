package com.zqj.blog.dao;

import com.zqj.blog.entity.ArticleAttachment;

public interface ArticleAttachmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArticleAttachment record);

    int insertSelective(ArticleAttachment record);

    ArticleAttachment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArticleAttachment record);

    int updateByPrimaryKey(ArticleAttachment record);
}
package com.zqj.blog.dao;

import com.zqj.blog.entity.UserRoleKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRoleMapper {
    int deleteByPrimaryKey(UserRoleKey key);

    int insert(UserRoleKey record);

    int insertSelective(UserRoleKey record);

    int insertList(@Param("userRoleKeyList") List<UserRoleKey> userRoleKeyList);
}
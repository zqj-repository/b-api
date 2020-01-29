package com.zqj.blog.entity;

import java.util.ArrayList;
import java.util.List;

public class UserRoleKey {
    private Integer user;

    private Integer role;

    public UserRoleKey(Integer user, Integer role) {
        this.user = user;
        this.role = role;
    }

    public UserRoleKey() { }

    public static List<UserRoleKey> initUserRole(Integer userId, Integer[] roles) {
        List<UserRoleKey> roleList = new ArrayList<>();
        for (Integer role: roles) {
            roleList.add(new UserRoleKey(userId, role));
        }
        return roleList;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
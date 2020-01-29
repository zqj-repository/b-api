package com.zqj.blog.service;

import com.zqj.blog.dao.RoleMapper;
import com.zqj.blog.dao.UserMapper;
import com.zqj.blog.entity.Role;
import com.zqj.blog.entity.User;
import com.zqj.blog.entity.bo.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userMapper.selectByUserName(s);
        List<Role> roleList = roleMapper.selectRoleListByUserName(user.getUserName());

        return org.springframework.security.core.userdetails.User
            .builder()
            .username(user.getUserName())
            .password(user.getEncryptedPassword())
            .accountLocked(UserStatus.LOCK.getCode().equals(user.getStatus()))
            .disabled(UserStatus.INACTIVE.getCode().equals(user.getStatus()))
            .roles(roleList.stream().map(Role::getName).collect(Collectors.toList()).toArray(new String[]{}))
            .build();
    }
}

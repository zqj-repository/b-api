package com.zqj.blog.service;

import com.zqj.blog.dao.UserMapper;
import com.zqj.blog.dao.UserRoleMapper;
import com.zqj.blog.entity.User;
import com.zqj.blog.entity.UserRoleKey;
import com.zqj.blog.entity.bo.LoginUser;
import com.zqj.blog.entity.bo.NewUser;
import com.zqj.blog.entity.bo.UserStatus;
import com.zqj.blog.properties.BlogProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private BlogProperties blogProperties;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private JwtService jwtService;

    @Transactional
    public String createUser(NewUser newUser) {
        User user = new User();
        user.setUserName(newUser.getUserName());
        user.setEncryptedPassword(new String(Base64.getEncoder().encode(newUser.getPassword().getBytes())));
        user.setEmail(newUser.getEmail());
        user.setRegisteredWhen(new Date());
        user.setStatus(UserStatus.INACTIVE.getCode());
        userMapper.insert(user);

        User poUser = userMapper.selectByUserName(user.getUserName());
        //insert role
        List<UserRoleKey> roleKeys = UserRoleKey.initUserRole(poUser.getId(), new Integer[] {1, 2});
        userRoleMapper.insertList(roleKeys);

        //TODO generate activation url and send to user email
        String plainSignal = poUser.getUserName() + poUser.getRegisteredWhen().getTime();
        String signal = new String(Base64.getEncoder().encode(plainSignal.getBytes()));
        return String.format(blogProperties.getHost() + blogProperties.getActivationUrl(), poUser.getId(),signal);
    }

    @Transactional
    public void activateUser(Integer userId, String signal) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            throw new RuntimeException("User Not Found");
        }
        if (!UserStatus.INACTIVE.getCode().equals(user.getStatus())) {
            throw new RuntimeException("Bad Request.");
        }
        String plainSignal = user.getUserName() + user.getRegisteredWhen().getTime();
        String originalSignal = new String(Base64.getEncoder().encode(plainSignal.getBytes()));
        if (signal != null && signal.equals(originalSignal)) {
            user.setStatus(UserStatus.ACTIVE.getCode());
            userMapper.updateByPrimaryKey(user);
        }
    }

    @Transactional
    public String login(LoginUser loginUser) {
        User user = userMapper.selectByUserName(loginUser.getUserName());
        if (user == null) {
            throw new RuntimeException("Login User Not Found.");
        }
        if (!UserStatus.ACTIVE.getCode().equals(user.getStatus())) {
            throw new RuntimeException("User is not active");
        }
        String encodedPsw = new String(Base64.getEncoder().encode(loginUser.getPassword().getBytes()));
        String token = "";
        if (StringUtils.isNotEmpty(encodedPsw) && encodedPsw.equals(user.getEncryptedPassword())) {
            token = jwtService.generateToken(user.getUserName());
        }
        return token;
    }

}

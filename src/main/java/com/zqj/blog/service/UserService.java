package com.zqj.blog.service;

import com.zqj.blog.dao.UserMapper;
import com.zqj.blog.entity.User;
import com.zqj.blog.entity.bo.NewUser;
import com.zqj.blog.entity.bo.UserStatus;
import com.zqj.blog.properties.BlogProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

@Service
public class UserService {

    @Autowired
    private BlogProperties blogProperties;

    @Autowired
    private UserMapper userMapper;

    public String createUser(NewUser newUser) {
        User user = new User();
        user.setUserName(newUser.getUserName());
        user.setEncryptedPassword(new String(Base64.getEncoder().encode(newUser.getPassword().getBytes())));
        user.setEmail(newUser.getEmail());
        user.setRole(2);
        user.setRegisteredWhen(new Date());
        user.setStatus(UserStatus.INACTIVE.getStatus());
        userMapper.insert(user);

        User poUser = userMapper.selectByUserName(user.getUserName());
        //TODO generate activation url and send to user email
        String plainSignal = poUser.getUserName() + poUser.getRegisteredWhen().getTime();
        String signal = new String(Base64.getEncoder().encode(plainSignal.getBytes()));
        return String.format(blogProperties.getHost() + blogProperties.getActivationUrl(), poUser.getId(),signal);
    }

    public void activateUser(Integer userId, String signal) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            throw new RuntimeException("User Not Found");
        }
        if (!UserStatus.INACTIVE.getStatus().equals(user.getStatus())) {
            throw new RuntimeException("Bad Request.");
        }
        String plainSignal = user.getUserName() + user.getRegisteredWhen().getTime();
        String originalSignal = new String(Base64.getEncoder().encode(plainSignal.getBytes()));
        if (signal != null && signal.equals(originalSignal)) {
            user.setStatus(UserStatus.ACTIVE.getStatus());
            userMapper.updateByPrimaryKey(user);
        }
    }
}

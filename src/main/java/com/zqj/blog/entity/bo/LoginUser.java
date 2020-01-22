package com.zqj.blog.entity.bo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginUser {

    @NotEmpty(message = "user name is empty")
    private String userName;

    @NotEmpty(message = "password is empty")
    private String password;

}

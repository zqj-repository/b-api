package com.zqj.blog.entity.bo;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class NewUser {

    @NotEmpty(message = "用户名不能为空")
    @Pattern(regexp = "^[A-Za-z0-9]{3,12}$")
    private String userName;

    @NotEmpty(message = "密码不能为空")
    @Pattern(regexp = "^[A-Za-z0-9]{8,20}$")
    private String password;

    @Email(message = "邮箱格式错误")
    private String email;

}

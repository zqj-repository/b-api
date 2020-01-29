package com.zqj.blog.entity.bo;

import lombok.Data;
import org.springframework.security.core.userdetails.User;

@Data
public class BlogUserDetails extends User {

    private String ip;

}

package com.zqj.blog.context;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;

@Data
public class RestRequestContext {

    private String ip;

    public static RestRequestContext init(HttpServletRequest request) {
        RestRequestContext restRequestContext = new RestRequestContext();
        restRequestContext.setIp(request.getRemoteAddr());
        return restRequestContext;
    }
}

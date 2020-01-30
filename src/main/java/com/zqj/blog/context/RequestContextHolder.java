package com.zqj.blog.context;

import org.springframework.util.Assert;

public class RequestContextHolder {

    private static final ThreadLocal<RestRequestContext> contextHolder = new ThreadLocal<>();

    public static void clearContext() {
        contextHolder.remove();
    }

    public static RestRequestContext getContext() {
        RestRequestContext ctx = contextHolder.get();
        if (ctx == null) {
            ctx = createEmptyContext();
            contextHolder.set(ctx);
        }

        return ctx;
    }

    public static void setContext(RestRequestContext context) {
        Assert.notNull(context, "Only non-null RestRequestContext instances are permitted");
        contextHolder.set(context);
    }

    public static RestRequestContext createEmptyContext() {
        return new RestRequestContext();
    }
}

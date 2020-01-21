package com.zqj.blog.util;


public class Validations {

    public static void expect(Boolean e) {
        expect(e, "Request validation failed.");
    }

    public static void expect(Boolean e, String message) {
        if (!e) {
            throw new RuntimeException(message);
        }
    }

}

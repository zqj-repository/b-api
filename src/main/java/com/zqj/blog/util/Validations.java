package com.zqj.blog.util;

public class Validations {

    public static void check(Boolean ...booleans) {
        check("Request validation failed.", booleans);
    }

    public static void check(String message, Boolean ...booleans) {
        for (Boolean b: booleans) {
            if (!b) {
                throw new RuntimeException(message);
            }
        }
    }

}

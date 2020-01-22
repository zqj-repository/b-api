package com.zqj.blog.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "blog")
public class BlogProperties {

    private String host;
    private String activationUrl;
    private String jwtPrivateKey;
    private String jwtPublicKey;
}

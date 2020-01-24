package com.zqj.blog.filter;

import com.zqj.blog.service.JwtService;
import org.apache.commons.lang3.StringUtils;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_START_STR = "Bearer ";

    private JwtService jwtService;

    @Autowired
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
        super(authenticationManager);
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.isNotEmpty(authorization)) {
            String token = StringUtils.removeStart(authorization, AUTHORIZATION_START_STR);
            if (StringUtils.isNotEmpty(token)) {
                try {
                    String userName = jwtService.getSubject(token);

                } catch (InvalidJwtException | MalformedClaimException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

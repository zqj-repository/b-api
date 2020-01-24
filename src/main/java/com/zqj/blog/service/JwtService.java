package com.zqj.blog.service;


import com.zqj.blog.properties.BlogProperties;
import com.zqj.blog.util.KeyUtil;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwt.consumer.JwtContext;

import java.security.*;

import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    public static final Integer EXPIRATION = 10;
    public static final String ISSUER = "Blog API";
    public static final String AUDIENCE = "Blog Client";

    @Autowired
    private BlogProperties blogProperties;

    public String generateToken(String subject) {
        return generateToken(subject, KeyUtil.loadPrivateKey(blogProperties.getJwtPrivateKey()));
    }

    public String generateToken(String subject, PrivateKey privateKey) {
        JwtClaims claims = new JwtClaims();
        claims.setIssuer(ISSUER);
        claims.setAudience(AUDIENCE);
        claims.setExpirationTimeMinutesInTheFuture(EXPIRATION);
        claims.setGeneratedJwtId();
        claims.setIssuedAtToNow();
        claims.setSubject(subject);

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setKey(privateKey);
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
        try {
            return jws.getCompactSerialization();
        } catch (JoseException e) {
            throw new RuntimeException("Unexpected result.", e);
        }
    }

    public String getSubject(String jwt) throws InvalidJwtException, MalformedClaimException {
        JwtClaims claims = getJwtBody(jwt);
        return claims.getSubject();
    }

    public JwtClaims getJwtBody(String jwt) throws InvalidJwtException {
        return getJwtBody(jwt, KeyUtil.loadPublicKey(blogProperties.getJwtPublicKey()));
    }

    public JwtClaims getJwtBody(String jwt, PublicKey publicKey) throws InvalidJwtException {
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
            .setRequireExpirationTime()
            .setAllowedClockSkewInSeconds(30)
            .setRequireSubject()
            .setExpectedIssuer(ISSUER)
            .setExpectedAudience(AUDIENCE)
            .setVerificationKey(publicKey)
            .setJwsAlgorithmConstraints(AlgorithmConstraints.ConstraintType.WHITELIST, AlgorithmIdentifiers.RSA_USING_SHA256)
            .build();

        JwtContext context = jwtConsumer.process(jwt);
        return context.getJwtClaims();
    }
}

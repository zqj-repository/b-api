package com.zqj.blog.util;


import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;

import java.util.Base64;
import java.util.Date;

public class JwtUtil {

    public static final Long EXPIRATION = 86400000L;

    public static void generateToken(String subject, String privateKey) {
        JwtClaims claims = new JwtClaims();
        claims.setIssuer("Blog API");  // who creates the token and signs it
        claims.setAudience("Blog Client"); // to whom the token is intended to be sent
        claims.setExpirationTimeMinutesInTheFuture(10); // time when the token will expire (10 minutes from now)
        claims.setGeneratedJwtId(); // a unique identifier for the token
        claims.setIssuedAtToNow();  // when the token was issued/created (now)
        claims.setSubject(subject); // the subject/principal is whom the token is about
        JsonWebSignature jws = new JsonWebSignature();

        // The payload of the JWS is JSON content of the JWT Claims
        jws.setPayload(claims.toJson());

        // The JWT is signed using the private key
        jws.setKey(rsaJsonWebKey.getPrivateKey());
        jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
        String jwt = jws.getCompactSerialization();
        System.out.println(jwt);
    }

}

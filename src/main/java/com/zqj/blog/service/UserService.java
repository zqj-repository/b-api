package com.zqj.blog.service;

import com.zqj.blog.dao.UserMapper;
import com.zqj.blog.entity.User;
import com.zqj.blog.entity.bo.LoginUser;
import com.zqj.blog.entity.bo.NewUser;
import com.zqj.blog.entity.bo.UserStatus;
import com.zqj.blog.properties.BlogProperties;
import org.apache.commons.lang3.StringUtils;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwt.consumer.JwtContext;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

@Service
public class UserService {

    @Autowired
    private BlogProperties blogProperties;

    @Autowired
    private UserMapper userMapper;

    public String createUser(NewUser newUser) {
        User user = new User();
        user.setUserName(newUser.getUserName());
        user.setEncryptedPassword(new String(Base64.getEncoder().encode(newUser.getPassword().getBytes())));
        user.setEmail(newUser.getEmail());
        user.setRole(2);
        user.setRegisteredWhen(new Date());
        user.setStatus(UserStatus.INACTIVE.getStatus());
        userMapper.insert(user);

        User poUser = userMapper.selectByUserName(user.getUserName());
        //TODO generate activation url and send to user email
        String plainSignal = poUser.getUserName() + poUser.getRegisteredWhen().getTime();
        String signal = new String(Base64.getEncoder().encode(plainSignal.getBytes()));
        return String.format(blogProperties.getHost() + blogProperties.getActivationUrl(), poUser.getId(),signal);
    }

    public void activateUser(Integer userId, String signal) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            throw new RuntimeException("User Not Found");
        }
        if (!UserStatus.INACTIVE.getStatus().equals(user.getStatus())) {
            throw new RuntimeException("Bad Request.");
        }
        String plainSignal = user.getUserName() + user.getRegisteredWhen().getTime();
        String originalSignal = new String(Base64.getEncoder().encode(plainSignal.getBytes()));
        if (signal != null && signal.equals(originalSignal)) {
            user.setStatus(UserStatus.ACTIVE.getStatus());
            userMapper.updateByPrimaryKey(user);
        }
    }

    public String login(LoginUser loginUser) {
        User user = userMapper.selectByUserName(loginUser.getUserName());
        if (user == null) {
            throw new RuntimeException("Login User Not Found.");
        }
        String encodedPsw = new String(Base64.getEncoder().encode(loginUser.getPassword().getBytes()));
        String token = "";
        if (StringUtils.isNotEmpty(encodedPsw) && encodedPsw.equals(user.getEncryptedPassword())) {
//            token= Jwts.builder()
//                    .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encode("key".getBytes()))
//                    .setIssuer("Blog Backend")
//                    .setAudience("Blog Frontend")
//                    .setExpiration(new Date(System.currentTimeMillis() +  86400 * 1000))
//                    .setIssuedAt(new Date())
//                    .setSubject(user.getUserName())
//                    .compact();
        }
        return token;
    }

    public static void main(String[] args) throws JoseException {
        RsaJsonWebKey rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
        System.out.println(new String(Base64.getEncoder().encode(rsaJsonWebKey.getKey().getEncoded())));

        System.out.println(new String(Base64.getEncoder().encode(rsaJsonWebKey.getPrivateKey().getEncoded())));
        // Give the JWK a Key ID (kid), which is just the polite thing to do
        rsaJsonWebKey.setKeyId("k1");

        // Create the Claims, which will be the content of the JWT
        JwtClaims claims = new JwtClaims();
        claims.setIssuer("Blog API");  // who creates the token and signs it
        claims.setAudience("Blog Client"); // to whom the token is intended to be sent
        claims.setExpirationTimeMinutesInTheFuture(10); // time when the token will expire (10 minutes from now)
        claims.setGeneratedJwtId(); // a unique identifier for the token
        claims.setIssuedAtToNow();  // when the token was issued/created (now)
        claims.setSubject("user name"); // the subject/principal is whom the token is about
        JsonWebSignature jws = new JsonWebSignature();

        // The payload of the JWS is JSON content of the JWT Claims
        jws.setPayload(claims.toJson());

        // The JWT is signed using the private key
        jws.setKey(rsaJsonWebKey.getPrivateKey());
        jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
        String jwt = jws.getCompactSerialization();
        System.out.println(jwt);

        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime() // the JWT must have an expiration time
                .setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account for clock skew
                .setRequireSubject() // the JWT must have a subject claim
                .setExpectedIssuer("Blog API") // whom the JWT needs to have been issued by
                .setExpectedAudience("Blog Client") // to whom the JWT is intended for
                .setVerificationKey(rsaJsonWebKey.getRsaPublicKey()) // verify the signature with the public key
                .setJwsAlgorithmConstraints( // only allow the expected signature algorithm(s) in the given context
                        AlgorithmConstraints.ConstraintType.WHITELIST, AlgorithmIdentifiers.RSA_USING_SHA256) // which is only RS256 here
                .build(); // create the JwtConsumer instance

        try {
            JwtContext context = jwtConsumer.process(jwt);
            JwtClaims claims1 = context.getJwtClaims();
            System.out.println(claims1.getSubject());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

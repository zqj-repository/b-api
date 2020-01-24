package com.zqj.blog.util;

import org.jose4j.base64url.Base64;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class KeyUtil {

    public static PrivateKey loadPrivateKey(String str) {
        byte[] decodedKey = Base64.decode(str);
        KeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        PrivateKey privateKey = null;
        try {
            privateKey = KeyFactory.getInstance("RSA").generatePrivate(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load private key", e);
        }
        return privateKey;
    }

    public static PublicKey loadPublicKey(String str) {
        byte[] decodedPKey = Base64.decode(str);
        X509EncodedKeySpec pkeySpec = new X509EncodedKeySpec(decodedPKey);
        PublicKey publicKey = null;
        try {
            publicKey = KeyFactory.getInstance("RSA").generatePublic(pkeySpec);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load public key", e);
        }
        return publicKey;
    }

    public static void generateKeyPair() {
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyPairGenerator.initialize(2048, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        String privateKeyStr = Base64.encode(privateKey.getEncoded());
        String publicKeyStr = Base64.encode(publicKey.getEncoded());

        System.out.println(privateKeyStr);

        System.out.println(publicKeyStr);
    }

    public static void main(String[] args) {
        generateKeyPair();
    }
}

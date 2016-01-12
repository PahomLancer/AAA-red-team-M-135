package com.blzr.domain;

import java.security.MessageDigest;
import java.security.SecureRandom;

public class User {
    private Long id;
    private String name;
    private String login;
    private byte[] hash;
    private byte[] salt;

    public User(Long id, String name, String login, String password) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.salt = generateSalt();
        this.hash = generateHash(password, this.salt);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public boolean validatePassword(String password) {
        return MessageDigest.isEqual(generateHash(password, this.salt), this.getHash());
    }

    /**
     * sha(sha(pass)+salt)
     */
    public static byte[] generateHash(String password, byte[] salt) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] shaFirst = password.getBytes("UTF-8");
            sha.reset();
            sha.update(shaFirst);
            sha.update(sha.digest());
            sha.update(salt);
            return sha.digest();
        } catch (Exception e) {
            throw new RuntimeException("Suppress exception", e);
        }
    }

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return bytes;
    }
}

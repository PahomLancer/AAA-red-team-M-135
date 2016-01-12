package com.andr.domain;

import java.security.MessageDigest;
import java.security.SecureRandom;

//opredelenie klassa User
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

    //return ID
    public Long getId() {
        return id;
    }

    //zadat ID
    public void setId(Long id) {
        this.id = id;
    }
    
    //return name
    public String getName() {
        return name;
    }
    
    //zadat name
    public void setName(String name) {
        this.name = name;
    }
    
    //return login
    public String getLogin() {
        return login;
    }
    
    //zadat login
    public void setLogin(String login) {
        this.login = login;
    }
    
    //return hash
    public byte[] getHash() {
        return hash;
    }
    
    //zadat hash
    public void setHash(byte[] hash) {
        this.hash = hash;
    }
    
    //return sole
    public byte[] getSalt() {
        return salt;
    }

    //zadat sole
    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    //proverka parolya na validnost
    public boolean validatePassword(String password) {
        return MessageDigest.isEqual(generateHash(password, this.salt), this.getHash());
    }
    
    //generaciya hasha
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

    //generaciya soli
    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return bytes;
    }
}

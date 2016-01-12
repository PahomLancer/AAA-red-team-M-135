package com.andr.domain;

//opredelenie klassa User
public class User {
    private final Long id;
    private final String name;
    private final String login;
    private final String hash;
    private final String salt;

    public User(Long id, String name, String login, String hash, String salt) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.hash = hash;
        this.salt = salt;
    }

    //return ID
    public Long getId() {
        return id;
    }

    //retun name
    public String getName() {
        return name;
    }
    
    //return login
    public String getLogin() {
        return login;
    }
    
    //return hash
    public String getHash() {
        return hash;
    }

    //return sol`
    public String getSalt() {
        return salt;
    }
}

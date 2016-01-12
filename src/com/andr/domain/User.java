package com.andr.domain;

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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getHash() {
        return hash;
    }

    public String getSalt() {
        return salt;
    }
}

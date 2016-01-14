package com.andr.domains;

public class User {
    private int id;

    private String name;

    private String password;

    private String login;

    private String salt;

    public User(int id, String name, String password, String login, String salt) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.login = login;
        this.salt = salt;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public String getSalt() {
        return salt;
    }

}

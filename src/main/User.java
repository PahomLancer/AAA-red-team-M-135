package main;

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

    public int returnId() {
        return id;
    }
    public String returnName() {
        return name;
    }
    public String returnPassword() {
        return password;
    }
    public String returnLogin() {
        return login;
    }
    public String returnSalt() {
        return salt;
    }
}

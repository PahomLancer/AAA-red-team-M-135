package andr.domains;

import java.util.Date;
import java.util.Calendar;

public class Accounting {
    private int id;
    private Role role;
    private User user;
    private Date loginDate;
    private Date logoutDate;
    private int value;
    private Resource resources;
    //Set account
    public Accounting(int id, Role role, User user, Resource resources) {
        this.id = id;
        this.role = role;
        this.user = user;
        this.loginDate = Calendar.getInstance().getTime();
        this.logoutDate = null;
        this.value = 0;
        this.resources = resources;
    }
    //Log delog
    public void setLogoutDate(Date logoutDate) {
        this.logoutDate = logoutDate;
    }
    //Set value
    public void setValue(int value) {
        this.value = value;
    }
    //Get id
    public int returnId() {
        return id;
    }
    public Role returnRole() {
        return role;
    }
    public User returnUser() {
        return user;
    }
    public Date returnLoginDate() {
        return loginDate;
    }
    public Date returnLogoutDate() {
        return logoutDate;
    }
    public int returnValue() {
        return value;
    }
    public Resource returnResources() {
        return resources;
    }
}
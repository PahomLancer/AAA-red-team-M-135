package com.andr.domains;

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

    public Accounting(int id, Role role, User user, Resource resources) {
        this.id = id;
        this.role = role;
        this.user = user;
        this.loginDate = Calendar.getInstance().getTime();
        this.logoutDate = null;
        this.value = 0;
        this.resources = resources;
    }

    public int getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public void setLogoutDate(Date logoutDate) {
        this.logoutDate = logoutDate;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public Date getLogoutDate() {
        return logoutDate;
    }

    public int getValue() {
        return value;
    }

    public Resource getResources() {
        return resources;
    }

}

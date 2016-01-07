package main.java.com.company.domains;

import java.util.Date;

//import main.auth;

import java.util.Calendar;
//import java.util.Date;

public class account {
    private int id;
    private role role;
    private user user;
    private Date loginDate;
    private Date logoutDate;
    private int value;
    private resource resources;
    //Set account
    public account(int id, role role, user user, resource resources) {
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
    public role returnRole() {
        return role;
    }
    public user returnUser() {
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
    public resource returnResources() {
        return resources;
    }
}
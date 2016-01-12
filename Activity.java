package com.blzr.domain;

import java.util.Date;

public class Activity {
    private Long id;
    private Authority authority;
    private Date loginDate;
    private Date logoutDate;
    private Long volume;

    public Activity(Long id, Authority authority, Date loginDate, Date logoutDate, Long volume) {
        this.id = id;
        this.authority = authority;
        this.loginDate = loginDate;
        this.logoutDate = logoutDate;
        this.volume = volume;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Date getLogoutDate() {
        return logoutDate;
    }

    public void setLogoutDate(Date logoutDate) {
        this.logoutDate = logoutDate;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }
}

package com.andr.domain;

import java.util.Date;

public class Activity {
    private final Long id;
    private final Authority authority;
    private final Date loginDate;
    private final Date logoutDate;
    private final Long volume;

    public Activity(Long id, Authority authority, Date loginDate, Date logoutDate, Long volume) {
        this.id = id;
        this.authority = authority;
        this.loginDate = loginDate;
        this.logoutDate = logoutDate;
        this.volume = volume;
    }

    public Activity(Authority authority, Date loginDate, Date logoutDate, Long volume) {
        this(null, authority, loginDate, logoutDate, volume);
    }

    //return ID
    public Long getId() {
        return id;
    }

    //return prava
    public Authority getAuthority() {
        return authority;
    }

    //return data vhoda
    public Date getLoginDate() {
        return loginDate;
    }

    //return data vihoda
    public Date getLogoutDate() {
        return logoutDate;
    }

    //return znachenie prav
    public Long getVolume() {
        return volume;
    }
}

package com.andr.domain;

import java.util.Date;

//opredelenie klassa activnosti
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
    
    //return id
    public Long getId() {
        return id;
    }
    
    //zadat id
    public void setId(Long id) {
        this.id = id;
    }
    
    //return authority
    public Authority getAuthority() {
        return authority;
    }
    
    //zadat avtorizaciy
    public void setAuthority(Authority authority) {
        this.authority = authority;
    }
    
    //return datu vlogina
    public Date getLoginDate() {
        return loginDate;
    }
    
    //zadat datu logina
    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }
    
    //return data vihoda
    public Date getLogoutDate() {
        return logoutDate;
    }
    
    //zadat datu vihoda
    public void setLogoutDate(Date logoutDate) {
        this.logoutDate = logoutDate;
    }

    //return prava
    public Long getVolume() {
        return volume;
    }
    
    //zadat prava
    public void setVolume(Long volume) {
        this.volume = volume;
    }
}

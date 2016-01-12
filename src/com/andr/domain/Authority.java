package com.andr.domain;

//opredelenie klassa avtorizacii
public class Authority {
    private final Long id;
    private final User user;
    private final Role role;
    private final String site;

    public Authority(Long id, User user, Role role, String site) {
        this.id = id;
        this.user = user;
        this.role = role;
        this.site = site;
    }
    
    //return ID
    public Long getId() {
        return id;
    }

    //return polzovatel
    public User getUser() {
        return user;
    }

    //return role
    public Role getRole() {
        return role;
    }
    
    //return site
    public String getSite() {
        return site;
    }
}

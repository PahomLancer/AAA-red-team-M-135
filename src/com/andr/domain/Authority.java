package com.andr.domain;

//opredelenie klassa avtorizacii
public class Authority {
    private Long id;
    private User user;
    private Role role;
    private String site;

    public Authority(Long id, User user, Role role, String site) {
        this.id = id;
        this.user = user;
        this.role = role;
        this.site = site;
    }
    
    //return id
    public Long getId() {
        return id;
    }
    
    //zadat id
    public void setId(Long id) {
        this.id = id;
    }

    //return polzovatel
    public User getUser() {
        return user;
    }
    
    //zadat polzovatelya
    public void setUser(User user) {
        this.user = user;
    }

    //return role
    public Role getRole() {
        return role;
    }

    //zadat role
    public void setRole(Role role) {
        this.role = role;
    }
    
    //return site
    public String getSite() {
        return site;
    }

    //zadat site
    public void setSite(String site) {
        this.site = site;
    }
}

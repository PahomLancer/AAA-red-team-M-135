package com.andr.domain;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}

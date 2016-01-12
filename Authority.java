package com.blzr.domain;

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

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Role getRole() {
        return role;
    }

    public String getSite() {
        return site;
    }
}

package com.blzr.domain;

public enum Role {
    READ,
    WRITE,
    EXECUTE;

    public static Role getRole(String role) {
        for (Role r : values()) {
            if (r.name().equals(role)) {
                return r;
            }
        }
        return null;
    }
}

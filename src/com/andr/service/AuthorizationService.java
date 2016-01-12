package com.andr.service;

import com.andr.domain.Authority;
import com.andr.domain.Role;
import com.andr.domain.User;

import java.util.ArrayList;
import java.util.List;

public class AuthorizationService {
    // Temporary storage
    private static List<User> users = new ArrayList<User>() {{
        add(new User(1L, "John Doe", "jdoe", "sup3rpaZZ"));
        add(new User(2L, "Jane Row", "jrow", "Qweqrty12"));
    }};

    private static List<Authority> authorities = new ArrayList<Authority>() {{
        add(new Authority(1L, users.get(0), Role.READ, "a"));
        add(new Authority(2L, users.get(0), Role.WRITE, "a.b"));
        add(new Authority(3L, users.get(1), Role.EXECUTE, "a.b.c"));
        add(new Authority(4L, users.get(0), Role.EXECUTE, "a.bc"));
    }};

    public User getUser(String user) {
        for (User u : users) {
            if (u.getLogin().equals(user)) {
                return u;
            }
        }
        return null;
    }

    public boolean isUserExist(String username) {
        return getUser(username) != null;
    }

    public boolean isRoleExist(String role) {
        return Role.getRole(role) != null;
    }

    public boolean isPasswordCorrect(String username, String password) {
        return getUser(username).validatePassword(password);
    }

    public Authority getAuthority(String username, String site, String role) {
        User user = getUser(username);
        Role r = Role.getRole(role);
        if (r == null) {
            return null;
        }

        for (Authority a : authorities) {
            if (a.getUser() == user && a.getRole() == r) {
                if (isSubSite(a.getSite(), site)) {
                    return a;
                }
            }
        }
        return null;
    }

    public boolean isAuthorized(String username, String site, String role) {
        return getAuthority(username, site, role) != null;
    }

    /**
     * Check if dst subsite of src
     * a.b, a.b.c -> true
     * a.b, a.b   -> true
     * a.b, a     -> false
     * a.b, a.d   -> false
     */
    private boolean isSubSite(String src, String dst) {
        String[] srcList = src.split("\\.");
        String[] dstList = dst.split("\\.");

        if (dstList.length < srcList.length) {
            return false;
        }

        for (int i = 0; i < srcList.length; i++) {
            if (!srcList[i].equals(dstList[i])) {
                return false;
            }
        }
        return true;
    }
}

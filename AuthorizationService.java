package com.blzr.service;

import com.blzr.domain.Authority;
import com.blzr.domain.Role;
import com.blzr.domain.User;

public class AuthorizationService {
    private final ConnectionService connectionService;
    private final AuthenticationService authenticationService;

    public AuthorizationService(ConnectionService connectionService, AuthenticationService authenticationService) {
        this.connectionService = connectionService;
        this.authenticationService = authenticationService;
    }

    public User getUser(String user) {
        return connectionService.getUserByLogin(user);
    }

    public boolean isUserExist(String username) {
        return getUser(username) != null;
    }

    public boolean isRoleExist(String role) {
        return Role.getRole(role) != null;
    }

    public boolean isPasswordCorrect(String username, String password) {
        final User user = connectionService.getUserByLogin(username);
        return authenticationService.validatePassword(password, user.getHash(), user.getSalt());
    }

    public Authority getAuthority(String username, String role, String site) {
        User user = getUser(username);
        Role r = Role.getRole(role);
        if (r == null) {
            return null;
        }

        for (Authority a : connectionService.getAuthoritiesByUserAndRole(user, r)) {
            if (isSubSite(a.getSite(), site)) {
                return a;
            }
        }
        return null;
    }

    public boolean isAuthorized(String username, String role, String site) {
        return getAuthority(username, role, site) != null;
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

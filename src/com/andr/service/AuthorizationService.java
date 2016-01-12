package com.andr.service;

import com.andr.domain.Authority;
import com.andr.domain.Role;
import com.andr.domain.User;

//opredelenie klassa avtorizacii
public class AuthorizationService {
    private final ConnectionService connectionService;
    private final AuthenticationService authenticationService;

    public AuthorizationService(ConnectionService connectionService, AuthenticationService authenticationService) {
        this.connectionService = connectionService;
        this.authenticationService = authenticationService;
    }
    
    //return polzovatel
    public User getUser(String user) {
        return connectionService.getUserByLogin(user);
    }
    
    //proverka suwestvuet li polzovatel
    public boolean isUserExist(String username) {
        return getUser(username) != null;
    }

    //proverka roli
    public boolean isRoleExist(String role) {
        return Role.getRole(role) != null;
    }

    //proverka parolya
    public boolean isPasswordCorrect(String username, String password) {
        final User user = connectionService.getUserByLogin(username);
        return authenticationService.validatePassword(password, user.getHash(), user.getSalt());
    }
    
    //avtorizaciya
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

    //proverka avtorizovan li polzovatel
    public boolean isAuthorized(String username, String role, String site) {
        return getAuthority(username, role, site) != null;
    }

    //proverka na podstroku
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

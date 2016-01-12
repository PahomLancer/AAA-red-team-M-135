import EnumConstants.AuthErrorCodes;

/**
 * Created by Артем on 05.10.2015.
 */
public class AuthorizationInfo {

    private AuthErrorCodes code;
    private int userId;
    private int roleId;

    public AuthorizationInfo(AuthErrorCodes code, int userId, int roleId) {
        this.code = code;
        this.userId = userId;
        this.roleId = roleId;
    }

    public AuthorizationInfo(AuthErrorCodes code) {
        this(code, 0, 0);
    }

    public AuthErrorCodes getCode() {
        return code;
    }

    public int getRoleId() {
        return roleId;
    }

}

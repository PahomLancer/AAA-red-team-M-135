package EnumConstants;

/**
 * Created by Артем on 04.10.2015.
 */

public enum AuthErrorCodes {
    AUTH_WRONG_DB(-1),
    AUTH_SUCCESS(0),
    AUTH_WRONG_LOGIN(1),
    AUTH_WRONG_PASSWORD(2),
    AUTH_UNKNOWN_ROLE(3),
    AUTH_NO_ACCESS(4),
    AUTH_INCORRECT_ACTIVITY(5);

    private int code;

    AuthErrorCodes(int _code) {
        code=_code;
    }

    public int toInt() {
        return code;
    }

    public static void exitWith(AuthErrorCodes code) {
        System.exit(code.toInt());
    }

    public String getDescription() {
        switch (code) {
            case -1: {
                return "Error while connecting to database";
            }
            case 0: {
                return "Success";
            }
            case 1: {
                return "Login is incorrect";
            }
            case 2: {
                return "Password is incorrect";
            }
            case 3: {
                return "Role is unknown";
            }
            case 4: {
                return "Forbidden";
            }
            case 5: {
                return "Activity is invalid";
            }
        }
        return null;
    }
}

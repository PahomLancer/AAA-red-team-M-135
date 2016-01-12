/**
 * Created by Артем on 04.10.2015.
 */

import EnumConstants.AuthErrorCodes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.*;
import java.math.BigInteger;

public class AuthRecord {
    private int id;
    private String login;
    private String hash;
    private String salt;
    private String name;
    private static final Logger logger = LogManager.getLogger(AuthRecord.class);

    public AuthRecord(int id, String login, String hash, String salt, String name) {
        this.id = id;
        this.login = login;
        this.hash = hash;
        this.salt = salt;
        this.name = name;
    }

    public AuthErrorCodes checkPassword(String password){
        logger.trace("Checking password...");
        String hashedPwd = makeHash(makeHash(password)+salt);
        if (!hashedPwd.equals(hash)) {
            logger.error("Password "+password+" for user "+login+" is incorrect");
            return AuthErrorCodes.AUTH_WRONG_PASSWORD;
        }

        logger.info("Password is correct");
        return AuthErrorCodes.AUTH_SUCCESS;
    }

    public int getId() {
        return id;
    }

    public static String makeHash(String s){
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(s.getBytes(), 0, s.length());
            String md5hash = new BigInteger(1,md5.digest()).toString(16);
            return md5hash;

        } catch (final NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

}

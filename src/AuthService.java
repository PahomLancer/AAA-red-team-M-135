/**
 * Created by Артем on 04.10.2015.
 */

import EnumConstants.AuthErrorCodes;
import EnumConstants.Roles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static EnumConstants.AuthErrorCodes.*;

public class AuthService {
    private static final Logger logger = LogManager.getLogger(AuthService.class);

    public AuthService() {
        sqlCon = null;
    }

    public boolean connect(String dbName, String dbLogin, String dbPwd) {
        boolean shouldICreateDatabase = false;

        if (!(new File("dbase/aaa.h2.db").isFile())) {
            shouldICreateDatabase=true;
        }

        logger.trace("Connecting to database...");

        try {
            Class.forName("org.h2.Driver");
            sqlCon = DriverManager.getConnection(dbName, dbLogin, dbPwd);

            if (shouldICreateDatabase) {
                logger.trace("Creating database and filling it...");
                try {
                    org.h2.tools.RunScript.execute(sqlCon, new FileReader("dbase/create_db.sql"));
                    org.h2.tools.RunScript.execute(sqlCon, new FileReader("dbase/fill_db.sql"));
                } catch (SQLException e) {
                    logger.error(e);
                } catch (FileNotFoundException e) {
                    logger.error(e);
                }
            }

            loadAuthBase();
            return true;
        } catch (Exception e) {
            logger.error("Cannot connect to the database", e);
            return false;
        }
    }

    public void closeConnection() {
        try {
            if (sqlCon != null) sqlCon.close();
        } catch (Exception e) {
            logger.error("Cannot close the connection", e);
        }
    }

    public AuthRecord getRecord(String login) {
        return records.get(login);
    }

    public AuthErrorCodes signIn(String login, String pwd) {
        logger.trace("Authentication");
        AuthRecord rec = getRecord(login);
        if (rec == null) {
            logger.error("User "+login+" does not exist");
            return AUTH_WRONG_LOGIN;
        }

        return rec.checkPassword(pwd);
    }


    public AuthErrorCodes signIn(String login, String pwd, String res, Roles role, String startDate, String endDate, Integer volume) {
        //1 этап
        AuthErrorCodes authResult = signIn(login, pwd);
        if (authResult != AUTH_SUCCESS) return authResult;

        if (res == null) return AUTH_SUCCESS;

        //2 этап
        AuthorizationInfo authorizationRes = authorizeToResource(login, res, role);
        if (authorizationRes.getCode() != AUTH_SUCCESS) {
            logger.error("Cannot authorize "+login+" to "+res+" with role "+role.toString());
            return authorizationRes.getCode();
        }

        if (startDate == null) return AUTH_SUCCESS;

        //3 этап
        //Проверка даты и объема
        logger.trace("Accounting");

        logger.trace("Checking dates and volume...");
        SimpleDateFormat dateFormat = new SimpleDateFormat("y-MM-dd");
        dateFormat.setLenient(false);
        try {
            if ((dateFormat.parse(startDate) == null) || (dateFormat.parse(endDate) == null) || (volume == null)) {
                logger.error("Some parameters (start date, end date, volume) are missing");
                return AUTH_INCORRECT_ACTIVITY;
            }
        } catch (ParseException e) {
            logger.error("Cannot parse the date", e);
            return AUTH_INCORRECT_ACTIVITY;
        }

        //Запись в бд
        logger.trace("Inserting new data to the database...");
        PreparedStatement st = null;
        AuthErrorCodes resultCode = AUTH_INCORRECT_ACTIVITY;
        try {
            sqlCon.setAutoCommit(false);
            st = sqlCon.prepareStatement("insert into accounting (roles_id, start_date, end_date, volume) values (?, PARSEDATETIME(?, \'yyyy-MM-dd\'), PARSEDATETIME(?, \'yyyy-MM-dd\'),?)");
            st.setInt(1, authorizationRes.getRoleId());
            st.setString(2, startDate);
            st.setString(3, endDate);
            st.setInt(4, volume);
            st.execute();
            sqlCon.commit();

            resultCode = AUTH_SUCCESS;
        } catch (Exception e) {
            logger.error(e);
        } finally {
            try {
                if (st != null) st.close();
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return resultCode;
    }

    private AuthorizationInfo authorizeToResource(String login, String res, Roles role) {
        //Считаем, что аутентификация пройдена - не проверяем пароль
        logger.trace("Authorization");
        if (sqlCon == null) {
            logger.error("Wrong database");
            return new AuthorizationInfo(AUTH_WRONG_DB);
        }

        if (role == null) {
            logger.error("Wrong role");
            return new AuthorizationInfo(AUTH_UNKNOWN_ROLE);
        }

        PreparedStatement st = null;
        ResultSet rs = null;
        AuthRecord userRecord = records.get(login);

        boolean access = false;
        int outRoleId = 0;
        try {
            sqlCon.setAutoCommit(false);
            //id auth_id resource role
            st = sqlCon.prepareStatement("select id, resource, role from roles where auth_id = ?");
            st.setInt(1, userRecord.getId());
            rs = st.executeQuery();

            String _res;
            String _role;
            while ((rs.next()) && (!access)) {
                _res = rs.getString(2);
                _role = rs.getString(3);
                access = (isChild(_res, res)) && (_role.equals(role.toString())) && (Roles.valueOf(_role) != null);
                if (access) {
                    outRoleId = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            try {
                if (st != null) st.close();
                if (rs != null) rs.close();
            } catch (Exception e) {
                logger.error(e);
            }
        }

        return new AuthorizationInfo((access) ? AUTH_SUCCESS : AUTH_NO_ACCESS, userRecord.getId(), outRoleId);
    }

    private boolean isChild(String ancestor, String child) {
        String[] a = ancestor.split("[.]");
        String[] c = child.split("[.]");

        if (a.length > c.length) return false;

        for (int i = 0; i < a.length; i++) {
            if (!a[i].equals(c[i])) return false;
        }

        return true;
    }

    private AuthErrorCodes loadAuthBase() throws FileNotFoundException, SQLException {
        if (sqlCon == null) return AUTH_WRONG_DB;

        records = new HashMap<>();

        Statement st = null;
        ResultSet rs = null;
        try {
            st = sqlCon.createStatement();
            rs = st.executeQuery("select * from auth");
            while (rs.next()) {
                records.put(rs.getString(2), new AuthRecord(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            try {
                if (st != null) st.close();
                if (rs != null) rs.close();
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return AUTH_SUCCESS;
    }

    private Connection sqlCon = null;
    private Map<String, AuthRecord> records;
}

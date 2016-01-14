package com.andr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;

import com.andr.domains.Resource;
import com.andr.domains.Role;
import com.andr.domains.User;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Auth {

    private static final Logger logger = LogManager.getLogger(Auth.class);

    Connection connection;
    Resource resource;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private boolean resAccess = false;
    private boolean correctVolume = false;
    private boolean correctDate = false;
    private boolean correctPass = false;
    private boolean correctLogin = false;

    public java.util.Date dateIn;
    public java.util.Date dateOut;
    int volume;
    User user;

    public Auth() throws SQLException {
        Auth.logger.debug("Establishing connection with database");

        connection = getConnection();
        df.setLenient(false);


        Auth.logger.debug("Migration");

        // Create the Flyway instance
        Flyway flyway = new Flyway();

        // Point it to the database
        flyway.setDataSource("jdbc:h2:./aaa", "sa", "");

        // Start the migration
        flyway.migrate();

    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:./aaa", "sa", "");
    }

    public static String md5(String input) {

        String md5 = null;

        if (null == input) return null;

        try {

            //Create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");
            //Update input string in message digest
            digest.update(input.getBytes(), 0, input.length());
            //Converts message digest value in base 16 (hex)
            md5 = new BigInteger(1, digest.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }
        return md5;
    }

    //Check authentication
    public void authentication(String login, String pass) throws SQLException {
        Auth.logger.debug("Checking authentication");
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Auth WHERE login=?")) {
            statement.setString(1, login);

            try (ResultSet result = statement.executeQuery()) {

                correctLogin = result.first();

                if (!correctLogin) {
                    return;
                }

                correctPass = md5(md5(pass) + result.getString("salt")).equals(result.getString("password"));

                if (!correctPass) {
                    return;
                }
                user = new User(result.getInt("id"),result.getString("name"),result.getString("password"),result.getString("login"),result.getString("salt"));
            }
        }
    }

    public void res(String resource, Role role) throws SQLException {
        Auth.logger.debug("Checking access");
        try (PreparedStatement statement = connection.prepareStatement("SELECT id FROM resource WHERE name = ?")) {
            statement.setString(1, resource);

            try (ResultSet result = statement.executeQuery()) {

                resAccess = result.first();
                if (!resAccess) {
                    // access denied
                    return;
                }

                int resource_id = result.getInt("id");
                this.resource = new Resource(resource_id, resource, null);

                checkAccess(resource_id, role);
            }
        }
    }

    public void checkAccess(int resource_id, Role role) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS count FROM res_user WHERE res_id = ? AND auth_id = ? AND Role = ?")) {
            statement.setInt(1, resource_id);
            statement.setInt(2, user.getId());
            statement.setString(3, role.getValue());

            try (ResultSet result = statement.executeQuery()) {
                result.first();

                if (result.getInt("count") == 0){
                    try (PreparedStatement statementParentResource = connection.prepareStatement("SELECT id_parent FROM resource WHERE id = ?")) {
                        statementParentResource.setInt(1, resource_id);

                        try (ResultSet resultParentResource = statementParentResource.executeQuery()) {
                            resultParentResource.first();

                            int resource_id_parent = resultParentResource.getInt("id_parent");

                            if (!resultParentResource.wasNull()) {
                                checkAccess(resource_id_parent, role);
                            } else {
                                resAccess = false;
                            }
                        }
                    }
                } else {
                    resAccess = true;
                }
            }
        }
    }

    public void checkDate(String firstDate, String lastDate) {
        try {
            dateIn = df.parse(firstDate);
            dateOut = df.parse(lastDate);
            correctDate = true;
        } catch (java.text.ParseException e) {
            correctDate = false;
        }
    }

    public void insertToAccau(String role) throws SQLException {
        Auth.logger.debug("Writing to Accounting");
        try (PreparedStatement insertToAcc = connection.prepareStatement("INSERT INTO accau(Role, Auth_id, Date_in, Date_out, volume, res_id) " +
                "VALUES (?,?,?,?,?,?)")) {
            insertToAcc.setString(1, role);
            insertToAcc.setInt(2, user.getId());
            insertToAcc.setDate(3, new java.sql.Date(dateIn.getTime()));
            insertToAcc.setDate(4, new java.sql.Date(dateOut.getTime()));
            insertToAcc.setInt(5, volume);
            insertToAcc.setInt(6, resource.getId());
            insertToAcc.executeUpdate();
        }
    }

    public void checkVolume(String str) {
        try {
            volume = Integer.parseInt(str);
            correctVolume = true;
        } catch (NumberFormatException e) {
            correctVolume = false;
        }
    }

    public boolean isCorrectVolume() {
        return correctVolume;
    }

    public boolean isCorrectLogin() {
        return correctLogin;
    }

    public boolean isCorrectDate() {
        return correctDate;
    }

    public boolean isResAccess() {
        return resAccess;
    }

    public boolean isCorrectPass() {
        return correctPass;
    }

}

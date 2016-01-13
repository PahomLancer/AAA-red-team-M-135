package main;

//import main.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Auth {
    private static final Logger logger = LogManager.getLogger(Auth.class);
    Connection connection;
    resource resource;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private boolean resAccess = false;
    private boolean correctVolume = false;
    private boolean correctDate = false;
    private boolean correctPass = false;
    private boolean correctLogin = false;
    public java.util.Date dateIn;
    public java.util.Date dateOut;
    int volume;
    user user;
    public Auth() throws SQLException {
        Auth.logger.debug("Establishing connection with database");
        connection = getConnection();
        df.setLenient(false);
        Auth.logger.debug("Migration");

        Flyway flyway = new Flyway();

        flyway.setDataSource("jdbc:h2:./m135-red", "sa", "");

        flyway.migrate();
    }
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:./m135-red", "sa", "");
    }
    public static String md5(String input) {
        String md5 = null;
        if (null == input) return null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(input.getBytes(), 0, input.length());

            md5 = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }
        return md5;
    }

    public void Authentication(String login, String pass) throws SQLException {
        Auth.logger.debug("Checking Authentication");
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
                user = new user(result.getInt("id"),result.getString("name"),result.getString("password"),result.getString("login"),result.getString("salt"));
            }
        }
    }

    public void res(String resource, role role) throws SQLException {
        Auth.logger.debug("Checking access");
        try (PreparedStatement statement = connection.prepareStatement("SELECT id FROM resource WHERE name = ?")) {
            statement.setString(1, resource);
            try (ResultSet result = statement.executeQuery()) {
                resAccess = result.first();
                if (!resAccess) {
                    // Äîñòóï çàïðåùåí
                    return;
                }

                int resource_id = result.getInt("id");
                this.resource = new resource(resource_id, resource, null);

                // Ïðîâåðÿåì äîñòóï
                checkAccess(resource_id, role);
            }
        }
    }

    public void checkAccess(int resource_id, role role) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS count FROM res_user WHERE res_id = ? AND Auth_id = ? AND Role = ?")) {
            statement.setInt(1, resource_id);
            statement.setInt(2, user.returnId());
            statement.setString(3, role.returnValue());
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
            insertToAcc.setInt(2, user.returnId());
            insertToAcc.setDate(3, new java.sql.Date(dateIn.getTime()));
            insertToAcc.setDate(4, new java.sql.Date(dateOut.getTime()));
            insertToAcc.setInt(5, volume);
            insertToAcc.setInt(6, resource.returnId());
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
//R1-R2 version
/*
public class Auth{
	int id = 0;
	public String login;
	private String password;
	private String salt = "qa12ws34ed56rf78tg90";

	public void setUser(String login, String password){
		this.login = login;
		//this.password = password;
		this.password = hash.makeHash(password, salt);
		this.id = 0;
	}

	public int checkUser(Auth user){
		if (user.login.equals(this.login))
		{
			if (user.password.equals(this.password))
			{
				return 1;
			}
			return 2;
		}
		return 3;
	}

	public Auth getUser(Auth user) {
        user.id = this.id;
        user.password = this.password;
        return user;
    }

	public void printUser()
	{
		System.out.println(this.id + " " + this.login + " " + this.password);
	}
}
*/

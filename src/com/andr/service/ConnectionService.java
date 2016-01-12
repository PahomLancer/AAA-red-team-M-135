package com.andr.service;

import com.andr.domain.Activity;
import com.andr.domain.Authority;
import com.andr.domain.Role;
import com.andr.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

//opredelenie klassa podkucheniya
public class ConnectionService {
    private static final Logger log = LogManager.getLogger(ConnectionService.class);
    private final String url = "jdbc:h2:./aaa";
    private final String user = "sa";
    private final String password = null;
    private Connection connection;

    public void connect() {
        log.info("Connecting to DB");
        try {
            migrate();
            connection = getConnection();
        } catch (SQLException e) {
            log.error("Cannot connect to DB", e);
            throw new RuntimeException("Suppress exception", e);
        }
    }

    //otklychenie
    public void disconnect() {
        log.info("Disconnecting from DB");
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("Cannot disconnect from DB", e);
                throw new RuntimeException("Suppress exception", e);
            }
        }
    }
    //migraciya
    public void migrate() {
        log.info("Trying to migrate DB");
        // Create the Flyway instance
        Flyway flyway = new Flyway();

        // Point it to the database
        flyway.setDataSource(url, user, password);

        // Start the migration
        try {
            flyway.migrate();
        } catch (FlywayException e) {
            log.error("Cannot migrate DB", e);
            throw e;
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public User getUserByLogin(String user) {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT * FROM user WHERE login = ?");
            pstmt.setString(1, user);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("salt"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            log.error("Cannot execute query", e);
            throw new RuntimeException("Suppress exception", e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    // Do nothing
                }
            }
        }
    }

    public List<Authority> getAuthoritiesByUserAndRole(User user, Role role) {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT * FROM authority WHERE user_id = ? and role = ?");
            pstmt.setLong(1, user.getId());
            pstmt.setString(2, role.name());
            ResultSet rs = pstmt.executeQuery();
            List<Authority> authorities = new LinkedList<>();
            while (rs.next()) {
                authorities.add(new Authority(
                        rs.getLong("id"),
                        user, // No need to request same user again
                        Role.getRole(rs.getString("role")),
                        rs.getString("site")));
            }
            return authorities;
        } catch (SQLException e) {
            log.error("Cannot execute query", e);
            throw new RuntimeException("Suppress exception", e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    // Do nothing
                }
            }
        }
    }

    public void addActivity(Activity activity) {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(
                    "INSERT INTO activity (authority_id, login_date, logout_date, volume) VALUES (?,?,?,?)");
            pstmt.setLong(1, activity.getAuthority().getId());
            pstmt.setDate(2, new java.sql.Date(activity.getLoginDate().getTime()));
            pstmt.setDate(3, new java.sql.Date(activity.getLogoutDate().getTime()));
            pstmt.setLong(4, activity.getVolume());
            pstmt.execute();
        } catch (SQLException e) {
            log.error("Cannot execute query", e);
            throw new RuntimeException("Suppress exception", e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    // Do nothing
                }
            }
        }
    }

    public Long countActivity() {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(
                    "SELECT COUNT(*) FROM activity");
            ResultSet rs = pstmt.executeQuery();
            rs.next(); // There is always one row
            return rs.getLong(1);
        } catch (SQLException e) {
            log.error("Cannot execute query", e);
            throw new RuntimeException("Suppress exception", e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    // Do nothing
                }
            }
        }
    }
}

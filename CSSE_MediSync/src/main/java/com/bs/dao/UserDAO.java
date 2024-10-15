package com.bs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import com.bs.interfaces.IUserDAO;
import com.bs.model.User;
import com.bs.utility.DBConnection;

public class UserDAO implements IUserDAO {
    private Connection connection;

    // SQL statements as private static final fields
    private static final String INSERT_USER_SQL = "INSERT INTO users (username, password_hash) VALUES (?, ?)";
    private static final String SELECT_USER_SQL = "SELECT user_id, username, password_hash FROM users WHERE username = ?";

    public UserDAO() {
        connection = DBConnection.getConnection();
    }

    @Override
    public void registerUser(User user) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(INSERT_USER_SQL)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.executeUpdate();
        }
    }

    @Override
    public User loginUser(String username, String password) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_USER_SQL)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                
                // Validate password using BCrypt
                if (BCrypt.checkpw(password, storedHash)) {
                    int userId = rs.getInt("user_id");
                    String dbUsername = rs.getString("username");
                    return new User(userId, dbUsername, storedHash);
                }
            }
        }
        return null;
    }
}

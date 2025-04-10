package com.bs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bs.interfaces.IUserDAO;
import com.bs.model.User;
import com.bs.utility.DBConnection;
import org.mindrot.jbcrypt.BCrypt;

public class UserDAO implements IUserDAO {
    private Connection connection;

    public UserDAO() {
        connection = DBConnection.getConnection();
    }

    @Override
    public int registerUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password_hash) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.executeUpdate();

            // Retrieve generated keys (user ID)
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Return the generated user ID
                } else {
                    throw new SQLException("Registration failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public User loginUser(String username, String password) throws SQLException {
        String sql = "SELECT user_id, username, password_hash FROM users WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
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
        return null; // Return null if login fails
    }
}

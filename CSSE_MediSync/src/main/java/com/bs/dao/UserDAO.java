package com.bs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bs.interfaces.IUserDAO;
import com.bs.model.User;
import com.bs.utility.DBConnection;

public class UserDAO implements IUserDAO {
    private Connection connection;

    public UserDAO() {
        connection = DBConnection.getConnection();
    }

    @Override
    public void registerUser(User user) throws SQLException {
        // Updated SQL statement without the role field
        String sql = "INSERT INTO users (username, password_hash) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            // Role is no longer needed
            ps.executeUpdate();
        }
    }

    @Override
    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Updated User constructor call without the role field
                return new User(rs.getInt("user_id"), rs.getString("username"),
                        rs.getString("password_hash"));
            }
            return null;
        }
    }
}

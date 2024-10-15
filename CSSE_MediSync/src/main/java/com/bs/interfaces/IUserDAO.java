package com.bs.interfaces;

import java.sql.SQLException;

import com.bs.model.User;

public interface IUserDAO {
	   void registerUser(User user) throws SQLException;
	   User loginUser(String username, String password) throws SQLException;
}

package com.bs.interfaces;

import java.sql.SQLException;

import com.bs.model.User;

public interface IUserDAO {
	   int registerUser(User user) throws SQLException;
	   User loginUser(String username, String password) throws SQLException;
}

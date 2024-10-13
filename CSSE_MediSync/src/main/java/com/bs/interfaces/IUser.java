package com.bs.interfaces;

import java.sql.SQLException;

import com.bs.model.User;

public interface IUser {
	   void registerUser(User user) throws SQLException;
	    User getUserByUsername(String username) throws SQLException;
}

package com.bs.model;

public class User {
	private int userId;
	private String username;
	private String passwordHash;
	private String role;

	public User(int userId, String username, String passwordHash, String role) {
		super();
		this.userId = userId;
		this.username = username;
		this.passwordHash = passwordHash;
		this.role = role;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}

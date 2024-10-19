package com.bs.dao;

import com.bs.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    private UserDAO userDAO;

    @BeforeEach
    void setUp() throws SQLException {
        userDAO = new UserDAO();
    }

    // Positive Test: Successfully register a new user
    @Test
    void testRegisterUserSuccessfully() throws SQLException {
        User user = new User("Jayath", BCrypt.hashpw("Jayath@13131", BCrypt.gensalt()));
        userDAO.registerUser(user);

        // Attempt to login with the same credentials
        User registeredUser = userDAO.loginUser("Jayath", "Jayath@13131");
        assertNotNull(registeredUser, "Registered user should not be null");
        assertEquals("Jayath", registeredUser.getUsername(), "Usernames should match");
    }

    // Negative Test: Attempt to register a user with a duplicate username
    @Test
    void testRegisterUserWithDuplicateUsername() throws SQLException {
        User user1 = new User("Jayath", BCrypt.hashpw("Jayath@13131", BCrypt.gensalt()));
        User user2 = new User("Jayath", BCrypt.hashpw("Jayath@13131", BCrypt.gensalt()));

        userDAO.registerUser(user1); // Register first user

        boolean exceptionThrown = false;

        // Attempt to register the second user with the same username
        try {
            userDAO.registerUser(user2);
        } catch (SQLException e) {
            exceptionThrown = true;
            assertTrue(e.getMessage().contains("Duplicate entry"), "Exception message should indicate duplicate entry");
        }

        // Assert that the exception was thrown
        assertTrue(exceptionThrown, "Registering duplicate username should throw SQLException");
    }

    // Positive Test: Login user successfully with correct credentials
    @Test
    void testLoginExistingUserSuccessfully() throws SQLException {
        // Assuming 'Yohan' is already in the database with the password 'pwd123yoh'
        User loggedInUser = userDAO.loginUser("Yohan", "pwd123yoh");
        assertNotNull(loggedInUser, "Logged in user should not be null");
        assertEquals("Yohan", loggedInUser.getUsername(), "Usernames should match");
    }

    // Negative Test: Login user with incorrect password
    @Test
    void testLoginExistingUserWithIncorrectPassword() throws SQLException {
        // Assuming 'Yohan' is already in the database with the password 'pwd123yoh'
        User loggedInUser = userDAO.loginUser("Yohan", "pwd1234yoh");
        assertNull(loggedInUser, "Login with incorrect password should fail");
    }

    // Negative Test: Login user with non-existent username
    @Test
    void testLoginNonExistentUser() throws SQLException {
        User loggedInUser = userDAO.loginUser("Kamal", "pwd123Kam");
        assertNull(loggedInUser, "Login should fail for non-existent user");
    }
}

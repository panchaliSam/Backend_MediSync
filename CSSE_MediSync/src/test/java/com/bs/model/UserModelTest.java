package com.bs.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserModelTest {

    // Positive Test: Creating a new user without ID
    @Test
    public void testNewUserCreationWithoutId() {
        User user = new User("testUser", "hashedPassword");
        Assertions.assertEquals("testUser", user.getUsername());
        Assertions.assertEquals("hashedPassword", user.getPasswordHash());
        Assertions.assertEquals(0, user.getUserId());  
    }

    // Positive Test: Creating a user with ID (simulates a user retrieved from the database with an auto-assigned ID)
    @Test
    public void testUserCreationWithId() {
        User user = new User(1, "testUser", "hashedPassword");
        Assertions.assertEquals(1, user.getUserId());  // ID is set after database insertion
        Assertions.assertEquals("testUser", user.getUsername());
        Assertions.assertEquals("hashedPassword", user.getPasswordHash());
    }

    // Positive Test: Test setters for username and password
    @Test
    public void testSettersAndGetters() {
        User user = new User("testUser", "hashedPassword");
        user.setUsername("newUsername");
        user.setPasswordHash("newHashedPassword");

        Assertions.assertEquals("newUsername", user.getUsername());
        Assertions.assertEquals("newHashedPassword", user.getPasswordHash());
    }


    // Negative Test: Creating a user with a null username
    @Test
    public void testUserCreationWithNullUsername() {
        User user = new User(null, "hashedPassword");

        // Check that the username is null
        Assertions.assertNull(user.getUsername(), "The username should be null when null is passed.");
    }

    // Negative Test: Creating a user with null passwordHash
    @Test
    public void testUserCreationWithNullPasswordHash() {
        User user = new User("testUser", null);

        // Check that the password hash is null
        Assertions.assertNull(user.getPasswordHash(), "The password hash should be null when null is passed.");
    }
    
    //Negative Test: Invalid user creation
    @Test
    public void testInvalidUserCreation() {
        try {
            User user = new User("", "");  // Invalid user creation, expecting some failure
            
            // This line should not be reached if validation fails
            Assertions.fail("User creation with empty username and password should have failed.");
        } catch (IllegalArgumentException e) {
            // Expected an IllegalArgumentException to be thrown
        	Assertions.assertTrue(true);
        }
    }



}

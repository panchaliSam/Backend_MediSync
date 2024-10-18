package com.bs.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.bs.model.User;
import com.bs.dao.UserDAO;
import com.bs.interfaces.IUserDAO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.mindrot.jbcrypt.BCrypt;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import com.bs.utility.CorsUtil;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IUserDAO iUserDAO = new UserDAO();
    private Gson gson = new Gson();

    public UserServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CorsUtil.addCorsHeaders(response);
        response.setContentType("application/json");

        String action = request.getParameter("action");
        if (action == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Action is required\"}");
            return;
        }

        // Read JSON body
        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }
        String jsonData = jsonBuffer.toString();
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        String username = jsonObject.get("username").getAsString();
        String password = jsonObject.get("password").getAsString();

        if (username == null || password == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Username and password are required\"}");
            return;
        }

        if (action.equals("register")) {
            // Register user logic
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            User user = new User(username, hashedPassword);

            try {
                // Call registerUser and get the generated user ID
                int userId = iUserDAO.registerUser(user);
                JsonObject responseJson = new JsonObject();
                responseJson.addProperty("message", "User registered successfully");
                responseJson.addProperty("userId", userId); // Include user ID in the response
                response.getWriter().write(responseJson.toString());
            } catch (SQLException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\": \"Error registering user: " + e.getMessage() + "\"}");
            }
        } else if (action.equals("login")) {
            // Login logic
            try {
                User user = iUserDAO.loginUser(username, password);
                if (user != null) {
                    JsonObject responseJson = new JsonObject();
                    responseJson.addProperty("message", "Login successful");
                    responseJson.addProperty("userId", user.getUserId()); // Include user ID
                    responseJson.addProperty("username", user.getUsername()); // Optionally include username
                    response.getWriter().write(responseJson.toString());
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("{\"error\": \"Invalid username or password\"}");
                }
            } catch (SQLException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\": \"An error occurred: " + e.getMessage() + "\"}");
            }
        }
    }


    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CorsUtil.addCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}

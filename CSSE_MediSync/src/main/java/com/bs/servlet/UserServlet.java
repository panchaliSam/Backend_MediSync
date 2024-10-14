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

@WebServlet("/users")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IUserDAO iUserDAO = new UserDAO();
    private Gson gson = new Gson();

    public UserServlet() {
        super();
    }

    private void addCorsHeaders(HttpServletResponse response) {
        // Allow requests from your frontend origin
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        addCorsHeaders(response); 
        String action = request.getParameter("action");
        response.setContentType("application/json");

        if (action == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Action is required\"}");
        } else if (action.equals("getUser")) {
            String username = request.getParameter("username");
            if (username == null || username.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Username is required\"}");
                return;
            }

            try {
                User user = iUserDAO.getUserByUsername(username);
                if (user != null) {
                    response.getWriter().write(gson.toJson(user));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\": \"User not found\"}");
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\": \"An error occurred: " + e.getMessage() + "\"}");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        addCorsHeaders(response); 
        response.setContentType("application/json");

        // Read JSON body from the request
        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }
        
        // Parse JSON body
        String jsonData = jsonBuffer.toString();
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        String username = jsonObject.get("username").getAsString();
        String password = jsonObject.get("password").getAsString();
        
        if (username == null || password == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Username and password are required\"}");
            return;
        }

        // Hash password before storing
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(username, hashedPassword); 

        try {
            iUserDAO.registerUser(user);
            response.getWriter().write("{\"message\": \"User registered successfully\"}");
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Error registering user: " + e.getMessage() + "\"}");
        }
    }


    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        addCorsHeaders(response); 
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}

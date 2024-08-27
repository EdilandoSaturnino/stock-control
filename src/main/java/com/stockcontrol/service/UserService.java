package main.java.com.stockcontrol.service;

import main.java.com.stockcontrol.dao.UserDAO;
import main.java.com.stockcontrol.model.User;

import java.sql.SQLException;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public User authenticateUser(String username, String password) throws SQLException {
        return userDAO.getUser(username, password);
    }
}
package com.my_shop.pos.dao.custom.impl;

import com.my_shop.pos.dao.custom.UserDao;
import com.my_shop.pos.db.DbConnection;
import com.my_shop.pos.dto.UserDto;
import com.my_shop.pos.entity.User;
import com.my_shop.pos.util.PasswordManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public boolean saveUser(User user) throws SQLException, ClassNotFoundException {
        String SQL = "INSERT INTO user VALUES (?,?)";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(SQL);
        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, PasswordManager.encryptPassword(user.getPassword()));
        return preparedStatement.executeUpdate() > 0;
    }

    @Override
    public boolean updateUser(User user) throws SQLException, ClassNotFoundException {
        String SQL = "UPDATE user SET password=? WHERE email=?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(SQL);
        preparedStatement.setString(1, PasswordManager.encryptPassword(user.getPassword()));
        preparedStatement.setString(2, user.getEmail());
        return preparedStatement.executeUpdate() > 0;
    }

    @Override
    public boolean deleteUser(String email) throws SQLException, ClassNotFoundException {
        String SQL = "DELETE FROM user WHERE email=?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(SQL);
        preparedStatement.setString(1, email);
        return preparedStatement.executeUpdate() > 0;
    }

    @Override
    public User findUser(String email) throws SQLException, ClassNotFoundException {
        String SQL = "SELECT * FROM user WHERE email=?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(SQL);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new User(
                    resultSet.getString(1),
                    resultSet.getString(2)
            );
        }
        return null;
    }

    @Override
    public List<User> findAllUser() throws SQLException, ClassNotFoundException {
        String SQL = "SELECT * FROM user";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(SQL);
        List<User> users = new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            users.add(new User(
                    resultSet.getString(1),
                    resultSet.getString(2)
            ));
        }
        return users;
    }
}

package com.my_shop.pos.dao;

import com.my_shop.pos.db.DbConnection;
import com.my_shop.pos.dto.CustomerDto;
import com.my_shop.pos.dto.UserDto;
import com.my_shop.pos.util.PasswordManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAccessCode {
    public static boolean createUser(
            String email, String password
    ) throws ClassNotFoundException, SQLException {

        String SQL = "INSERT INTO user VALUES (?,?)";
                PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(SQL);

        preparedStatement.setString(1, email);
        preparedStatement.setString(2, PasswordManager.encryptPassword(password));

        return preparedStatement.executeUpdate() > 0;
    }


    public static UserDto findUser(String email) throws ClassNotFoundException, SQLException {

        String SQL = "SELECT * FROM user WHERE email=?";
                PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(SQL);

        preparedStatement.setString(1, email);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return new UserDto(
                    resultSet.getString(1),
                    resultSet.getString(2)
            );
        }
        return null;
    }


    public static boolean createCustomer(
            String email,
            String name,
            String contact,
            double salary
    ) throws ClassNotFoundException, SQLException {

        String SQL = "INSERT INTO customer VALUES (?,?,?,?)";
                PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(SQL);

        preparedStatement.setString(1, email);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, contact);
        preparedStatement.setDouble(4, salary);

        return preparedStatement.executeUpdate() > 0;
    }


    public static boolean updateCustomer(
            String email,
            String name,
            String contact,
            double salary
    ) throws ClassNotFoundException, SQLException {

        String SQL = "UPDATE customer SET name=?, contact=?, salary=? WHERE email=?";
                PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(SQL);

        preparedStatement.setString(1, name);
        preparedStatement.setString(2, contact);
        preparedStatement.setDouble(3, salary);
        preparedStatement.setString(4, email);

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean deleteCustomer(
            String email
    ) throws ClassNotFoundException, SQLException {

        String SQL = "DELETE FROM customer WHERE email=?";
                PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(SQL);

        preparedStatement.setString(1, email);
        return preparedStatement.executeUpdate() > 0;
    }

    public static CustomerDto findCustomer(
            String email
    ) throws ClassNotFoundException, SQLException {

        String SQL = "SELECT * FROM customer WHERE email=?";
                PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(SQL);

        preparedStatement.setString(1, email);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return new CustomerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            );
        }
        return null;
    }


    public static List<CustomerDto> findAllCustomer() throws ClassNotFoundException, SQLException {

        String SQL = "SELECT * FROM customer";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(SQL);


        ResultSet resultSet = preparedStatement.executeQuery();

        List<CustomerDto> customerDtos = new ArrayList<>();
        while (resultSet.next()) {
            customerDtos.add(new CustomerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            ));
        }
        return customerDtos;
    }


    public static List<CustomerDto> searchCustomer(
            String searchText
    ) throws ClassNotFoundException, SQLException {

        searchText = "%" + searchText + "%";

        String SQL = "SELECT * FROM customer WHERE email LIKE ? || name LIKE ?";
                PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(SQL);

        preparedStatement.setString(1, searchText);
        preparedStatement.setString(2, searchText);

        ResultSet resultSet = preparedStatement.executeQuery();

        List<CustomerDto> customerDtos = new ArrayList<>();
        while (resultSet.next()) {
            customerDtos.add(new CustomerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            ));
        }
        return customerDtos;
    }


}

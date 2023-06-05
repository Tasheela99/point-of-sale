package com.my_shop.pos.dao;

import com.my_shop.pos.dao.custom.CustomerDao;
import com.my_shop.pos.dao.custom.ProductDao;
import com.my_shop.pos.dao.custom.UserDao;
import com.my_shop.pos.dao.custom.impl.CustomerDaoImpl;
import com.my_shop.pos.dao.custom.impl.ProductDaoImpl;
import com.my_shop.pos.dao.custom.impl.UserDaoImpl;
import com.my_shop.pos.db.DbConnection;
import com.my_shop.pos.dto.CustomerDto;
import com.my_shop.pos.dto.ProductDto;
import com.my_shop.pos.dto.UserDto;
import com.my_shop.pos.entity.Customer;
import com.my_shop.pos.entity.Product;
import com.my_shop.pos.entity.User;
import com.my_shop.pos.util.PasswordManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAccessCode {

    CustomerDao customerDao = new CustomerDaoImpl();
    ProductDao productDao = new ProductDaoImpl();
    UserDao userDao = new UserDaoImpl();

    //===========================================user=======================================================
    public boolean createUser(String email, String password) throws ClassNotFoundException, SQLException {
        return userDao.saveUser(
                new User(email, password)
        );
    }

    public UserDto findUser(String email) throws ClassNotFoundException, SQLException {

        String SQL = "SELECT * FROM user WHERE email=?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(SQL);

        preparedStatement.setString(1, email);

        User user = userDao.findUser(email);

        if (user != null) {
            return new UserDto(
                    user.getEmail(),
                    user.getPassword()
            );
        }
        return null;
    }
    //===========================================user=======================================================


    //===========================================customer=======================================================

    public boolean createCustomer(String email, String name, String contact, double salary) throws ClassNotFoundException, SQLException {
        return customerDao.saveCustomer(new Customer(
                email,
                name,
                contact,
                salary
        ));
    }

    public boolean updateCustomer(String email, String name, String contact, double salary) throws ClassNotFoundException, SQLException {
        return customerDao.updateCustomer(
                new Customer(
                        email,
                        name,
                        contact,
                        salary
                ));
    }

    public boolean deleteCustomer(String email) throws ClassNotFoundException, SQLException {

        return customerDao.deleteCustomer(email);
    }

    public CustomerDto findCustomer(String email) throws ClassNotFoundException, SQLException {
        Customer customers = customerDao.findCustomer(email);

        if (customers != null) {
            return new CustomerDto(
                    customers.getEmail(),
                    customers.getName(),
                    customers.getContact(),
                    customers.getSalary()
            );
        }
        return null;
    }

    public List<CustomerDto> findAllCustomer() throws ClassNotFoundException, SQLException {

        List<CustomerDto> customerDtos = new ArrayList<>();
        for (Customer customer : customerDao.findAllCustomer()) {
            if (customer != null) {
                customerDtos.add(new CustomerDto(
                        customer.getEmail(),
                        customer.getName(),
                        customer.getContact(),
                        customer.getSalary()
                ));
            }
        }
        return customerDtos;
    }

    public List<CustomerDto> searchCustomer(String searchText) throws ClassNotFoundException, SQLException {

        searchText = "%" + searchText + "%";
        List<CustomerDto> customerDtos = new ArrayList<>();
        for (Customer customer : customerDao.searchCustomers(searchText)
        ) {
            customerDtos.add(new CustomerDto(
                    customer.getEmail(),
                    customer.getName(),
                    customer.getContact(),
                    customer.getSalary()
            ));
        }
        return customerDtos;
    }
    //===========================================customer=======================================================


    //===========================================product=======================================================
    public boolean createProduct(int code, String description) throws SQLException, ClassNotFoundException {
        return productDao.saveProduct(
                new Product(
                        code,
                        description
                )
        );
    }

    public boolean updateProduct(int code, String description) throws SQLException, ClassNotFoundException {
        return productDao.updateProduct(
                new Product(
                        code,
                        description
                )
        );
    }

    public boolean deleteProduct(int code) throws SQLException, ClassNotFoundException {
        String SQL = "DELETE FROM product WHERE code=?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(SQL);

        preparedStatement.setInt(1, code);
        return preparedStatement.executeUpdate() > 0;
    }

    public ProductDto searchProduct(int code) throws SQLException, ClassNotFoundException {
        String SQL = "SELECT * FROM product WHERE code=?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(SQL);

        preparedStatement.setInt(1, code);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return new ProductDto(
                    resultSet.getInt(1),
                    resultSet.getString(2)

            );
        }
        return null;
    }

    public List<ProductDto> findAllProducts() throws SQLException, ClassNotFoundException {
        String SQL = "SELECT * FROM product";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(SQL);


        ResultSet resultSet = preparedStatement.executeQuery();

        List<ProductDto> productDtos = new ArrayList<>();
        while (resultSet.next()) {
            productDtos.add(new ProductDto(
                    resultSet.getInt(1),
                    resultSet.getString(2)
            ));
        }
        return productDtos;
    }

    public List<ProductDto> searchProduct(String searchText) throws SQLException, ClassNotFoundException {
        searchText = "%" + searchText + "%";

        String SQL = "SELECT * FROM product WHERE code LIKE ? || description LIKE ?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(SQL);

        preparedStatement.setString(1, searchText);
        preparedStatement.setString(2, searchText);

        ResultSet resultSet = preparedStatement.executeQuery();

        List<ProductDto> productDtos = new ArrayList<>();
        while (resultSet.next()) {
            productDtos.add(new ProductDto(
                    resultSet.getInt(1),
                    resultSet.getString(2)

            ));
        }
        return productDtos;
    }

    public int getLastProductId() throws SQLException, ClassNotFoundException {
        return productDao.getLastProductId();
    }
    //===========================================product=======================================================


}

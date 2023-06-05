package com.my_shop.pos.dao.custom.impl;

import com.my_shop.pos.dao.custom.ProductDao;
import com.my_shop.pos.db.DbConnection;
import com.my_shop.pos.dto.ProductDto;
import com.my_shop.pos.entity.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    @Override
    public boolean saveProduct(Product product) throws SQLException, ClassNotFoundException {
        String SQL = "INSERT INTO product VALUES (?,?)";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(SQL);

        preparedStatement.setInt(1, product.getCode());
        preparedStatement.setString(2, product.getDescription());

        return preparedStatement.executeUpdate() > 0;
    }

    @Override
    public boolean updateProduct(Product product) throws SQLException, ClassNotFoundException {
        String SQL = "UPDATE product SET description=? WHERE code=?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(SQL);
        preparedStatement.setString(1, product.getDescription());
        preparedStatement.setInt(2, product.getCode());

        return preparedStatement.executeUpdate() > 0;
    }

    @Override
    public boolean deleteProduct(int code) throws SQLException, ClassNotFoundException {
        String SQL = "DELETE FROM product WHERE code=?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(SQL);
        preparedStatement.setInt(1, code);
        return preparedStatement.executeUpdate() > 0;
    }

    @Override
    public Product findProduct(int code) throws SQLException, ClassNotFoundException {
        String SQL = "SELECT * FROM product WHERE code=?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(SQL);

        preparedStatement.setInt(1, code);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return new Product(
                    resultSet.getInt(1),
                    resultSet.getString(2)

            );
        }
        return null;
    }

    @Override
    public List<Product> findAllProduct() throws SQLException, ClassNotFoundException {
        String SQL = "SELECT * FROM product";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(SQL);


        ResultSet resultSet = preparedStatement.executeQuery();

        List<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            products.add(new Product(
                    resultSet.getInt(1),
                    resultSet.getString(2)
            ));
        }
        return products;
    }

    @Override
    public int getLastProductId() throws SQLException, ClassNotFoundException {
        String SQL = "SELECT code FROM product ORDER BY code DESC LIMIT 1";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(SQL);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return (resultSet.getInt(1) + 1);
        }
        return 0;
    }
}

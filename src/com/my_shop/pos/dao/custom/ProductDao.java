package com.my_shop.pos.dao.custom;

import com.my_shop.pos.entity.Customer;
import com.my_shop.pos.entity.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

    public boolean saveProduct(Product product) throws SQLException, ClassNotFoundException;
    public boolean updateProduct(Product product) throws SQLException, ClassNotFoundException;
    public boolean deleteProduct(int code) throws SQLException, ClassNotFoundException;
    public Product findProduct(int code) throws SQLException, ClassNotFoundException;
    public List<Product> findAllProduct() throws SQLException, ClassNotFoundException;

    public int getLastProductId() throws SQLException, ClassNotFoundException;
}

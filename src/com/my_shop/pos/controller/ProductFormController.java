package com.my_shop.pos.controller;

import com.my_shop.pos.dao.DatabaseAccessCode;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ProductFormController {
    public TextField txtProductCode;
    public Button btnSaveUpdate;
    public TextArea txtProductDescription;

    private String searchText;

    public void initialize() {
        loadNewProductId();
    }

    private void loadNewProductId() {
        try {
            txtProductCode.setText(String.valueOf(new DatabaseAccessCode().getLastProductId()));
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void addNewProductBtnOnAction(ActionEvent actionEvent) {
    }

    public void saveProductBtnOnAction(ActionEvent actionEvent) {

        try {
            if (btnSaveUpdate.getText().equals("Save Product")) {
                if (new DatabaseAccessCode().createProduct(
                        Integer.parseInt(txtProductCode.getText()),
                        txtProductDescription.getText()
                )) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Product Saved Successfully");
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again");

                }
            } else {
                if (new DatabaseAccessCode().updateProduct(
                        Integer.parseInt(txtProductCode.getText()),
                        txtProductDescription.getText()
                )) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Product Updated Successfully");
                    clearFields();
                    loadAllProducts(searchText);
                    btnSaveUpdate.setText("Save Product");
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again");

                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();

        }
    }

    private void loadAllProducts(String searchText) {
    }

    private void clearFields() {
        txtProductDescription.clear();
    }
}
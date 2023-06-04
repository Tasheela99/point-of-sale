package com.my_shop.pos.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CustomerFormController {
    public AnchorPane context;
    public TextField txtEmail;
    public TextField txtName;
    public TextField txtContact;
    public TextField txtSalary;
    public Button btnSaveUpdate;
    public TextField txtSearch;
    public TableView tbl;
    public TableColumn colId;
    public TableColumn colEmail;
    public TableColumn colName;
    public TableColumn colContact;
    public TableColumn colSalary;
    public TableColumn colOperate;

    public void btnSaveUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnBackToHomeOnAction(ActionEvent actionEvent) {
    }

    public void btnLoyaltyCardOnAction(ActionEvent actionEvent) {
    }

    public void btnAddNewCustomerOnAction(ActionEvent actionEvent) {
    }
}

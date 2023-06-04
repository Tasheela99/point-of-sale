package com.my_shop.pos.controller;

import com.my_shop.pos.dao.DatabaseAccessCode;
import com.my_shop.pos.dto.CustomerDto;
import com.my_shop.pos.view.tm.CustomerTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Optional;

public class CustomerFormController {
    public AnchorPane context;
    public TextField txtEmail;
    public TextField txtName;
    public TextField txtContact;
    public TextField txtSalary;
    public Button btnSaveUpdate;
    public TextField txtSearch;
    public TableView<CustomerTm> tbl;
    public TableColumn colId;
    public TableColumn colEmail;
    public TableColumn colName;
    public TableColumn colContact;
    public TableColumn colSalary;
    public TableColumn colOperate;


    private String searchText = "";


    public void initialize() throws SQLException, ClassNotFoundException {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colOperate.setCellValueFactory(new PropertyValueFactory<>("deleteBtn"));

        loadAllCustomers(searchText);

        tbl.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setData(newValue);
            }
        });
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = newValue;
            try {
                loadAllCustomers(searchText);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setData(CustomerTm newValue) {
        txtEmail.setEditable(false);
        btnSaveUpdate.setText("Update Customer");
        txtEmail.setText(newValue.getEmail());
        txtName.setText(newValue.getName());
        txtContact.setText(newValue.getContact());
        txtSalary.setText(String.valueOf(newValue.getSalary()));
    }

    private void loadAllCustomers(String searchText) throws SQLException, ClassNotFoundException {
        ObservableList<CustomerTm> observableList = FXCollections.observableArrayList();
        int counter = 1;
        for (CustomerDto customerDto : searchText.length() > 0 ? DatabaseAccessCode.searchCustomer(searchText) : DatabaseAccessCode.findAllCustomer()) {
            Button button = new Button("Delete");
            CustomerTm tm = new CustomerTm(
                    counter,
                    customerDto.getEmail(),
                    customerDto.getName(),
                    customerDto.getContact(),
                    customerDto.getSalary(),
                    button

            );
            observableList.add(tm);
            counter++;

            button.setOnAction((e)->{

                try{
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are YOU sure", ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> selectedButtonType = alert.showAndWait();
                    if (selectedButtonType.equals(ButtonType.YES)){
                        if (DatabaseAccessCode.deleteCustomer(
                                txtEmail.getText()
                        )) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Customer Deleted Successfully");
                            loadAllCustomers(searchText);
                        } else {
                            new Alert(Alert.AlertType.WARNING, "Try Again");

                        }
                    }
                }catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();

                }

            });
        }
        tbl.setItems(observableList);
    }

    public void btnSaveUpdateOnAction(ActionEvent actionEvent) {
        try {
            if (btnSaveUpdate.getText().equals("Save Customer")) {
                if (DatabaseAccessCode.createCustomer(
                        txtEmail.getText(),
                        txtName.getText(),
                        txtContact.getText(),
                        Double.parseDouble(txtSalary.getText())
                )) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer Saved Successfully");
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again");

                }
            } else {
                if (DatabaseAccessCode.updateCustomer(
                        txtEmail.getText(),
                        txtName.getText(),
                        txtContact.getText(),
                        Double.parseDouble(txtSalary.getText())
                )) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer Updated Successfully");
                    clearFields();
                    loadAllCustomers(searchText);
                    txtEmail.setEditable(true);
                    btnSaveUpdate.setText("Save Customer");
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again");

                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();

        }
    }

    private void clearFields() {
        txtEmail.clear();
        txtName.clear();
        txtContact.clear();
        txtSalary.clear();
    }

    public void btnBackToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    public void btnLoyaltyCardOnAction(ActionEvent actionEvent) {
    }

    public void btnAddNewCustomerOnAction(ActionEvent actionEvent) {
        txtEmail.setEditable(true);
        btnSaveUpdate.setText("Save Customer");
        clearFields();
    }

    private void setUi(String url) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/" + url + ".fxml")))
        );

    }
}

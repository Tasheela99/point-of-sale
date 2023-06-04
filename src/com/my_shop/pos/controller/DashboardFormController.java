package com.my_shop.pos.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardFormController {
    public AnchorPane context;

    public void custmerManagementBtnOnAction(ActionEvent actionEvent) {
    }

    public void orderDetailsBtnOnAction(ActionEvent actionEvent) {
    }

    public void productManagementBtnOnAction(ActionEvent actionEvent) {
    }

    public void incomeReportBtnOnAction(ActionEvent actionEvent) {
    }

    public void placeOrderBtnOnAction(ActionEvent actionEvent) {
    }

    public void exitBtnOnAction(ActionEvent actionEvent) {
    }

    private void setUi(String url) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/" + url + ".fxml")))
        );
        stage.centerOnScreen();
    }
}

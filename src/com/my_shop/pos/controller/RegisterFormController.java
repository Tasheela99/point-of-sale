package com.my_shop.pos.controller;

import com.my_shop.pos.util.PasswordManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterFormController {


    public AnchorPane context;
    public TextField txtPassword;
    public TextField txtUsername;

    public void signUpBtnOnAction(ActionEvent actionEvent) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/my_shop?useSSL=false",
                    "root",
                    "Tsj123##"
            );
            String SQL = "INSERT INTO user VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, txtUsername.getText());
            preparedStatement.setString(2, PasswordManager.encryptPassword(txtPassword.getText()));

            if (preparedStatement.executeUpdate() > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "User Saved Successfully");
                clearFields();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void alreadyHaveAnAccountBtnOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }

    private void setUi(String url) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/" + url + ".fxml")))
        );
        stage.centerOnScreen();
    }

    private void clearFields(){
        txtUsername.clear();
        txtPassword.clear();
    }
}

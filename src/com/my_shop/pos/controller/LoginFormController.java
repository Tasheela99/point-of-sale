package com.my_shop.pos.controller;

import com.my_shop.pos.dao.DatabaseAccessCode;
import com.my_shop.pos.dto.UserDto;
import com.my_shop.pos.util.PasswordManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class LoginFormController {
    public AnchorPane context;
    public TextField txtUsername;
    public PasswordField txtPassword;
    public Button btnSignIn;

    public void signInBtnOnAction(ActionEvent actionEvent) {
        try {
            UserDto userDto = new DatabaseAccessCode().findUser(txtUsername.getText());
           if (userDto!=null) {
                if (PasswordManager.decryptPassword(txtPassword.getText(),userDto.getPassword())){
                    setUi("DashboardForm");
                }else {
                    new Alert(Alert.AlertType.WARNING, "Check Your Password and try Again");

                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again... User Email Not Found");

            }
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void dontHaveAnAccountBtnOnAction(ActionEvent actionEvent) throws IOException {
        setUi("RegisterForm");
    }

    private void setUi(String url) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/" + url + ".fxml")))
        );
        stage.centerOnScreen();
    }
}

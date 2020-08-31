package lk.ijse.dep.pos.controller;

import com.jfoenix.controls.JFXButton;
import lk.ijse.dep.pos.dbConnection.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginController {
    public TextField txt_username;
    public PasswordField txt_password;
    public Button btn_login;
    public AnchorPane root;
    public JFXButton btn_close;

    public void btn_login_OnAction(ActionEvent actionEvent) {
        login();
    }

    public void txt_password_OnAction(ActionEvent actionEvent) {
        login();
    }

    private void login(){
        if (txt_username.getText().trim().isEmpty()) {
            txt_username.selectAll();
            txt_username.requestFocus();
            return;
        }
        if (txt_password.getText().trim().isEmpty()) {
            txt_password.selectAll();
            txt_password.requestFocus();
            return;
        }
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM user WHERE user_name=? AND password=?");
            preparedStatement.setObject(1,txt_username.getText());
            preparedStatement.setObject(2,txt_password.getText());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()){
                txt_username.setStyle("-fx-border-color: red;-fx-text-fill: red");
                txt_password.setStyle("-fx-border-color: red;-fx-text-fill: red");
            }else{
                resultSet.beforeFirst();
                while(resultSet.next()) {
                    try {
                        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/pos/view/main.fxml"));
                        Scene mainScene = new Scene(root);
                        Stage stage = (Stage) (this.root.getScene().getWindow());
                        stage.setScene(mainScene);
                        stage.centerOnScreen();
                        stage.setResizable(false);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btn_close_OnAction(ActionEvent actionEvent) {
        System.exit(0);
    }
}

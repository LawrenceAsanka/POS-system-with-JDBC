package lk.ijse.dep.pos;

import lk.ijse.dep.pos.dbConnection.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
        try {
            DBConnection.getInstance().getConnection().close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root= FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/pos/view/login.fxml"));
            Scene mainScene = new Scene(root);
            primaryStage.setScene(mainScene);
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.time.Year;
import java.util.*;

public class Controller {
    @FXML
    TextField userNameField, passwordField;

    Stage mainStage;


    public void start(Stage mainStage) {
        this.mainStage = mainStage;
    }
    public void loginButton(ActionEvent e) throws IOException {
        String user = userNameField.getText();
        String password = passwordField.getText();
        if (user.equals("") || user == null)  {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Must input a username or password");
            alert.show();
            return;
        }
        if (password.equals("") || password == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Must input a username or password");
            alert.show();
            return;
        }
        if(user.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")){
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("AdminView.fxml"));

                AnchorPane root = (AnchorPane) loader.load();


                AdminController listController = loader.getController();
                listController.start(mainStage);

                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.setResizable(false);
                mainStage.show();


            } catch (IOException m) {
                m.printStackTrace();
            }
        }
    }
}
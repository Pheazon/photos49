package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


import java.io.*;
import java.lang.reflect.Array;
import java.time.Year;
import java.util.*;

public class AdminController {
    @FXML
    TextField userNameField;

    @FXML
    Label adminUserLabel;

    @FXML
    ListView<String> listView;
    Alert addAlert = new Alert(AlertType.NONE);
    Alert deleteAlert = new Alert(AlertType.NONE);

    ArrayList<User> arraylist;

    Stage mainStage;

    private ObservableList<String> obsList;

    public void start(Stage mainStage, String userNameField, ArrayList<User> arraylist) {
        this.mainStage = mainStage;
        this.adminUserLabel = adminUserLabel;
        this.arraylist = arraylist;

        adminUserLabel.setText(userNameField);


        obsList = FXCollections.observableArrayList();
        this.arraylist = arraylist;

        for(int i = 0; i < arraylist.size(); i++){
            obsList.add(arraylist.get(i).getUsername());

        }
        listView.setItems(obsList);
        listView.getSelectionModel().select(0);

        listView
                .getSelectionModel()
                .selectedItemProperty();

    }

    public void logoutButton(ActionEvent e) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Logout");
        alert.setHeaderText("Are you sure you want to logout?");
        alert.setContentText("You will be logged out");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            System.out.println("logout");
            //Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Login.fxml"));

            AnchorPane root = (AnchorPane) loader.load();

            Controller listController = loader.getController();
            listController.start(mainStage,arraylist,obsList);

            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.setResizable(false);
            mainStage.show();
        }
    }
    public void createUserButton(ActionEvent e) throws IOException {
        userNameField.setText((userNameField.getText()).trim());
        int index = listView.getItems().size();
        if (userNameField.getText().isEmpty()) {
            addAlert.setAlertType(AlertType.ERROR);
            addAlert.setContentText("Username cannot be empty");
            addAlert.show();
            return;
        }
        else if(userNameField.getText().equalsIgnoreCase("admin")){
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("A user cannot be named admin");
            alert.show();
        }
        else {
            obsList.add(userNameField.getText());
            arraylist.add(new User(userNameField.getText()));
            listView.setItems(obsList);
            userNameField.clear();
            System.out.println(index);
        }
    }

    public void deleteUserButton (ActionEvent e) throws IOException {
        int index = listView.getSelectionModel().getSelectedIndex();
        if(obsList.isEmpty()) {
            return;
        }
        else {

            if (arraylist.size() != 0) {
                obsList.remove(index);
                arraylist.remove(index);
            }
        }
    }
}
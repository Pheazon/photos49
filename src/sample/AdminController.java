/**
 * @author Haseeb Balal
 * @author Muffalal Hussain
 */
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

/**
 * this is the admin controller class, where an admin can manage users
 * an admin can see all user, create new users and delete users
 */
public class AdminController {
    @FXML
    TextField userNameField;

    @FXML
    Label adminUserLabel;

    @FXML
    ListView<String> listView;
    Alert addAlert = new Alert(AlertType.NONE);
    Alert deleteAlert = new Alert(AlertType.NONE);



    Stage mainStage;

    private ObservableList<String> obsList;

    public static ArrayList<User> arraylist;
    public static ArrayList<User> getUsers(){
        return arraylist;
    }

    /**
     *
     * @param mainStage
     * @param userNameField
     * @param arraylist
     */
    public void start(Stage mainStage, String userNameField, ArrayList<User> arraylist) {
        this.mainStage = mainStage;
        this.adminUserLabel = adminUserLabel;
        this.arraylist = arraylist;
        adminUserLabel.setText(userNameField);
        obsList = FXCollections.observableArrayList();

        for(int i = 0; i < arraylist.size(); i++){
            obsList.add(arraylist.get(i).getUsername());

        }
        listView.setItems(obsList);
        listView.getSelectionModel().select(0);

        listView
                .getSelectionModel()
                .selectedItemProperty();

    }

    /**
     * this logout button method allows the admin to logout gracefully
     * @param e
     * @throws IOException
     */
    public void logoutButton(ActionEvent e) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Logout");
        alert.setHeaderText("Are you sure you want to logout?");
        alert.setContentText("You will be logged out");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
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

    /**
     * the create user button allows the admin to create a new user
     * @param e
     * @throws IOException
     */
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
        else{
            boolean addUserName = true;
            for(int i = 0; i< arraylist.size(); i++) {
                if(arraylist.get(i).getUsername().equalsIgnoreCase(userNameField.getText()))
                {
                    addUserName = false;
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText("Error");
                    alert.setContentText("Already exist");
                    alert.showAndWait();
                    break;
                }
            }
            if(addUserName)
            {
                obsList.add(userNameField.getText());
                arraylist.add(new User(userNameField.getText()));
                listView.setItems(obsList);
                userNameField.clear();
            }
        }
    }

    /**
     * the delete user button allows the admin to delete a user
     * @param e
     * @throws IOException
     */
    public void deleteUserButton (ActionEvent e) throws IOException {
        int index = listView.getSelectionModel().getSelectedIndex();

        if(obsList.isEmpty()) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("nothing to delete");
            alert.show();
        }
        else {
            if (arraylist.size() != 0) {
                obsList.remove(index);
                arraylist.remove(index);
            }
        }
    }
}
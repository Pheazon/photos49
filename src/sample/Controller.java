package sample;

import javafx.beans.Observable;
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
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.time.Year;
import java.util.*;

public class Controller {
    @FXML
    TextField userNameField;
    ArrayList<User> arraylist;
    ObservableList<String> obsList;

    Stage mainStage;


    public void start(Stage mainStage,  ArrayList<User> arrayList,ObservableList<String>obsList)
    {
        this.mainStage = mainStage;
        this.arraylist = arrayList;
        this.obsList = obsList;

    }
    public void loginButton(ActionEvent e) throws IOException
    {
        String user = userNameField.getText();
        if ((user.equals("") || user == null))
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Must input a username");
            alert.show();
            return;
        }
        else if(user.equalsIgnoreCase("admin"))
        {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("AdminView.fxml"));

                AnchorPane root = (AnchorPane) loader.load();


                AdminController listController = loader.getController();
                listController.start(mainStage, userNameField.getText(), arraylist);

                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.setResizable(false);
                mainStage.show();


            } catch (IOException m) {
                m.printStackTrace();
            }
        }
        else
        {
            for(int i=0;i<arraylist.size();i++)
                if(arraylist.get(i).getUsername().equalsIgnoreCase(user)) {

                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("UserView.fxml"));

                        AnchorPane root = (AnchorPane) loader.load();


                        UserViewController listController = loader.getController();
                        listController.start(mainStage,obsList,arraylist, i);

                        Scene scene = new Scene(root);
                        mainStage.setScene(scene);
                        mainStage.setResizable(false);
                        mainStage.show();

                    } catch (IOException m) {
                        m.printStackTrace();
                    }
                    return;
                }
        }
    }
    public void createUserButton(ActionEvent e) throws IOException {
        //ArrayList<String> list = new ArrayList<String>();
        String user = userNameField.getText();
        if ((user.equals("") || user == null)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Must input a username");
            alert.show();
            return;
        }
        else{
            arraylist.add(new User(user));
            userNameField.clear();
        }
    }
}
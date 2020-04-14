/**
 * @author Haseeb Balal
 * @author Muffalal Hussain
 */
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

/**
 * This is the class for the login controller, this is the first screen the user will see
 * and can either login as admin or create an account and login as a user
 */
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
        mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                        @Override
                                        public void handle(WindowEvent windowEvent) {
                                            try {
                                                for(int i=0;i<arraylist.size();i++)
                                                {
                                                }
                                                Main.writeApp(arraylist);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
        );

    }

    /**
     * This method is for the login button, a user can either login as an admin or regular user
     * @param e
     * @throws IOException
     */
    public void loginButton(ActionEvent e) throws IOException
    {
        String user = userNameField.getText();
        if (( user == null ||  user.equals("")))
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

    /**
     * This is the create user button, a user can enter a username allowing them to create their username to login with
     * @param e
     * @throws IOException
     */
    public void createUserButton(ActionEvent e) throws IOException {
        //ArrayList<String> list = new ArrayList<String>();
        String user = userNameField.getText();
        if (( user == null) || user.equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Must input a username");
            alert.show();
            return;
        }
        else if(user.equalsIgnoreCase("admin")){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Admin name is reserved");
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
                arraylist.add(new User(userNameField.getText()));
                userNameField.clear();
            }
        }
    }
}
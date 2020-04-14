package sample;


import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.AdminController;
import sample.User;

import java.io.*;
import java.util.ArrayList;

public class Main extends Application implements Serializable {

    public static final String storeDir = "dat";
    public static final String storeFile = "data.dat";
    public static ArrayList<User> userData;
    ObservableList<String>obslist;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        userData = new ArrayList<>();
        ArrayList<User>users = readApp();
        obslist = FXCollections.observableArrayList();
        if(users.size() ==0)
        {
            users.add(new User("stock"));
            obslist.add("stock");
        }
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                           @Override
                                           public void handle(WindowEvent windowEvent) {
                                               try {
                                                   for(int i=0;i<users.size();i++)
                                                   {
                                                   }
                                                   writeApp(users);
                                               } catch (IOException e) {
                                                   e.printStackTrace();
                                               }
                                           }
                                       }
        );

        loader.setLocation(getClass().getResource("Login.fxml"));
        AnchorPane root = (AnchorPane) loader.<AnchorPane>load();
        Controller listController = loader.getController();
        listController.start(primaryStage, users, obslist);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Main photoAlbum = new Main();


        launch(args);
        photoAlbum.userData = AdminController.getUsers();


    }


    public static ArrayList<User> readApp()
            throws IOException, ClassNotFoundException {
        try {
            ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(storeDir + File.separator + storeFile));
            userData = (ArrayList<User>) ois.readObject();
            ois.close();
        }
        catch(IOException ioe){
            return userData;
        }catch(ClassNotFoundException c) {
            return userData;
        }
        return userData;
    }
    public static void writeApp(ArrayList<User> gapp)
            throws IOException {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(storeDir + File.separator + storeFile));
            oos.writeObject(gapp);
            oos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}

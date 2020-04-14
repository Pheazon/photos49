/**
 * @author Haseeb Balal
 * @author Muffalal Hussain
 */
package sample;



import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.util.ArrayList;

public class Photos extends Application implements Serializable {

    /**
     * Creates a folder
     */
    public static final String storeDir = "dat";

    /**
     * Creates the file in the folder
     */
    public static final String storeFile = "data.dat";

    /**
     *  Array list of users from using arraylist class of User
     */
    public static ArrayList<User> userData;

    /**
     * arraylist of users
     */
    ObservableList<String>obslist;

    /**Creates a user name called stock when no user is present and is called by the main class
     * @param primaryStage the scene which it goes to
     * @throws Exception
     */
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
        LoginController listLoginController = loader.getController();
        listLoginController.start(primaryStage, users, obslist);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Photos photoAlbum = new Photos();


        launch(args);
        photoAlbum.userData = AdminController.getUsers();


    }


    /**
     * Reads for serialization
     * @return The user data
     * @throws IOException
     * @throws ClassNotFoundException
     */
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

    /**
     * Writes for serialization
     * @param user User arraylist
     * @throws IOException
     */
    public static void writeApp(ArrayList<User> user)
            throws IOException {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(storeDir + File.separator + storeFile));
            oos.writeObject(user);
            oos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}

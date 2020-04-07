package sample;


import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    ObservableList<String>obslist;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        ArrayList<User>users = new ArrayList<>();
        obslist = FXCollections.observableArrayList();
        users.add(new User("stock"));
        obslist.add("stock");

        loader.setLocation(getClass().getResource("Login.fxml"));
        AnchorPane root = (AnchorPane) loader.<AnchorPane>load();
        Controller listController = loader.getController();
        listController.start(primaryStage,users,obslist);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

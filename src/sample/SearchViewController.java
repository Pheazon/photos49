package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class SearchViewController {
    ArrayList<User> Users;
    int userIndex;
    int albumIndex;

    Stage mainStage;

    public void start(Stage mainStage,ArrayList<User>users,int userIndex,int albumIndex)
    {
        this.mainStage = mainStage;
        this.userIndex = userIndex;
        this.Users = users;
        this.albumIndex = albumIndex;
    }

    public void Back() throws IOException {
        ObservableList<String> obsList = FXCollections.observableArrayList();
        for (int i = 0; i < Users.size(); i++)
            obsList.add(Users.get(i).getUsername());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("UserView.fxml"));

        AnchorPane root = (AnchorPane) loader.load();

        UserViewController listController = loader.getController();
        listController.start(mainStage, obsList, Users, userIndex);

        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();
    }
}

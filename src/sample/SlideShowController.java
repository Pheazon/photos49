package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class SlideShowController
{
    @FXML
    Label Username;
    @FXML
    ImageView Image;

    Stage mainStage;

    ArrayList<User>Users;
    int userIndex;
    int albumIndex;
    int Index;
    public void start(Stage mainStage, ArrayList<User> users, int userIndex, int albumIndex) throws Exception {
        this.albumIndex = albumIndex;
        this.userIndex = userIndex;
        this.Users = users;
        Username.setText(users.get(userIndex).getAlbums().get(albumIndex).getName());
        this.mainStage = mainStage;
        Index = 0;
        ImageUp(Index);
    }

    public void ImageUp(int Index) throws Exception
    {
        Image image = new Image(new FileInputStream(Users.get(userIndex).getAlbums().get(albumIndex).getPictures().get(Index).getImages()));
        Image.setImage(image);
    }
    public void nextPicture(ActionEvent event) throws Exception
    {
        if(Index + 1 == Users.get(userIndex).getAlbums().get(albumIndex).getPictures().size())
            Index = 0;
        else
            Index++;
        ImageUp(Index);
    }
    public void previousPicture(ActionEvent event) throws Exception {
        if(Index - 1 == -1)
            Index =  Users.get(userIndex).getAlbums().get(albumIndex).getPictures().size()-1;
        else
            Index--;
       ImageUp(Index);
    }

    public void Back() throws IOException
    {
        ObservableList<String> obsList = FXCollections.observableArrayList();
        for(int i=0;i<Users.size();i++)
            obsList.add(Users.get(i).getUsername());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AlbumView.fxml"));

        AnchorPane root = (AnchorPane) loader.load();

        AlbumViewController listController = loader.getController();
        listController.start(mainStage,Users,userIndex,albumIndex);

        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();
    }
    public void logoutButton(ActionEvent e) throws IOException {
        ObservableList<String> obsList = FXCollections.observableArrayList();
        for(int i=0;i<Users.size();i++)
            obsList.add(Users.get(i).getUsername());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
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
            listController.start(mainStage,Users,obsList);

            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.setResizable(false);
            mainStage.show();

        }
    }
}

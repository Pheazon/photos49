package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class SearchViewController
{
    @FXML
    DatePicker StartDate;
    @FXML
    DatePicker EndDate;
    @FXML
    TextField AlbumName;
    @FXML
    TableView<TableRow> table;

    ArrayList<User> Users;
    int userIndex;
    int albumIndex;
    ObservableList<String> obsList;
    ObservableList<TableRow> photos;

    Stage mainStage;

    public void start(Stage mainStage,ArrayList<User>users,int userIndex)
    {
        this.mainStage = mainStage;
        this.userIndex = userIndex;
        this.Users = users;

        obsList = FXCollections.observableArrayList();
        photos = FXCollections.observableArrayList();

        TableColumn<TableRow, ImageView> image = new TableColumn<>("image");
        image.setMaxWidth(400);
        image.setCellValueFactory(new PropertyValueFactory<>("image"));

        TableColumn<TableRow, String> name = new TableColumn<>("caption");
        name.setMaxWidth(200);
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<TableRow, String> pathName = new TableColumn<>("pathName");
        pathName.setMaxWidth(200);
        pathName.setCellValueFactory(new PropertyValueFactory<>("pathName"));

        TableColumn<TableRow, String> date = new TableColumn<>("date");
        date.setMaxWidth(200);
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        table.getColumns().addAll(image,name,pathName,date);
    }

    public void Search() throws IOException
    {
        if(StartDate.getValue() == null || EndDate.getValue() == null)
        {
            return;
        }
        if(StartDate.getValue().compareTo(EndDate.getValue()) >0)
        {
            return;
        }
        TableDisplay(StartDate.getValue(),EndDate.getValue());
    }

    public void TableDisplay(LocalDate startDate,LocalDate endDate) throws FileNotFoundException {
        photos = FXCollections.observableArrayList();
        for(int i = 0; i <Users.get(userIndex).getAlbums().size();i++)
        {
            for(int j = 0; j<Users.get(userIndex).getAlbums().get(i).getPictures().size();j++)
            {
                if(startDate.compareTo(Users.get(userIndex).getAlbums().get(i).getPictures().get(j).getDate()) <= 0 && endDate.compareTo(Users.get(userIndex).getAlbums().get(i).getPictures().get(j).getDate()) >= 0)
                {
                    ImageView imageView = new ImageView(new Image(new FileInputStream(Users.get(userIndex).getAlbums().get(i).getPictures().get(j).getImages())));
                    imageView.setFitWidth(200);
                    imageView.setFitHeight(200);
                    photos.add(new TableRow(imageView,Users.get(userIndex).getAlbums().get(i).getPictures().get(j).getCaption(),Users.get(userIndex).getAlbums().get(i).getPictures().get(j).getImages(),Users.get(userIndex).getAlbums().get(i).getPictures().get(j).getDate()));

                }

            }
        }
        table.setItems(photos);
    }

    public void CreateAlbum()
    {
        if(AlbumName.getText().isEmpty())
        {
            return;
        }
        if(photos.size() == 0)
        {
            return;
        }
        Users.get(userIndex).addAlbum(AlbumName.getText());
        for(int i=0;i<photos.size();i++)
            Users.get(userIndex).getAlbums().get(Users.get(userIndex).getAlbums().size()-1).addPicture(photos.get(i).getPathName(),photos.get(i).getName(),photos.get(i).getDate());
       AlbumName.clear();
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

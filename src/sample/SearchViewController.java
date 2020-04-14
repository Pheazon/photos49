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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    TextField albumName;
    @FXML
    TableView<TableRow> table;
    @FXML
    TextField firstTagValue;
    @FXML
    TextField secondTagValue;
    @FXML
     ChoiceBox<String> choice;

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

        choice.getItems().add("Or");
        choice.getItems().add("And");

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

    public void SearchByTag() throws IOException
    {
        if(firstTagValue.getText().isEmpty() || secondTagValue.getText().isEmpty() || choice.getValue().isEmpty())
        {
            return;
        }
        TableDisplay(firstTagValue.getText(),secondTagValue.getText());
    }
    public void TableDisplay(String tagOne, String tagTwo) throws  IOException
    {
        photos = FXCollections.observableArrayList();
        if(choice.getValue().equals("And"))
        {
            for(int i = 0; i <Users.get(userIndex).getAlbums().size();i++)
            {
                for(int j = 0; j<Users.get(userIndex).getAlbums().get(i).getPictures().size();j++)
                {
                    for(int k = 0 ; k<Users.get(userIndex).getAlbums().get(i).getPictures().get(j).tagSize();k++) {
                        if (Users.get(userIndex).getAlbums().get(i).getPictures().get(j).getTagValue(k).equals(tagOne) && Users.get(userIndex).getAlbums().get(i).getPictures().get(j).getTagValue(k).equals(tagTwo)) {

                            ImageView imageView = new ImageView(new Image(new FileInputStream(Users.get(userIndex).getAlbums().get(i).getPictures().get(j).getImages())));
                            imageView.setFitWidth(200);
                            imageView.setFitHeight(200);
                            photos.add(new TableRow(imageView,
                                    Users.get(userIndex).getAlbums().get(i).getPictures().get(j).getCaption(),
                                    Users.get(userIndex).getAlbums().get(i).getPictures().get(j).getImages(),
                                    Users.get(userIndex).getAlbums().get(i).getPictures().get(j).getDate(),
                                    Users.get(userIndex).getAlbums().get(i).getPictures().get(j).getTagNameArray(),
                                    Users.get(userIndex).getAlbums().get(i).getPictures().get(j).getTagValueArray()));
                        }
                    }
                    }
                }
            }
        else if(choice.getValue().equals("Or"))
        {
            for(int i = 0; i <Users.get(userIndex).getAlbums().size();i++)
            {
                for(int j = 0; j<Users.get(userIndex).getAlbums().get(i).getPictures().size();j++)
                {
                    System.out.println(Users.get(userIndex).getAlbums().get(i).getPictures().get(j).tagSize());
                    for(int k = 0; k<Users.get(userIndex).getAlbums().get(i).getPictures().get(j).tagSize();k++) {
                        if (Users.get(userIndex).getAlbums().get(i).getPictures().get(j).getTagValue(k).equals(tagOne) || Users.get(userIndex).getAlbums().get(i).getPictures().get(j).getTagValue(k).equals(tagTwo)) {
                            System.out.println(Users.get(userIndex).getAlbums().get(i).getPictures().get(j).getTagValue(k));
                            ImageView imageView = new ImageView(new Image(new FileInputStream(Users.get(userIndex).getAlbums().get(i).getPictures().get(j).getImages())));
                            imageView.setFitWidth(200);
                            imageView.setFitHeight(200);
                            photos.add(new TableRow(imageView,
                                    Users.get(userIndex).getAlbums().get(i).getPictures().get(j).getCaption(),
                                    Users.get(userIndex).getAlbums().get(i).getPictures().get(j).getImages(),
                                    Users.get(userIndex).getAlbums().get(i).getPictures().get(j).getDate(),
                                    Users.get(userIndex).getAlbums().get(i).getPictures().get(j).getTagNameArray(),
                                    Users.get(userIndex).getAlbums().get(i).getPictures().get(j).getTagValueArray()));
                        }
                    }
                }
            }
        }
        table.setItems(photos);
    }
    public void TableDisplay(LocalDate startDate,LocalDate endDate) throws FileNotFoundException {
        photos = FXCollections.observableArrayList();
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(23,59);
        for(int i = 0; i <Users.get(userIndex).getAlbums().size();i++)
        {
            for(int j = 0; j<Users.get(userIndex).getAlbums().get(i).getPictures().size();j++)
            {
                if(start.compareTo(Users.get(userIndex).getAlbums().get(i).getPictures().get(j).getDate()) <= 0 && end.compareTo(Users.get(userIndex).getAlbums().get(i).getPictures().get(j).getDate()) >= 0)
                {
                    ImageView imageView = new ImageView(new Image(new FileInputStream(Users.get(userIndex).getAlbums().get(i).getPictures().get(j).getImages())));
                    imageView.setFitWidth(200);
                    imageView.setFitHeight(200);
                    photos.add(new TableRow(imageView,Users.get(userIndex).getAlbums().get(i).getPictures().get(j).getCaption(),
                            Users.get(userIndex).getAlbums().get(i).getPictures().get(j).getImages(),
                            Users.get(userIndex).getAlbums().get(i).getPictures().get(j).getDate(),
                            Users.get(userIndex).getAlbums().get(i).getPictures().get(j).getTagNameArray(),
                            Users.get(userIndex).getAlbums().get(i).getPictures().get(j).getTagValueArray()));

                }

            }
        }
        table.setItems(photos);
    }

    public void CreateAlbum()
    {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        if(AlbumName.getText().isEmpty())
        {
            return;
        }
        if(photos.size() == 0)
        {
            return;
        }
        for(int i = 0; i < Users.get(userIndex).getAlbums().size();i++) {

            if(Users.get(userIndex).getAlbums().get(i).getName().equals(AlbumName.getText()))
            {
                errorAlert.setContentText("Album Name already created");
                errorAlert.showAndWait();
                return;
            }
        }
        Users.get(userIndex).addAlbum(albumName.getText());
        for(int i=0;i<photos.size();i++)
            Users.get(userIndex).getAlbums().get(Users.get(userIndex).getAlbums().size()-1).addPicture(photos.get(i).getPathName(),photos.get(i).getName(),photos.get(i).getDate());
       AlbumName.clear();
    }
    public void CreateAlbumTag()
    {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        if(albumName.getText().isEmpty())
        {
            return;
        }
        if(photos.size() == 0)
        {
            return;
        }
        for(int i = 0; i < Users.get(userIndex).getAlbums().size();i++) {

            if(Users.get(userIndex).getAlbums().get(i).getName().equals(AlbumName.getText()))
            {
                errorAlert.setContentText("Album Name already created");
                errorAlert.showAndWait();
                return;
            }
        }
        Users.get(userIndex).addAlbum(albumName.getText());
        for(int i=0;i<photos.size();i++) {
            Users.get(userIndex).getAlbums().get(Users.get(userIndex).getAlbums().size() - 1).addPicture(photos.get(i).getPathName(), photos.get(i).getName(), photos.get(i).getDate());
             Users.get(userIndex).getAlbums().get(Users.get(userIndex).getAlbums().size() - 1)
                    .getPicture(Users.get(userIndex).getAlbums().get(Users.get(userIndex)
                            .getAlbums().size() - 1).getPictures().size() - 1).setTags(photos.get(i).getTagNames(), photos.get(i).getTagValues());

        }
        albumName.clear();
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

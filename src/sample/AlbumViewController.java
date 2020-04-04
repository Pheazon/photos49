package sample;

import java.io.FileInputStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class AlbumViewController
{
    @FXML
    TableView<TableRow> table;
    @FXML
    TextField picURL;
    @FXML
    TextField Date;
    @FXML
    TextField caption;
    @FXML
     Label name;
    @FXML
     ChoiceBox<Album>choices;

    ObservableList<String> obsList;
    ObservableList<TableRow> photos;

    ArrayList<User>Users;
    int userIndex;
    int albumIndex;

    Stage mainStage;
    public void start(Stage mainStage,ArrayList<User>users,int userIndex,int albumIndex) throws FileNotFoundException
    {
        this.Users = users;
        name.setText(users.get(userIndex).getAlbums().get(albumIndex).getName());
        this.mainStage = mainStage;

        ImageView imageView = new ImageView(new Image(new FileInputStream("D:/Project2/photo1.jpg")) );
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        ImageView imageView1 = new ImageView(new Image(new FileInputStream("D:/Project2/photo2.jpg")) );
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        obsList = FXCollections.observableArrayList();
        photos = FXCollections.observableArrayList();


        TableColumn<TableRow, ImageView> image = new TableColumn<>("image");
        image.setMaxWidth(200);
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

        TableRow item_1 = new TableRow(imageView,"s","aa","d");
        TableRow item_2 = new TableRow(imageView1,"s","aa","d");

         //photos.add(item_1);
         //photos.add(item_2);

        //table.setItems(photos);
        table.getColumns().addAll(image,name,pathName,date);


    }

    public void addPicture() throws FileNotFoundException
    {

        String url = picURL.getText();
        String cap = caption.getText();
        String date = Date.getText();
        ImageView imageView = new ImageView(new Image(new FileInputStream(url)));
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        TableRow item_1 = new TableRow(imageView,cap,url,date);
        Users.get(userIndex).albums.get(albumIndex).addPicture(url,cap,date);
        photos.add(item_1);
        table.setItems(photos);
        picURL.clear();
        caption.clear();
        Date.clear();
        table.getSelectionModel().select(0);
    }

    public void deletePicture()
    {
        int index = table.getSelectionModel().getSelectedIndex();
        if(photos.isEmpty())
        {
            return;
        }
        else {
            if (photos.size() != 0)
            {
                Users.get(userIndex).getAlbums().get(albumIndex).deletePicture(index);
                photos.remove(index);
            }
        }
    }

    public void editPicture() throws FileNotFoundException
    {
        int index = table.getSelectionModel().getSelectedIndex();

        String cap = photos.get(index).getName();

        if(!cap.equals(caption.getText()))
        {
            System.out.println(caption.getText());
            System.out.println("cap is" + cap);
            cap = caption.getText();
        }

        if(photos.isEmpty())
        {
            return;
        }
        else
        {
            if(photos.size() != 0)
            {
                Users.get(userIndex).getAlbums().get(albumIndex).getPictures().get(index).setCaption(cap);
                photos = FXCollections.observableArrayList();
                for(int i=0;i<Users.get(userIndex).getAlbums().get(albumIndex).getPictures().size();i++) {
                    ImageView image = new ImageView(new Image(new FileInputStream(Users.get(userIndex).getAlbums().get(albumIndex).getPictures().get(i).getImages())));
                    image.setFitWidth(100);
                    image.setFitHeight(100);
                    photos.add(new TableRow(image,
                            Users.get(userIndex).getAlbums().get(albumIndex).getPictures().get(i).getCaption(),
                            Users.get(userIndex).getAlbums().get(albumIndex).getPictures().get(i).getImages(),
                            Users.get(userIndex).getAlbums().get(albumIndex).getPictures().get(i).getDate())
                    );
                }
            table.setItems(photos);
            }

        }
        caption.clear();
    }
    public void Copy()
    {

    }

    public void logoutButton(ActionEvent e) throws IOException {
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
            listController.start(mainStage);

            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.setResizable(false);
            mainStage.show();

        }
    }
}

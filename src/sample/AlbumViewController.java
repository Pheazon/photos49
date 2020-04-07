package sample;

import java.io.FileInputStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.time.LocalDate;
public class AlbumViewController
{
    @FXML
    TableView<TableRow> table;
    @FXML
    TextField picURL;
    @FXML
    TextField caption;
    @FXML
     Label Username;
    @FXML
     ChoiceBox <String> CopyPictures;
    @FXML
    ChoiceBox <String> CopyAlbums;

    @FXML
    ChoiceBox <String> MovePictures;
    @FXML
    ChoiceBox <String> MoveAlbums;



    ObservableList<String> obsList;
    ObservableList<TableRow> photos;
    ObservableList<String> albumNames;
    ObservableList<String> pictureList;
    ArrayList<User>Users;
    int userIndex;
    int albumIndex;

    Stage mainStage;


    public void start(Stage mainStage,ArrayList<User>users,int userIndex,int albumIndex) throws FileNotFoundException
    {
        this.albumIndex = albumIndex;
        this.userIndex = userIndex;
        this.Users = users;
        Username.setText(users.get(userIndex).getAlbums().get(albumIndex).getName());
        this.mainStage = mainStage;

        ImageView imageView = new ImageView(new Image(new FileInputStream("D:/Project2/photo1.jpg")) );
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        ImageView imageView1 = new ImageView(new Image(new FileInputStream("D:/Project2/photo2.jpg")) );
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

       //DateLabel = new LocalDate();

        obsList = FXCollections.observableArrayList();
        photos = FXCollections.observableArrayList();
        albumNames = FXCollections.observableArrayList();
        pictureList = FXCollections.observableArrayList();

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

        //TableRow item_1 = new TableRow(imageView,"s","aa","d");
      //  TableRow item_2 = new TableRow(imageView1,"s","aa","d");
        table.getColumns().addAll(image,name,pathName,date);
        for(int i = 0;i<users.get(userIndex).getAlbums().get(albumIndex).getPictures().size();i++)
        {
            Images temp = users.get(userIndex).getAlbums().get(albumIndex).getPictures().get(i);
            ImageView tempImageView = new ImageView(new Image(new FileInputStream(temp.getImages())));
            tempImageView.setFitWidth(100);
            tempImageView.setFitHeight(100);
            TableRow item = new TableRow(tempImageView,temp.getCaption(),temp.getImages(),temp.getDate());
            photos.add(item);
        }

        for(int i = 0; i < users.get(userIndex).getAlbums().size(); i++)
        {
            if (users.get(userIndex).getAlbums().size() == 0) {
                break;
            }
            else if(!users.get(userIndex).getAlbums().get(i).getName().equals(Username.getText()))
            {
                albumNames.add(users.get(userIndex).getAlbums().get(i).getName());
            }
        }
            CopyAlbums.getItems().addAll(albumNames);
            MoveAlbums.getItems().addAll(albumNames);

        for(int i = 0; i < users.get(userIndex).getAlbums().get(albumIndex).getPictures().size(); i++)
        {
            pictureList.add(users.get(userIndex).getAlbums().get(albumIndex).getPictures().get(i).getImages());
        }
        CopyPictures.getItems().addAll(pictureList);
        MovePictures.getItems().addAll(pictureList);
         //photos.add(item_1);
         //photos.add(item_2);

        //table.setItems(photos);
        table.setItems(photos);

    }
    public void Back() throws IOException {
        ObservableList<String> obsList = FXCollections.observableArrayList();
        for(int i=0;i<Users.size();i++)
            obsList.add(Users.get(i).getUsername());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("UserView.fxml"));

        AnchorPane root = (AnchorPane) loader.load();

        UserViewController listController = loader.getController();
        listController.start(mainStage,obsList,Users,userIndex);

        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();
    }
    public void addPicture() throws FileNotFoundException
    {

        String url = picURL.getText();
        String cap = caption.getText();
        ImageView imageView = new ImageView(new Image(new FileInputStream(url)));
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        TableRow item_1 = new TableRow(imageView,cap,url,java.time.LocalDate.now());
        Users.get(userIndex).getAlbums().get(albumIndex).addPicture(url,cap,java.time.LocalDate.now());
        pictureList.add(url);
        CopyPictures.getItems().add(url);
        MovePictures.getItems().add(url);
        photos.add(item_1);
        table.setItems(photos);
        picURL.clear();
        caption.clear();
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
                pictureList.remove(index);
                CopyPictures.setItems(pictureList);
                MovePictures.setItems(pictureList);
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
        int tempAlbumIndex = 0;
        int tempPicIndex = 0;
        if(CopyPictures.getValue() == null || CopyAlbums.getValue() == null)
            return;
        String albumSelected = CopyAlbums.getValue();
        String pictureSelected = CopyPictures.getValue();
        for(int i = 0 ; i<Users.get(userIndex).getAlbums().size(); i++)
        {
            if(Users.get(userIndex).getAlbums().get(i).getName().equals(albumSelected))
            {
                tempAlbumIndex = i;
                break;
            }
        }

        for(int i = 0 ; i<Users.get(userIndex).getAlbums().get(albumIndex).getPictures().size(); i++)
        {
            if(!Users.get(userIndex).getAlbums().get(albumIndex).getPictures().get(i).getImages().equals(pictureSelected))
            {
                tempPicIndex = i;
                break;
            }
        }
        String caption = Users.get(userIndex).getAlbums().get(albumIndex).getPictures().get(tempPicIndex).getCaption();
        LocalDate date = Users.get(userIndex).getAlbums().get(albumIndex).getPictures().get(tempPicIndex).getDate();
        Users.get(userIndex).getAlbums().get(tempAlbumIndex).addPicture(pictureSelected,caption,date);

    }

    public void Move()
    {
        int tempAlbumIndex = 0;
        int tempPicIndex = 0;
        if(MovePictures.getValue() == null || MoveAlbums.getValue() == null)
            return;
        String albumSelected = MoveAlbums.getValue();
        String pictureSelected = MovePictures.getValue();
        for(int i = 0 ; i<Users.get(userIndex).getAlbums().size(); i++)
        {
            if(Users.get(userIndex).getAlbums().get(i).getName().equals(albumSelected))
            {
                tempAlbumIndex = i;
                break;
            }
        }

        for(int i = 0 ; i<Users.get(userIndex).getAlbums().get(albumIndex).getPictures().size(); i++)
        {
            if(!Users.get(userIndex).getAlbums().get(albumIndex).getPictures().get(i).getImages().equals(pictureSelected))
            {
                tempPicIndex = i;
                break;
            }
        }
        String caption = Users.get(userIndex).getAlbums().get(albumIndex).getPictures().get(tempPicIndex).getCaption();
        LocalDate date = Users.get(userIndex).getAlbums().get(albumIndex).getPictures().get(tempPicIndex).getDate();
        Users.get(userIndex).getAlbums().get(tempAlbumIndex).addPicture(pictureSelected,caption,date);
        Users.get(userIndex).getAlbums().get(albumIndex).deletePicture(tempPicIndex);
        photos.remove(tempPicIndex);
        pictureList.remove(tempPicIndex);
        CopyPictures.setItems(pictureList);
        MovePictures.setItems(pictureList);
    }


    public void toSlideShow(ActionEvent e ) throws Exception
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SlideShow.fxml"));

        AnchorPane root = (AnchorPane) loader.load();

        SlideShowController listController = loader.getController();
        listController.start(mainStage,Users,userIndex,albumIndex);

        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();
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
            listController.start(mainStage,Users,obsList);

            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.setResizable(false);
            mainStage.show();

        }
    }
}

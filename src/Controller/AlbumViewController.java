/**
 * @author Haseeb Balal
 * @author Muffalal Hussain
 */
package Controller;

import java.io.FileInputStream;

import Model.Images;
import Model.TableRow;
import Model.User;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class AlbumViewController
{
    @FXML
    TableView<Model.TableRow> table;
    @FXML
    TextField picURL;
    @FXML
    TextField caption;
    @FXML
    TextField tagName;
    @FXML
    TextField tagValue;
    @FXML
     Label Username;
    @FXML
     ChoiceBox <String> CopyPictures;
    @FXML
    ChoiceBox <String> CopyAlbums;
    @FXML
    ChoiceBox <String> tagPictures;
    @FXML
    ChoiceBox <String> MovePictures;
    @FXML
    ChoiceBox <String> MoveAlbums;
    @FXML
     ListView tagNameList;
    @FXML
    ListView tagValueList;


    /**
     * List of the users to send everywhere
     */
    ObservableList<String> obsList;
    /**
     * list of the all the pictures into a tablerow format
     */
    ObservableList<Model.TableRow> photos;
    /**
     *list of the albums in the user
     */
    ObservableList<String> albumNames;
    /**
     *List of the pictures
     */
    ObservableList<String> pictureList;
    /**
     * list of the users
     */
    ArrayList<User>Users;
    /**
     *the current user selected
     */
    int userIndex;
    /**
     * the current album selected
     */
    int albumIndex;

    Stage mainStage;

    /**
     *Creates a table which is empty or with what ever picture that is added into it already. Storing data into the
     * array list
     *
     * @param mainStage the stage which is selected
     * @param users The user list
     * @param userIndex the current user index from the users
     * @param albumIndex the current album index
     * @throws FileNotFoundException
     */
    public void start(Stage mainStage,ArrayList<User>users,int userIndex,int albumIndex) throws FileNotFoundException
    {
        this.albumIndex = albumIndex;
        this.userIndex = userIndex;
        this.Users = users;
        Username.setText(users.get(userIndex).getAlbums().get(albumIndex).getName());
        this.mainStage = mainStage;

        obsList = FXCollections.observableArrayList();
        photos = FXCollections.observableArrayList();
        albumNames = FXCollections.observableArrayList();
        pictureList = FXCollections.observableArrayList();

        TableColumn<Model.TableRow, ImageView> image = new TableColumn<>("image");
        image.setMaxWidth(200);
        image.setCellValueFactory(new PropertyValueFactory<>("image"));

        TableColumn<Model.TableRow, String> name = new TableColumn<>("caption");
        name.setMaxWidth(200);
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Model.TableRow, String> pathName = new TableColumn<>("pathName");
        pathName.setMaxWidth(200);
        pathName.setCellValueFactory(new PropertyValueFactory<>("pathName"));

        TableColumn<Model.TableRow, String> date = new TableColumn<>("date");
        date.setMaxWidth(200);
        date.setCellValueFactory(new PropertyValueFactory<>("date"));


        table.getColumns().addAll(image,name,pathName,date);
        for(int i = 0;i<users.get(userIndex).getAlbums().get(albumIndex).getPictures().size();i++)
        {
            Images temp = users.get(userIndex).getAlbums().get(albumIndex).getPictures().get(i);
            ImageView tempImageView = new ImageView(new Image(new FileInputStream(temp.getImages())));
            tempImageView.setFitWidth(100);
            tempImageView.setFitHeight(100);
            Model.TableRow item = new Model.TableRow(tempImageView,temp.getCaption(),temp.getImages(),temp.getDate(),temp.getTagNameArray(), temp.getTagValueArray());
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
        tagPictures.getItems().addAll(pictureList);

        table.setItems(photos);
        if(table.getItems() != null && table.getItems().size() > 0)
            addTag(0);
        table.getSelectionModel().selectedIndexProperty()
                .addListener(e ->
                        {
                            if(table.getItems() != null && table.getItems().size() > 0)
                                addTag(table.getSelectionModel().getSelectedIndex());
                        }
                        );


    }

    /**
     * Goes back to the Userview fxml with the data saved
     * @throws IOException
     */
    public void Back() throws IOException {
        ObservableList<String> obsList = FXCollections.observableArrayList();
        for(int i=0;i<Users.size();i++)
            obsList.add(Users.get(i).getUsername());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/UserView.fxml"));

        AnchorPane root = (AnchorPane) loader.load();

        UserViewController listController = loader.getController();
        listController.start(mainStage,obsList,Users,userIndex);

        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();
    }

    /**
     * This adds a picture into the album and puts it into the table
     * @throws FileNotFoundException
     */
    public void addPicture() throws FileNotFoundException
    {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        if(picURL.getText().isEmpty())
        {
            errorAlert.setContentText("Empty path Name");
            errorAlert.showAndWait();
            return;
        }
        if(caption.getText().isEmpty())
        {
            caption.setText("");
        }
        String url = picURL.getText();
        String cap = caption.getText();
        ImageView imageView = new ImageView(new Image(new FileInputStream(url)));
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        Model.TableRow item_1 = new Model.TableRow(imageView,cap,url,java.time.LocalDateTime.now(), new ArrayList<String>(), new ArrayList<String>());
        Users.get(userIndex).getAlbums().get(albumIndex).addPicture(url,cap,java.time.LocalDateTime.now());
        pictureList.add(url);
        CopyPictures.getItems().add(url);
        MovePictures.getItems().add(url);
        tagPictures.getItems().add(url);
        photos.add(item_1);
        table.setItems(photos);
        picURL.clear();
        caption.clear();
        table.getSelectionModel().select(Users.get(userIndex).getAlbums().get(albumIndex).getPictures().size()-1);
        addTag(Users.get(userIndex).getAlbums().get(albumIndex).getPictures().size()-1);
    }

    /**
     * Deletes a picture which is selected from the table
     */
    public void deletePicture()
    {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        int index = table.getSelectionModel().getSelectedIndex();
        if(photos.isEmpty())
        {
            errorAlert.setContentText("Empty text");
            errorAlert.showAndWait();
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
                tagPictures.setItems(pictureList);
            }
        }
    }

    /**
     * edits the caption of the picture
     * @throws FileNotFoundException
     */
    public void editPicture() throws FileNotFoundException
    {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        int index = table.getSelectionModel().getSelectedIndex();

        String cap = photos.get(index).getName();

        if(cap.equals(caption.getText()))
        {
            errorAlert.setContentText("Same caption already");
            errorAlert.showAndWait();
            return;
        }

        if(photos.isEmpty())
        {
            errorAlert.setContentText("Empty List");
            errorAlert.showAndWait();
            return;
        }
        else
        {
            cap= caption.getText();
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
                            Users.get(userIndex).getAlbums().get(albumIndex).getPictures().get(i).getDate(),
                            Users.get(userIndex).getAlbums().get(albumIndex).getPictures().get(i).getTagNameArray(),
                            Users.get(userIndex).getAlbums().get(albumIndex).getPictures().get(i).getTagValueArray()));
                }
            table.setItems(photos);
            }

        }
        caption.clear();
    }

    /**
     * Copies a picture from the current album into a different album
     */
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
        LocalDateTime date = Users.get(userIndex).getAlbums().get(albumIndex).getPictures().get(tempPicIndex).getDate();
        Users.get(userIndex).getAlbums().get(tempAlbumIndex).addPicture(pictureSelected,caption,date);

    }

    /**
     * Moves the picture selected from the current album and deletes it from there and moves it into the album that
     * user wants to move it to.
     */
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
        LocalDateTime date = Users.get(userIndex).getAlbums().get(albumIndex).getPictures().get(tempPicIndex).getDate();
        Users.get(userIndex).getAlbums().get(tempAlbumIndex).addPicture(pictureSelected,caption,date);
        Users.get(userIndex).getAlbums().get(albumIndex).deletePicture(tempPicIndex);
        photos.remove(tempPicIndex);
        pictureList.remove(tempPicIndex);
        CopyPictures.setItems(pictureList);
        MovePictures.setItems(pictureList);
    }

    /**
     * Adds a tag if it is a valid statement provided and goes to the addTag to be displayed
     */
    public void addTagButton() {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        if(tagName.getText().isEmpty() || tagValue.getText().isEmpty() || tagPictures.getValue().isEmpty())
        {
            errorAlert.setContentText("Empty text");
            errorAlert.showAndWait();
            return;
        }

        int tempPicIndex = 0;
        String pictureSelected = tagPictures.getValue();
        for(int i = 0 ; i<Users.get(userIndex).getAlbums().get(albumIndex).getPictures().size(); i++)
        {
            if(Users.get(userIndex).getAlbums().get(albumIndex).getPictures().get(i).getImages().equals(pictureSelected))
            {
                tempPicIndex = i;
                break;
            }
        }
        if(!Users.get(userIndex).getAlbums().get(albumIndex).getPictures().get(tempPicIndex).addTag(tagName.getText(),tagValue.getText()))
        {
            return;
        }
        addTag(tempPicIndex);
    }

    /**
     * Displays the tags that were created on two list views of tagName and tagValue
     * @param tempPicIndex the index of which picture is selected
     */
    public void addTag(int tempPicIndex)
    {
        ObservableList<String> tagNameObsList = FXCollections.observableArrayList();
        ObservableList<String> tagValueObsList = FXCollections.observableArrayList();
        for (int i = 0; i < Users.get(userIndex).getAlbums().get(albumIndex).getPictures().get(tempPicIndex).tagSize();i++) {
            tagNameObsList.add(Users.get(userIndex).getAlbums().get(albumIndex).getPicture(tempPicIndex).getTagName(i));
            tagValueObsList.add(Users.get(userIndex).getAlbums().get(albumIndex).getPicture(tempPicIndex).getTagValue(i));
        }
        table.getSelectionModel().select(tempPicIndex);
        tagNameList.setItems(tagNameObsList);
        tagValueList.setItems(tagValueObsList);
        tagValue.clear();
        tagName.clear();

    }

    /**
     * Deletes the tag of the picture
     */
    public void deleteTag()
    {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        int pictureSelected1 = table.getSelectionModel().getSelectedIndex();
        String pictureSelected = table.getItems().get(pictureSelected1).getPathName();
        int selectedName= tagNameList.getSelectionModel().getSelectedIndex();
        int selectedValue = tagValueList.getSelectionModel().getSelectedIndex();
        int tempPicIndex = 0;
        for(int i = 0 ; i<Users.get(userIndex).getAlbums().get(albumIndex).getPictures().size(); i++)
        {
            if(Users.get(userIndex).getAlbums().get(albumIndex).getPictures().get(i).getImages().equals(pictureSelected))
            {
                tempPicIndex = i;
                break;
            }
        }
        if(tagValueList.getSelectionModel().getSelectedIndex() ==-1 || tagNameList.getSelectionModel().getSelectedIndex() ==-1)
        {
            errorAlert.setContentText("There are no tags to remove");
            errorAlert.showAndWait();
            return;
        }
        if(selectedName != selectedValue)
        {
            errorAlert.setContentText("Not correct pair of tags selected");
            errorAlert.showAndWait();
            return;
        }
        Users.get(userIndex).getAlbums().get(albumIndex).getPictures().get(tempPicIndex).deleteTag(selectedName);
        addTag(tempPicIndex);

    }

    /**
     * Goes to the slideshow fxml that shows all the pictures of the album
     * @param e
     * @throws Exception
     */
    public void toSlideShow(ActionEvent e ) throws Exception
    {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        if(photos.size()==0)
        {
            errorAlert.setContentText("There are no pictures to put on a slideshow");
            errorAlert.showAndWait();
        }
        else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/SlideShow.fxml"));

            AnchorPane root = (AnchorPane) loader.load();

            SlideShowController listController = loader.getController();
            listController.start(mainStage, Users, userIndex, albumIndex);

            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.setResizable(false);
            mainStage.show();
        }
    }

    /**
     * Logouts of the user and saves the data which the user did
     * @param e
     * @throws IOException
     */
    public void logoutButton(ActionEvent e) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Logout");
        alert.setHeaderText("Are you sure you want to logout?");
        alert.setContentText("You will be logged out");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            //Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/Login.fxml"));

            AnchorPane root = (AnchorPane) loader.load();

            LoginController listLoginController = loader.getController();
            listLoginController.start(mainStage,Users,obsList);

            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.setResizable(false);
            mainStage.show();

        }
    }
}

package sample;

import com.sun.javafx.iio.ios.IosDescriptor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class UserViewController
{
    @FXML
    ListView<String> listView;
    @FXML
    Label AlbumName;
    @FXML
    Label numberOfPhotos;
    @FXML
    Label dateRange;
    ObservableList<String> obsList;
    ObservableList<String> obsList1;
    ArrayList<String> arrayList;
    ArrayList<User> Users;
    Stage mainStage;
    Alert deleteAlert = new Alert(Alert.AlertType.NONE);
    int currentUserIndex;

    public void start(Stage mainStage,  ObservableList<String> list, ArrayList<User>user, int currentUser)
    {
        this.mainStage = mainStage;
        this.Users = user;
        this.obsList1 = list;
        currentUserIndex = currentUser;
        obsList = FXCollections.observableArrayList();

        arrayList = new ArrayList<String>();

        for(int i = 0; i < user.get(currentUserIndex).getAlbums().size(); i++){
            if(user.get(currentUserIndex).getAlbums().size() == 0)
            {
                break;
            }
            obsList.add(user.get(currentUserIndex).getAlbums().get(i).getName());
        }
        listView.setItems(obsList);

        listView.getSelectionModel().selectedIndexProperty().addListener(e ->
        {
            AlbumName.setText(Users.get(currentUserIndex).getAlbums().get(listView.getSelectionModel().getSelectedIndex()).getName());
            numberOfPhotos.setText("" +Users.get(currentUserIndex).getAlbums().get(listView.getSelectionModel().getSelectedIndex()).size());
            if(Users.get(currentUserIndex).getAlbums().get(listView.getSelectionModel().getSelectedIndex()).size() > 0) {
                LocalDateTime latestTime = Users.get(currentUserIndex).getAlbums().get(listView.getSelectionModel().getSelectedIndex()).getPictures().get(0).getDate();
                LocalDateTime oldestTime = latestTime;
                int latestIndex = 0;
                int oldestIndex = 0;
                for (int i = 1; i < Users.get(currentUserIndex).getAlbums().get(listView.getSelectionModel().getSelectedIndex()).size(); i++)
                    if (latestTime.compareTo(Users.get(currentUserIndex).getAlbums().get(listView.getSelectionModel().getSelectedIndex()).getPictures().get(i).getDate()) < 0){
                        latestTime = Users.get(currentUserIndex).getAlbums().get(listView.getSelectionModel().getSelectedIndex()).getPictures().get(i).getDate();
                        latestIndex = i;
                    }
                    else if (oldestTime.compareTo(Users.get(currentUserIndex).getAlbums().get(listView.getSelectionModel().getSelectedIndex()).getPictures().get(i).getDate()) > 0)
                    {
                        oldestTime = Users.get(currentUserIndex).getAlbums().get(listView.getSelectionModel().getSelectedIndex()).getPictures().get(i).getDate();
                        oldestIndex = i;
                    }

                //System.out.println("LATEST TIME IS " + users.get(userIndex).getAlbum().get(albumListView.getSelectionModel().getSelectedIndex()).getImages().get(latestIndex).getDate().toString());
                //System.out.println("OLDEST TIME IS " + users.get(userIndex).getAlbum().get(albumListView.getSelectionModel().getSelectedIndex()).getImages().get(oldestIndex).getDate().toString());

                dateRange.setText(Users.get(currentUserIndex).getAlbums().get(listView.getSelectionModel().getSelectedIndex()).getPictures().get(oldestIndex).getDate().toString()
                        + "\n to \n" +
                        Users.get(currentUserIndex).getAlbums().get(listView.getSelectionModel().getSelectedIndex()).getPictures().get(latestIndex).getDate().toString());
            }
            else
                dateRange.setText("No images, no range");
        });
        listView.getSelectionModel().select(0);
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
            listController.start(mainStage,Users,obsList1);

            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.setResizable(false);
            mainStage.show();

        }
    }

    public void createAlbum(ActionEvent e) throws IOException
    {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        TextInputDialog td = new TextInputDialog();
        //td.getResult();
        td.setHeaderText("enter Album");
        Optional<String> result = td.showAndWait();
        //String result = td.getResult();


        if(!result.isPresent()||result.get().equals(null)||result.get().equals(""))
        {
           errorAlert.setContentText("Empty text or canceled");
           errorAlert.showAndWait();
            return;
        }
        else
        {
            for(int i =0; i <obsList.size();i++)
            {
                if(obsList.get(i).equals(result))
                {
                   errorAlert.setContentText("Album name already created");
                   errorAlert.showAndWait();
                    return;
                }
            }
        }
        obsList.add(result.get());
        listView.setItems(obsList);
        Users.get(currentUserIndex).addAlbum(result.get());
        td.show();
        td.close();
        listView.getSelectionModel().select(0);

    }

    public void deleteAlbum(ActionEvent e) throws IOException {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        int index = listView.getSelectionModel().getSelectedIndex();
        if (obsList.isEmpty())
        {
            errorAlert.setContentText("Empty list");
            errorAlert.showAndWait();
            return;
        } else
            {

            if (obsList.size() != 0)
            {
                obsList.remove(index);
                Users.get(currentUserIndex).deleteAlbum(index);
            }

        }
    }

    public void renameAlbum(ActionEvent e) throws IOException
    {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        int index = listView.getSelectionModel().getSelectedIndex();
        if(obsList.isEmpty())
        {
            errorAlert.setContentText("Empty list");
            errorAlert.showAndWait();
            return;
        }
        else
        {

            if(obsList.size() != 0 )
            {
                TextInputDialog td = new TextInputDialog();
                td.getResult();
                td.setHeaderText("Rename Album");
                Optional<String> result1 = td.showAndWait();
                String result = td.getResult();
                for(int i =0; i <obsList.size();i++)
                {
                    if(obsList.get(i).equals(result))
                    {
                        errorAlert.setContentText("Album name already created");
                        errorAlert.showAndWait();
                        return;
                    }
                }
                obsList.add(result);
                listView.setItems(obsList);
                Users.get(currentUserIndex).renameAlbum(index,result);

                td.show();
                td.close();
            }
        }
        obsList.remove(index);

    }

    public void openAlbum(ActionEvent e) throws IOException
    {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        int index = listView.getSelectionModel().getSelectedIndex();
        if(obsList.isEmpty())
        {
            errorAlert.setContentText("Noting to open");
            errorAlert.showAndWait();
            return;
        }
        else
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AlbumView.fxml"));

            AnchorPane root = (AnchorPane) loader.load();

            AlbumViewController listController = loader.getController();
            listController.start(mainStage,Users,currentUserIndex,index);

            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.setResizable(false);
            mainStage.show();
        }
    }

    public void Search(ActionEvent e) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SearchView.fxml"));

        AnchorPane root = (AnchorPane) loader.load();

        SearchViewController listController = loader.getController();
        listController.start(mainStage,Users,currentUserIndex);

        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();
    }
}

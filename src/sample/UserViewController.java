/**
 * @author Haseeb Balal
 * @author Muffalal Hussain
 */
package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
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
    /**
     * the list of users
     */
    ObservableList<String> obsList;

    /**
     * list of users
     */

    ObservableList<String> obsList1;
    /**
     * list
     */

    ArrayList<String> arrayList;
    /**
     * temporarily array list
     */
    ArrayList<User> Users;

    Stage mainStage;
    /**
     * alert message
     */
    Alert deleteAlert = new Alert(Alert.AlertType.NONE);
    /**
     * current user placer
     */
    int currentUserIndex;


    /**
     *
     * @param mainStage the scene which it is currenly
     * @param list the list of users
     * @param user The list of users of User arraylist
     * @param currentUser current user index
     */
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

                dateRange.setText(Users.get(currentUserIndex).getAlbums().get(listView.getSelectionModel().getSelectedIndex()).getPictures().get(oldestIndex).getDate().toString()
                        + "\n to \n" +
                        Users.get(currentUserIndex).getAlbums().get(listView.getSelectionModel().getSelectedIndex()).getPictures().get(latestIndex).getDate().toString());
            }
            else
                dateRange.setText("No images, no range");
        });
        listView.getSelectionModel().select(0);
    }


    /**
     * logouts of the user
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
            System.out.println("logout");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Login.fxml"));

            AnchorPane root = (AnchorPane) loader.load();

            LoginController listLoginController = loader.getController();
            listLoginController.start(mainStage,Users,obsList1);

            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.setResizable(false);
            mainStage.show();

        }
    }

    /**
     * Creates a new album
     * @param e
     * @throws IOException
     */
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
                if(obsList.get(i).equals(result.get()))
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

    /**
     * delets the album
     * @param e
     * @throws IOException
     */
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

    /**
     * renames the album
     * @param e
     * @throws IOException
     */
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

    /**
     * Goes to albumview fxml
     * @param e
     * @throws IOException
     */
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

    /**
     * Goes to search fxml
     * @param e
     * @throws IOException
     */
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

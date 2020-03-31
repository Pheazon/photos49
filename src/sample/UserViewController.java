package sample;

import com.sun.javafx.iio.ios.IosDescriptor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class UserViewController
{
    @FXML
    ListView<String> listView;
    ObservableList<String> obsList;
    ArrayList<String> arrayList;
    Stage mainStage;
    Alert deleteAlert = new Alert(Alert.AlertType.NONE);

    public void start(Stage mainStage)
    {
        this.mainStage = mainStage;

        obsList = FXCollections.observableArrayList();

        arrayList = new ArrayList<String>();

        for(int i = 0; i < arrayList.size(); i++){
            if(arrayList.isEmpty())
            {
                break;
            }
            obsList.add(arrayList.get(i));
        }
        listView.setItems(obsList);
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

    public void createAlbum(ActionEvent e) throws IOException
    {
        TextInputDialog td = new TextInputDialog();
        td.getResult();
        td.setHeaderText("enter Album");
        Optional<String> result1 = td.showAndWait();
        String result = td.getResult();
        obsList.add(result);
        arrayList.add(result);
        listView.setItems(obsList);

        td.show();
        td.close();

    }

    public void deleteAlbum(ActionEvent e) throws IOException
    {
        int index = listView.getSelectionModel().getSelectedIndex();
        if(obsList.isEmpty())
        {
            return;
        }
        else {

            if (arrayList.size() != 0) {
                obsList.remove(index);
                arrayList.remove(index);
            }
        }
    }

    public void renameAlbum(ActionEvent e) throws IOException
    {
        int index = listView.getSelectionModel().getSelectedIndex();
        if(obsList.isEmpty())
        {
            return;
        }
        else
        {
            if(arrayList.size() != 0 )
            {
                TextInputDialog td = new TextInputDialog();
                td.getResult();
                td.setHeaderText("Rename Album");
                Optional<String> result1 = td.showAndWait();
                String result = td.getResult();
                obsList.add(result);
                arrayList.add(result);
                listView.setItems(obsList);

                td.show();
                td.close();
            }
        }
        obsList.remove(index);
        arrayList.remove(index);

    }

    public void openAlbum(ActionEvent e) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AlbumView.fxml"));

        AnchorPane root = (AnchorPane) loader.load();

       AlbumViewController listController = loader.getController();
       listController.start(mainStage);

        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();
    }

    public void Search(ActionEvent e) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SearchView.fxml"));

        AnchorPane root = (AnchorPane) loader.load();

        SearchViewController listController = loader.getController();
        listController.start(mainStage);

        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();
    }
}

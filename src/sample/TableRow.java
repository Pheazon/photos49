package sample;

import javafx.scene.image.ImageView;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * TableRow is used to get the outline for the table used to display thumbnails of the images.
 * @author user
 *
 */
public class TableRow {
    private ImageView image;
    private String name;
    private String pathName;
    private LocalDateTime date;
    private ArrayList<String> tagNames;
    private ArrayList<String> tagValues;

    TableRow(ImageView img, String name, String pathName, LocalDateTime date, ArrayList<String> tagNames, ArrayList<String> tagValues)
    {
        this.image = img;
        this.name = name;
        this.pathName = pathName;
        this.date = date;
        this.tagNames = tagNames;
        this.tagValues = tagValues;

    }
    TableRow(ImageView img)
    {
        this.image = img;
        name = "";
        pathName = "";
        date = null;

    }
    public ArrayList<String> getTagNames()
    {
        return tagNames;
    }
    public ArrayList<String> getTagValues()
    {
        return tagValues;
    }
    public void setImage(ImageView value) {
        image = value;
    }

    public ImageView getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getPathName() {
        return pathName;
    }

}

package sample;

import javafx.scene.image.ImageView;

/**
 * TableRow is used to get the outline for the table used to display thumbnails of the images.
 * @author user
 *
 */
public class TableRow {
    private ImageView image;
    private String name;
    private String pathName;
    private String date;
    TableRow(ImageView img, String name, String pathName, String date)
    {
        this.image = img;
        this.name = name;
        this.pathName = pathName;
        this.date = date;
    }
    TableRow(ImageView img)
    {
        this.image = img;
        name = "";
        pathName = "";
        date = "";
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

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getPathName() {
        return pathName;
    }
}

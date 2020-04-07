package sample;

import javafx.scene.image.ImageView;

import java.time.LocalDate;

/**
 * TableRow is used to get the outline for the table used to display thumbnails of the images.
 * @author user
 *
 */
public class TableRow {
    private ImageView image;
    private String name;
    private String pathName;
    private LocalDate date;
    TableRow(ImageView img, String name, String pathName, LocalDate date)
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
        date = null;
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

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getPathName() {
        return pathName;
    }
}

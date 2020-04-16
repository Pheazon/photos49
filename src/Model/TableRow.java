package Model;

import javafx.scene.image.ImageView;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * TableRow is used to get the outline for the table used to display thumbnails of the images.
 * @author user
 *
 */
public class TableRow {

    /**
     *The image of the picture
     */
    private ImageView image;

    /**
     * The caption
     */
    private String name;

    /**
     * the url for the image
     */
    private String pathName;

    /**
     * the date of the image
     */
    private LocalDateTime date;

    /**
     * the list of tagNames
     */
    private ArrayList<String> tagNames;

    /**
     * the list of tagValues
     */
    private ArrayList<String> tagValues;

    /**
     * The constructor for the table with all parameters given values
     * @param img the image
     * @param name the caption
     * @param pathName the url for the image
     * @param date the date of the image
     * @param tagNames the tag name list
     * @param tagValues the tag value list
     */
    public TableRow(ImageView img, String name, String pathName, LocalDateTime date, ArrayList<String> tagNames, ArrayList<String> tagValues)
    {
        this.image = img;
        this.name = name;
        this.pathName = pathName;
        this.date = date;
        this.tagNames = tagNames;
        this.tagValues = tagValues;

    }

    /**
     * The constructor with just image paramter given
     * @param img the image
     */
    TableRow(ImageView img)
    {
        this.image = img;
        name = "";
        pathName = "";
        date = null;

    }

    /**
     *A getter that gets the tagName list
     * @return the list of tagNames
     */
    public ArrayList<String> getTagNames()
    {
        return tagNames;
    }

    /**
     * A getter that gets the tagValue list
     * @return the list of tagValues
     */
    public ArrayList<String> getTagValues()
    {
        return tagValues;
    }

    /**
     * A setter that sets the image to a new image
     * @param value the image to change
     */
    public void setImage(ImageView value) {
        image = value;
    }

    /**
     * A getter that gets the image
     * @return the image
     */
    public ImageView getImage() {
        return image;
    }

    /**
     * A setter that sets the caption
     * @param name is the new caption to change to
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * A getter that gets the captiom
     * @return the caption for the image
     */
    public String getName() {
        return name;
    }

    /**
     * A setter that sets the date for the image
     * @param date to set the new date
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * A getter to get the date of the image
     * @return the date of the image
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * A setter to set the url for the image
     * @param pathName the new url for the image to be created
     */
    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    /**
     * A getter to get the url of the image
     * @return pathname of the image
     */
    public String getPathName() {
        return pathName;
    }

}

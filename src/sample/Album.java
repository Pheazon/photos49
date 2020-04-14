/**
 * @author Haseeb Balal
 * @author Muffalal Hussain
 */
package sample;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Creates an album for a user
 */
public class Album implements Serializable
{
    /**
     * the name of the album
     */
    private String name;

    /**
     * list of the the images that are created with the Images class
     */
    private ArrayList<Images> pictures;


    /**
     * Constructor with only album name given
     * @param n the name of the album
     */
    public Album(String n)
    {
        this.name = n;
        pictures = new ArrayList<>();
    }

    /**
     * Constructor with name given and pictures created
     * @param n the name of the album
     * @param picture the list of the images
     */
    public Album(String n,ArrayList<Images> picture)
    {
        this.name = n;
        this.pictures = picture;
    }

    /**
     *A getter method that gets the name of the album
     * @return the name of the album
     */
    public String getName() {
        return name;
    }

    /**
     * A setter method that sets the album name to something else
     * @param name the name of the album which you want to change to
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * A getter method that returns the picture arraylist
     * @return the array list of pictures which is in the album
     */
    public ArrayList<Images> getPictures() {
        return pictures;
    }

    /**
     * gets the specific picture in the arraylist
     * @param index which index of the array list you want to get
     * @return returns the picture with the index you are looking for
     */
    public Images getPicture(int index)
    {
        return this.pictures.get(index);
    }

    /**
     * A method that gets the size of the corresponding arraylist that you get
     * @return gets the size of the arraylist
     */
    public int size()
    {
        return pictures.size();
    }

    /**
     * A method that adds a picture into the array list pictures
     * @param url the local url that is needed to create the picture
     * @param caption the caption of the picture
     * @param date the date which it was created
     */
    public void addPicture(String url, String caption, LocalDateTime date)
    {
        pictures.add(new Images(url,caption, date));
    }


    /**
     * Deletes the picture with in the array list that is chosen by the user
     * @param index the index of which picture to delete
     */
    public void deletePicture(int index)
    {
        pictures.remove(index);
    }
}

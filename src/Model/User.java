package Model;

import Model.Album;

import java.util.ArrayList;
import java.io.Serializable;

public class User implements Serializable {

    /**
     * The name of the username
     */
    String username;

    /**
     * The list of albums for the the user
     */
    ArrayList<Album> albums;


    /**
     * A constructor for the User with just username
     * @param username name of user
     */
    public User(String username)
    {
        this.username = username;
        albums = new ArrayList<>();

    }

    /**
     * A constructor for the user with both values inputted
     * @param username the name of user
     * @param albums the list of albums for the user
     */
    public User(String username, ArrayList<Album>albums)
    {
        this.username = username;
        this.albums = albums;
    }


    /**
     * A setter to the userName to a new one
     * @param username the name you want to change to
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * A getter to get the userName
     * @return to get the userName
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * A getter to get the list of albums for the user
     * @return list of albums for user
     */
    public ArrayList<Album> getAlbums()
    {
        return albums;
    }

    /**
     * A to String to get the userName
     * @return the userName
     */
    public String toString()
    {
        return "username is " + username ;
    }

    /**
     * Adds an album to the album list
     * @param albumName album name
     */
    public void addAlbum(String albumName)
    {
        albums.add(new Album(albumName));
    }

    /**
     * Renames the album to something else
     * @param index the location of the album name you want to change in the list
     * @param albumName the new name for the album
     */
    public void renameAlbum(int index, String albumName)
    {
        albums.get(index).setName(albumName);
    }

    /**
     * delete an album from the album list
     * @param albumIndex the location of the album you want to remove from the list
     */
    public void deleteAlbum(int albumIndex)
    {
        albums.remove(albumIndex);
    }
}

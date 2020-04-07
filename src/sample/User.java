package sample;


import java.util.ArrayList;

public class User {

    String username;
    ArrayList<Album> albums;


    public User(String username)
    {
        this.username = username;
        albums = new ArrayList<>();

    }
    public User(String username, ArrayList<Album>albums)
    {
        this.username = username;
        this.albums = albums;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername()
    {
        return username;
    }
    public ArrayList<Album> getAlbums()
    {
        return albums;
    }

    public String toString()
    {
        return "username is " + username ;
    }

    public void addAlbum(String albumName)
    {
        albums.add(new Album(albumName));
    }
    public void renameAlbum(int index, String albumName)
    {
        albums.get(index).setName(albumName);
    }
    public void deleteAlbum(int albumIndex)
    {
        albums.remove(albumIndex);
    }
}

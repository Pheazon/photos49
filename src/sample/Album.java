package sample;

import com.sun.prism.Image;

import java.util.ArrayList;

public class Album
{
    private String name;
    private ArrayList<Images> pictures;

    public Album(String n)
    {
        this.name = n;
        pictures = new ArrayList<>();
    }

    public Album(String n,ArrayList<Images> picture)
    {
        this.name = n;
        this.pictures = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Images> getPictures() {
        return pictures;
    }

    public void setPictures(ArrayList<Images> pictures) {
        this.pictures = pictures;
    }

    public void addPicture(String url, String caption, String date)
    {
        pictures.add(new Images(url,caption, date));
    }

    public void deletePicture(int index)
    {
        pictures.remove(index);
    }
}

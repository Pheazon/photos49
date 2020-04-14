package sample;

import com.sun.prism.Image;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.io.Serializable;

public class Album implements Serializable
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

    public Images getPicture(int index)
    {
        return this.pictures.get(index);
    }
    public int size()
    {
        return pictures.size();
    }
    public void addPicture(String url, String caption, LocalDateTime date)
    {
        pictures.add(new Images(url,caption, date));
    }


    public void deletePicture(int index)
    {
        pictures.remove(index);
    }
}

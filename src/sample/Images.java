package sample;

import javafx.scene.image.Image;

public class Images
{
    private String images;
    private String caption;
    private String date;
    public Images(String image,String caption,String date)
    {
        this.images = image;
        this.caption = caption;
        this.date = date;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }


    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

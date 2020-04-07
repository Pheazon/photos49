package sample;


import java.time.LocalDate;
public class Images
{
    private String images;
    private String caption;
    private LocalDate date;
    public Images(String image,String caption,LocalDate date)
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

package sample;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Images implements Serializable
{
    private String images;
    private String caption;
    private LocalDateTime date;
    private ArrayList<String> tagName;
    private ArrayList<String> tagValue;

    public Images(String image)
    {
        this.images = image;
        caption = "";
        date = null;
        tagName = new ArrayList<>();
        tagValue = new ArrayList<>();
    }
    public Images(String image,String caption,LocalDateTime date)
    {
        this.images = image;
        this.caption = caption;
        this.date = date;
        tagName = new ArrayList<>();
        tagValue = new ArrayList<>();
    }
    public Images(String image,String caption,LocalDateTime date,ArrayList<String>tagName,ArrayList<String> tagValue)
    {
        this.images = image;
        this.caption = caption;
        this.date = date;
        this.tagName = tagName;
        this.tagValue = tagValue;

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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date)
    {
        this.date = date;
    }

    public String getTagName(int index)
    {
        return tagName.get(index);
    }

    public void setTagName(ArrayList<String> tagName) {
        this.tagName = tagName;
    }

    public String getTagValue(int index)
    {
        return tagValue.get(index);
    }
    public String getTagValue(String tagName)
    {
        for(int i=0;i<this.tagName.size();i++)
            if(this.tagName.get(i).equals(tagName))
                return tagValue.get(i);
            return null;
    }
    public ArrayList<String> getTagNameArray()
    {
        return this.tagName;
    }
    public ArrayList<String> getTagValueArray()
    {
        return this.tagValue;
    }
    public void setTags(ArrayList<String> tagName, ArrayList<String> tagValue)
    {
        this.tagName = tagName;
        this.tagValue = tagValue;
    }

    public void deleteTag(int index)
    {
        tagName.remove(index);
        tagValue.remove(index);
    }

    public boolean addTag(String tagName,String tagValue)
    {
        if(getTagValue(tagName) != null && getTagValue(tagName).equals(tagValue))
        {
            return false;
        }
        this.tagName.add(tagName);
        this.tagValue.add(tagValue);
        return true;
    }
    public int tagSize()
    {
        return tagValue.size();
    }
}

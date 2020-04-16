/**
 * @author Haseeb Balal
 * @author Muffalal Hussain
 */
package Model;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * A class to create each image with the url,caption,date, and tags
 */
public class Images implements Serializable
{
    /**
     * the url of the image
     */
    private String images;

    /**
     * the caption of the image
     */
    private String caption;

    /**
     *the date the image was created
     */
    private LocalDateTime date;

    /**
     * the list of tagNames of the image
     */
    private ArrayList<String> tagName;

    /**
     * the list of tagValues of the image
     */
    private ArrayList<String> tagValue;

    /**
     * Constructor of the Images class with just the image url
     * @param image the url of the image
     */
    public Images(String image)
    {
        this.images = image;
        caption = "";
        date = null;
        tagName = new ArrayList<>();
        tagValue = new ArrayList<>();
    }

    /**
     * Constructor of the Images class with url,caption and date
     * @param image the url of the image
     * @param caption the caption of the image
     * @param date the date of the image was created
     */
    public Images(String image,String caption,LocalDateTime date)
    {
        this.images = image;
        this.caption = caption;
        this.date = date;
        tagName = new ArrayList<>();
        tagValue = new ArrayList<>();
    }

    /**
     * Constructor of the Images class with url,caption,date and tagName and tagValue list
     * @param image the url of the image
     * @param caption the caption of the image
     * @param date the date of the image was created
     * @param tagName the tagName list
     * @param tagValue the tagValue list
     */
    public Images(String image,String caption,LocalDateTime date,ArrayList<String>tagName,ArrayList<String> tagValue)
    {
        this.images = image;
        this.caption = caption;
        this.date = date;
        this.tagName = tagName;
        this.tagValue = tagValue;

    }

    /**
     * A getter method that gets the image url
     * @return the url of the image
     */
    public String getImages() {
        return images;
    }

    /**
     * A getter method that gets the caption of the image
     * @return the caption of the image
     */
    public String getCaption() {
        return caption;
    }

    /**
     * A setter method that sets the caption to a new caption
     * @param caption the caption to change the caption
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     * A getter to get the date of the image
     * @return the date of the image
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * A getter to get the tag name using the index
     * @param index to get the tag from the tagName arraylist
     * @return the tagName of the given index of the tagname list
     */
    public String getTagName(int index)
    {
        return tagName.get(index);
    }

    /**
     * A getter to get the tag value using the index
     * @param index to get the tag value from the tagValue arraylist
     * @return the tagValue of the given index of the tagValue list
     */
    public String getTagValue(int index)
    {
        return tagValue.get(index);
    }

    /**
     * A getter to get the tag value using the tagName
     * @param tagName the tagName of which tag value to get
     * @return the tagValue using the tagName
     */
    public ArrayList<String> getTagValue(String tagName)
    {
        ArrayList<String> tags = new ArrayList<>();
        for(int i=0;i<this.tagName.size();i++)
        {
            if (this.tagName.get(i).equals(tagName))
                tags.add(tagValue.get(i));
        }
            return tags;
    }

    /**
     *A getter to get the arrayList of tagName
     * @return the arraylist of tagName
     */
    public ArrayList<String> getTagNameArray()
    {
        return this.tagName;
    }

    /**
     * A getter to get the arraylist of tagValue
     * @return the arraylist of tagValue
     */
    public ArrayList<String> getTagValueArray()
    {
        return this.tagValue;
    }

    /**
     * A setter to set the Tag using tagName arraylist and tagValue arraylist
     * @param tagName the arraylist of tagName
     * @param tagValue the arraylist of tagValue
     */
    public void setTags(ArrayList<String> tagName, ArrayList<String> tagValue)
    {
        this.tagName = tagName;
        this.tagValue = tagValue;
    }

    /**
     * Deletes a tag using which index to delete from the tag name and tag value arraylist
     * @param index to get which index to delete
     */
    public void deleteTag(int index)
    {
        tagName.remove(index);
        tagValue.remove(index);
    }

    /**
     * Adds tag to an image
     * @param tagName what the tagName is to be added
     * @param tagValue what the tagValue is to be added
     * @return true if its possible to add the Tag
     */
    public boolean addTag(String tagName,String tagValue)
    {
        for(int i = 0; i <getTagValue(tagName).size(); i++)
        {
            if(getTagValue(tagName).get(i).equals(tagValue))
            {
                return false;
            }
        }
        this.tagName.add(tagName);
        this.tagValue.add(tagValue);
        return true;
    }

    /**
     * Size of the tag name array list
     * @return the size of the tag name array list
     */
    public int tagSize()
    {
        return tagValue.size();
    }
}

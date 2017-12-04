
package com.smartysoft.receipeapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recepie implements Parcelable
{

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("subTitle")
    @Expose
    private String subTitle;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;
    public final static Creator<Recepie> CREATOR = new Creator<Recepie>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Recepie createFromParcel(Parcel in) {
            return new Recepie(in);
        }

        public Recepie[] newArray(int size) {
            return (new Recepie[size]);
        }

    }
    ;

    protected Recepie(Parcel in) {
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.subTitle = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Recepie() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(title);
        dest.writeValue(subTitle);
        dest.writeValue(description);
        dest.writeValue(image);
    }

    public int describeContents() {
        return  0;
    }

}

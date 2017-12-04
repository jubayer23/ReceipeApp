
package com.smartysoft.receipeapp.model;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecepieInfo implements Parcelable
{

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("recepies")
    @Expose
    private List<Recepie> recepies = null;
    @SerializedName("nextPageToken")
    @Expose
    private String nextPageToken;
    public final static Creator<RecepieInfo> CREATOR = new Creator<RecepieInfo>() {


        @SuppressWarnings({
            "unchecked"
        })
        public RecepieInfo createFromParcel(Parcel in) {
            return new RecepieInfo(in);
        }

        public RecepieInfo[] newArray(int size) {
            return (new RecepieInfo[size]);
        }

    }
    ;

    protected RecepieInfo(Parcel in) {
        this.status = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.recepies, (Recepie.class.getClassLoader()));
        this.nextPageToken = ((String) in.readValue((String.class.getClassLoader())));
    }

    public RecepieInfo() {
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Recepie> getRecepies() {
        return recepies;
    }

    public void setRecepies(List<Recepie> recepies) {
        this.recepies = recepies;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeList(recepies);
        dest.writeValue(nextPageToken);
    }

    public int describeContents() {
        return  0;
    }

}

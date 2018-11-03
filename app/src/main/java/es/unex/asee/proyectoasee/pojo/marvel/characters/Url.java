
package es.unex.asee.proyectoasee.pojo.marvel.characters;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Url {

   /* public Url(Parcel in) {
        readFromParcel(in);
    }

    public Url() {

    }*/

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("url")
    @Expose
    private String url;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

   /* @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(url);
    }

    private void readFromParcel(Parcel in) {
        type = in.readString();
        url = in.readString();

    }

    public static final Parcelable.Creator<Url> CREATOR
            = new Creator<Url>() {
        public Url createFromParcel(Parcel in) {
            return new Url(in);
        }

        public Url[] newArray(int size) {
            return new Url[size];
        }
    };*/

}

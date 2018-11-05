
package es.unex.asee.proyectoasee.pojo.marvel.comicDetails;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import es.unex.asee.proyectoasee.pojo.marvel.characterDetails.CharacterDetails;

public class ComicDetails implements Parcelable {

    public ComicDetails() {

    }

    public ComicDetails(Parcel in) {
        readFromParcel(in);
    }

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("attributionText")
    @Expose
    private String attributionText;
    @SerializedName("attributionHTML")
    @Expose
    private String attributionHTML;
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("data")
    @Expose
    private Data data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getAttributionText() {
        return attributionText;
    }

    public void setAttributionText(String attributionText) {
        this.attributionText = attributionText;
    }

    public String getAttributionHTML() {
        return attributionHTML;
    }

    public void setAttributionHTML(String attributionHTML) {
        this.attributionHTML = attributionHTML;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(status);
        dest.writeString(copyright);
        dest.writeString(attributionText);
        dest.writeString(attributionHTML);
        dest.writeString(etag);
        dest.writeValue(data);
    }

    private void readFromParcel(Parcel in) {
        code = in.readInt();
        status = in.readString();
        copyright = in.readString();
        attributionText = in.readString();
        attributionHTML = in.readString();
        etag = in.readString();

        in.readValue(data);

    }

    public static final Parcelable.Creator<CharacterDetails> CREATOR
            = new Creator<CharacterDetails>() {
        public CharacterDetails createFromParcel(Parcel in) {
            return new CharacterDetails(in);
        }

        public CharacterDetails[] newArray(int size) {
            return new CharacterDetails[size];
        }
    };
}

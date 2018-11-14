package es.unex.asee.proyectoasee.database.Entities.Comics;

public class ComicStateDataJOIN {

    public ComicStateDataJOIN(Integer id, String name, String thumbnailPath, String thumbnailExtension, boolean favorite, float rating, boolean read, boolean reading) {
        this.id = id;
        this.name = name;
        this.thumbnailPath = thumbnailPath;
        this.thumbnailExtension = thumbnailExtension;
        this.favorite = favorite;
        this.rating = rating;
        this.read = read;
        this.reading = reading;
    }

    Integer id;
    String name;
    String thumbnailPath;
    String thumbnailExtension;
    boolean favorite;
    float rating;
    boolean read;
    boolean reading;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getThumbnailExtension() {
        return thumbnailExtension;
    }

    public void setThumbnailExtension(String thumbnailExtension) {
        this.thumbnailExtension = thumbnailExtension;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isReading() {
        return reading;
    }

    public void setReading(boolean reading) {
        this.reading = reading;
    }
}

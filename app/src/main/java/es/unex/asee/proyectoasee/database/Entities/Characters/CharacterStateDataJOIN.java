package es.unex.asee.proyectoasee.database.Entities.Characters;

public class CharacterStateDataJOIN {

    public CharacterStateDataJOIN(Integer id, String name, String thumbnailPath, String thumbnailExtension, boolean favorite, float rating) {
        this.id = id;
        this.name = name;
        this.thumbnailPath = thumbnailPath;
        this.thumbnailExtension = thumbnailExtension;
        this.favorite = favorite;
        this.rating = rating;
    }

    Integer id;
    String name;
    String thumbnailPath;
    String thumbnailExtension;
    boolean favorite;
    float rating;

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
}

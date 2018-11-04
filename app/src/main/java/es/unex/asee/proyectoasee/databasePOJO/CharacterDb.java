package es.unex.asee.proyectoasee.databasePOJO;

public class CharacterDb {

    private Long id;
    private Long favorite;
    private Float rating;

    public CharacterDb() {}

    public CharacterDb(Long id, Long favorite, Float rating) {
        this.id = id;
        this.favorite = favorite;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFavorite() {
        return favorite;
    }

    public void setFavorite(Long favorite) {
        this.favorite = favorite;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}

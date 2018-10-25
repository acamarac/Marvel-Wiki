package es.unex.asee.proyectoasee.interfaces;

import es.unex.asee.proyectoasee.pojo.marvel.characters.Characters;
import es.unex.asee.proyectoasee.pojo.marvel.characters.Data;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("characters")
    Call<Characters> getCharactersData(@Query("ts") String ts, @Query("apikey") String apiKey, @Query("hash") String hash);

}

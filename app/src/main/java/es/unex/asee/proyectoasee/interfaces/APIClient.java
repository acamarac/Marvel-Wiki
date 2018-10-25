package es.unex.asee.proyectoasee.interfaces;

import es.unex.asee.proyectoasee.pojo.marvel.characters.Data;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIClient {

    @GET("characters")
    Call<Data> getCharactersData(@Query("api_key") String apiKey);

}

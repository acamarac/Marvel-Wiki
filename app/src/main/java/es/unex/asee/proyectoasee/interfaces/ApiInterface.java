package es.unex.asee.proyectoasee.interfaces;

import es.unex.asee.proyectoasee.pojo.marvel.characterDetails.CharacterDetails;
import es.unex.asee.proyectoasee.pojo.marvel.characters.Characters;
import es.unex.asee.proyectoasee.pojo.marvel.comicDetails.ComicDetails;
import es.unex.asee.proyectoasee.pojo.marvel.comics.Comics;
import es.unex.asee.proyectoasee.pojo.marvel.series.Series;
import es.unex.asee.proyectoasee.pojo.marvel.seriesDetails.SeriesDetails;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    /***********************
         - CHARACTERS -
     ***********************/

    @GET("characters")
    Call<Characters> getCharactersData(@Query("ts") String ts, @Query("apikey") String apiKey, @Query("hash") String hash,
                                       @Query("offset") Integer offset);

    @GET("characters/{id}")
    Call<CharacterDetails> getCharacterDetails(@Path("id") Integer id, @Query("ts") String ts, @Query("apikey") String apiKey,
                                               @Query("hash") String hash);

    @GET("characters")
    Call<Characters> getCharacterByName(@Query("ts") String ts, @Query("apikey") String apiKey, @Query("hash") String hash,
                                       @Query("nameStartsWith") String name);


    /***********************
           - COMICS -
     ***********************/

    @GET("comics")
    Call<Comics> getComicsData(@Query("ts") String ts, @Query("apikey") String apiKey, @Query("hash") String hash,
                               @Query("offset") Integer offset);


    @GET("comics/{id}")
    Call<ComicDetails> getComicDetails(@Path("id") Integer id, @Query("ts") String ts, @Query("apikey") String apiKey,
                                       @Query("hash") String hash);

    @GET("comics")
    Call<Comics> getComicByName(@Query("ts") String ts, @Query("apikey") String apiKey, @Query("hash") String hash,
                                        @Query("titleStartsWith") String title);



    /***********************
           - SERIES -
     ***********************/

    @GET("series")
    Call<Series> getSeriesData(@Query("ts") String ts, @Query("apikey") String apiKey, @Query("hash") String hash,
                               @Query("offset") Integer offset);


    @GET("series/{id}")
    Call<SeriesDetails> getSeriesDetails(@Path("id") Integer id, @Query("ts") String ts, @Query("apikey") String apiKey,
                                         @Query("hash") String hash);

    @GET("series")
    Call<Series> getSeriesByName(@Query("ts") String ts, @Query("apikey") String apiKey, @Query("hash") String hash,
                                @Query("titleStartsWith") String title);


}

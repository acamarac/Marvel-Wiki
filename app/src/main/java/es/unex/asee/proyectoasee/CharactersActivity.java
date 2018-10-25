package es.unex.asee.proyectoasee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.proyectoasee.R;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.unex.asee.proyectoasee.interfaces.APIClient;
import es.unex.asee.proyectoasee.pojo.marvel.characters.Data;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CharactersActivity extends AppCompatActivity {

    private static final String TAG = "CharactersActivity";

    private RecyclerView mRecyclerView;
    private APIClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_characters);

        mRecyclerView = (RecyclerView)findViewById(R.id.rViewCharactersList);

        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.api_base_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiClient = retrofit.create(APIClient.class);


        //Set layout manager for recycler view
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, GridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        String apiKey = getResources().getString(R.string.api_key);
        Call<Data> charactersDataCall = apiClient.getCharactersData(apiKey);


        charactersDataCall.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {

                Data charactersData = response.body();

                Log.d(TAG, "onResponse: " + response.code());

            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.d(TAG, "onResponse: " + t.getMessage());
            }
        });

    }

}


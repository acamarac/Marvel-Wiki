package es.unex.asee.proyectoasee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.proyectoasee.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import es.unex.asee.proyectoasee.adapters.CharactersAdapter;
import es.unex.asee.proyectoasee.client.APIClient;
import es.unex.asee.proyectoasee.interfaces.ApiInterface;
import es.unex.asee.proyectoasee.pojo.marvel.characters.Characters;
import es.unex.asee.proyectoasee.pojo.marvel.characters.Data;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CharactersActivity extends AppCompatActivity {

    private static final String TAG = "CharactersActivity";

    private RecyclerView mRecyclerView;
    private ApiInterface apiInterface;

    public static String MD5_Hash(String s) {
        MessageDigest m = null;

        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        m.update(s.getBytes(),0,s.length());
        String hash = new BigInteger(1, m.digest()).toString(16);
        return hash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_characters);

        mRecyclerView = (RecyclerView)findViewById(R.id.rViewCharactersList);

        apiInterface = APIClient.getClient().create(ApiInterface.class);


        //Set layout manager for recycler view
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, GridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        Long tsLong = new Date().getTime();
        String ts = tsLong.toString();

        String apiKey = getResources().getString(R.string.publickey);
        String privateKey = getResources().getString(R.string.privatekey);

        //Tercer parámetro: md5(ts + privatekey + publickey (apikey))
        String hash = ts + privateKey + apiKey;
        String hashResult = MD5_Hash(hash);

        Call<Characters> charactersCall = apiInterface.getCharactersData(ts, apiKey, hashResult);


        charactersCall.enqueue(new Callback<Characters>() {
            @Override
            public void onResponse(Call<Characters> call, Response<Characters> response) {

                Characters characters = response.body();

                Log.d(TAG, "onResponse: " + response.code());

                CharactersAdapter adapter = new CharactersAdapter(characters.getData().getResults(), getApplicationContext());
                mRecyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<Characters> call, Throwable t) {
                Log.d(TAG, "onResponse: " + t.getMessage());
            }
        });

    }

}


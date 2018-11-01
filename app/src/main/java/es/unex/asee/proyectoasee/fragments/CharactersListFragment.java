package es.unex.asee.proyectoasee.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.proyectoasee.R;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import es.unex.asee.proyectoasee.CharactersActivity;
import es.unex.asee.proyectoasee.MainActivity;
import es.unex.asee.proyectoasee.adapters.CharactersAdapter;
import es.unex.asee.proyectoasee.client.APIClient;
import es.unex.asee.proyectoasee.interfaces.ApiInterface;
import es.unex.asee.proyectoasee.pojo.marvel.characters.Characters;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharactersListFragment extends Fragment {

    private View view;
    private static final String TAG = "CharactersActivity";

    private RecyclerView mRecyclerView;
    private ApiInterface apiInterface;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.layout_characters, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Characters");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rViewCharactersList);

        //Create GridView
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);


        requestCharacters();


        return view;
    }


    public void requestCharacters() {

        apiInterface = APIClient.getClient().create(ApiInterface.class);


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

                //If characters is null, then the hash failed and we have to request again
                if (characters.getData() == null) requestCharacters();

                Log.d(TAG, "onResponse: " + response.code());

                CharactersAdapter adapter = new CharactersAdapter(characters.getData().getResults(), view.getContext());
                mRecyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<Characters> call, Throwable t) {
                Log.d(TAG, "onResponse: " + t.getMessage());
            }
        });

    }

    /**
     * Método que permite calcular el md5 de un string.
     * Será utilizado para calcular el hash que debe pasarse por parámetro en la url
     * @param s String del que calcular el hash
     * @return String obtenido tras la realización del algoritmo md5
     */
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

}

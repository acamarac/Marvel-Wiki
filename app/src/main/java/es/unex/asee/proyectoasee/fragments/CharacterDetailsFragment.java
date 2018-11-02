package es.unex.asee.proyectoasee.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.proyectoasee.R;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import es.unex.asee.proyectoasee.client.APIClient;
import es.unex.asee.proyectoasee.interfaces.ApiInterface;
import es.unex.asee.proyectoasee.pojo.marvel.characterDetails.CharacterDetails;
import es.unex.asee.proyectoasee.pojo.marvel.characterDetails.Result;
import es.unex.asee.proyectoasee.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterDetailsFragment extends Fragment {

    private View view;
    private Integer id;

    private static final String TAG = "CharacterDFragment";
    private static final String apiKey = "8930b8251773dc6334474b306aaaa6b6";
    private static final String privateKey = "a6fd8f30a718e8f8f2e8f462ef36a46ee94f9309";
    private static final String imageSize = "/landscape_incredible";

    private ApiInterface apiInterface;

    private CharacterDetails character;

    private TextView tvCharacterName;
    private TextView tvCharacterDescription;
    private ImageView ivCharacterImage;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey("id")) {
            id = getArguments().getInt("id");

            Log.d(TAG, "onCreate: id " + id);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.layout_character_detail, container, false);

        requestCharacterDetails();

        return view;
    }

    public void requestCharacterDetails() {

        apiInterface = APIClient.getClient().create(ApiInterface.class);


        Long tsLong = new Date().getTime();
        String ts = tsLong.toString();

        //Tercer parámetro: md5(ts + privatekey + publickey (apikey))
        String hash = ts + privateKey + apiKey;
        String hashResult = Utils.MD5_Hash(hash);

        Call<CharacterDetails> charactersCall = apiInterface.getCharacterDetails(id, ts, apiKey, hashResult);


        charactersCall.enqueue(new Callback<CharacterDetails>() {
            @Override
            public void onResponse(Call<CharacterDetails> call, Response<CharacterDetails> response) {

                character = response.body();

                loadCharacterData();

            }

            @Override
            public void onFailure(Call<CharacterDetails> call, Throwable t) {
                Log.d(TAG, "onResponse: " + t.getMessage());
            }
        });

    }

    private void loadCharacterData() {

        if (character.getCode().equals("InvalidCredentials")) requestCharacterDetails();

        if (character.getStatus().equals("Ok")) {

            List<Result> characterData = character.getData().getResults();

            Result characterDetail = characterData.get(0);

            String imagePath = characterDetail.getThumbnail().getPath();
            String imageExtension = characterDetail.getThumbnail().getExtension();
            String finalImagePath = imagePath + imageSize + "." + imageExtension;

            tvCharacterName = (TextView) view.findViewById(R.id.tvCharacterName);
            tvCharacterDescription = (TextView) view.findViewById(R.id.tvCharacterDescription);
            ivCharacterImage = (ImageView) view.findViewById(R.id.ivCharacterImage);

            tvCharacterName.setText(characterDetail.getName());

            TextView chDesc = (TextView) view.findViewById(R.id.tvChIntroduction);
            if (characterDetail.getDescription().equals(""))
                chDesc.setVisibility(View.INVISIBLE);
            else
                tvCharacterDescription.setText(characterDetail.getDescription());


            Picasso.with(this.getContext())
                    .load(finalImagePath)
                    .fit()
                    .into(ivCharacterImage);

        }
    }

}

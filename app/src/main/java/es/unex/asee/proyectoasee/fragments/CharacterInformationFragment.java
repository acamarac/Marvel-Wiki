package es.unex.asee.proyectoasee.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import es.unex.asee.proyectoasee.adapters.ComicsInDetailsAdapter;
import es.unex.asee.proyectoasee.client.APIClient;
import es.unex.asee.proyectoasee.interfaces.ApiInterface;
import es.unex.asee.proyectoasee.pojo.marvel.characterDetails.CharacterDetails;
import es.unex.asee.proyectoasee.pojo.marvel.characterDetails.Result;
import es.unex.asee.proyectoasee.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterInformationFragment extends Fragment {

    private View view;
    private CharacterDetails character;

    private static final String TAG = "CharacterIFragment";

    private TextView tvCharacterName;
    private TextView tvCharacterDescription;
    private ImageView ivCharacterImage;


    private static final String imageSize = "/landscape_incredible";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey("Character")) {
            character = getArguments().getParcelable("Character");

            Log.d(TAG, "onCreate: id " + character.getData().getResults().get(0).getName());

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.character_information_fragment, container, false);

        tvCharacterName = (TextView) view.findViewById(R.id.tvCharacterName);
        tvCharacterDescription = (TextView) view.findViewById(R.id.tvCharacterDescription);
        ivCharacterImage = (ImageView) view.findViewById(R.id.ivCharacterImage);

        loadCharacterData();

        return view;
    }


    private void loadCharacterData() {


        if (character.getStatus().equals("Ok")) {

            List<Result> characterData = character.getData().getResults();

            Result characterDetail = characterData.get(0);

            String imagePath = characterDetail.getThumbnail().getPath();
            String imageExtension = characterDetail.getThumbnail().getExtension();
            String finalImagePath = imagePath + imageSize + "." + imageExtension;

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

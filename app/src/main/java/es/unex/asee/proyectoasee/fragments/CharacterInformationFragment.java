package es.unex.asee.proyectoasee.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.android.proyectoasee.R;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import es.unex.asee.proyectoasee.database.DatabaseManager;
import es.unex.asee.proyectoasee.databasePOJO.CharacterDb;
import es.unex.asee.proyectoasee.pojo.marvel.characterDetails.CharacterDetails;
import es.unex.asee.proyectoasee.pojo.marvel.characterDetails.Result;

public class CharacterInformationFragment extends Fragment {

    private View view;
    private CharacterDetails character;

    private static final String TAG = "CharacterIFragment";

    private TextView tvCharacterName;
    private TextView tvCharacterDescription;
    private ImageView ivCharacterImage;
    private Button webButton;

    private static final String imageSize = "/landscape_incredible";

    private DatabaseManager dbManager;
    private CharacterDb characterDb;

    private MaterialFavoriteButton favButton;
    private RatingBar ratingBar;

    private boolean favCharacter = false;
    private float ratingCharacter = 0;

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

        dbManager = new DatabaseManager(view.getContext());
        dbManager.open();

        tvCharacterName = (TextView) view.findViewById(R.id.tvCharacterName);
        tvCharacterDescription = (TextView) view.findViewById(R.id.tvCharacterDescription);
        ivCharacterImage = (ImageView) view.findViewById(R.id.ivCharacterImage);
        webButton = (Button) view.findViewById(R.id.bShowWeb);
        favButton = (MaterialFavoriteButton) view.findViewById(R.id.characterFavButton);
        ratingBar = (RatingBar) view.findViewById(R.id.characterRatingBar);


        characterDb = dbManager.getCharacterInformation(character.getData().getResults().get(0).getId());

        loadCharacterData();

        favButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                favCharacter = favorite;
                Log.d(TAG, "Soy el botón de fav");
            }
        });


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingCharacter = rating;
                Log.d(TAG, "Soy el botón de rating");
            }
        });

        return view;
    }


    private void loadCharacterData() {


        if (character.getStatus().equals("Ok")) {

            final List<Result> characterData = character.getData().getResults();

            final Result characterDetail = characterData.get(0);

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



            webButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    boolean encontrado = false;
                    String urlResource = null;

                    for (int i=0; i<characterDetail.getUrls().size() && !encontrado; i++) {
                        if (characterDetail.getUrls().get(i).getType().equals("detail")) {
                            urlResource = characterDetail.getUrls().get(i).getUrl();
                            encontrado = true;
                        }
                    }

                    if (urlResource != null) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(urlResource));
                        startActivity(i);
                    }
                }
            });

            if (characterDb != null) {

                favCharacter = (characterDb.getFavorite() == 0)?false:true;
                ratingCharacter = characterDb.getRating();

                if (favCharacter == false) favButton.setFavorite(false);
                else favButton.setFavorite(true);

                if (ratingCharacter != 0) {
                    ratingBar.setRating(ratingCharacter);
                }

            }

        }
    }


    @Override
    public void onStop() {
        Log.d(TAG, "Soy el fragment, en onStop");

        Integer idCharacter = character.getData().getResults().get(0).getId();
        Integer favInsert = (favCharacter==true)?1:0;

        CharacterDb characterInsert = new CharacterDb(idCharacter.longValue(), favInsert.longValue(), ratingCharacter);

        if (characterDb == null) {
            dbManager.insertCharacterInformation(characterInsert);
        } else {
            dbManager.updateCharacterInformation(characterInsert);
        }

        dbManager.close();
        super.onStop();
    }
}

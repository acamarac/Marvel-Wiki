package es.unex.asee.proyectoasee.fragments.comics;

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

import es.unex.asee.proyectoasee.databaseOLD.DatabaseManager;
import es.unex.asee.proyectoasee.pojo.marvel.comicDetails.ComicDetails;
import es.unex.asee.proyectoasee.pojo.marvel.comicDetails.Result;

public class ComicInformationFragment extends Fragment{

    private View view;
    private ComicDetails comic;

    private static final String TAG = "ComicIFragment";

    private TextView tvComicName;
    private TextView tvComicDescription;
    private ImageView ivComicImage;
    private Button webButton;

    private static final String imageSize = "/landscape_incredible";

    private DatabaseManager dbManager;
    //private ComicDb comicDb;

    private MaterialFavoriteButton favButton;
    private RatingBar ratingBar;

    private boolean favComic = false;
    private float ratingComic = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // This is a public method that the Activity can use to communicate
    // directly with this Fragment
    public void reciveComic(ComicDetails comic) {
        this.comic = comic;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.character_information_fragment, container, false);

        //dbManager = new DatabaseManager(view.getContext());
        //dbManager.open();

        tvComicName = (TextView) view.findViewById(R.id.tvCharacterName);
        tvComicDescription = (TextView) view.findViewById(R.id.tvCharacterDescription);
        ivComicImage = (ImageView) view.findViewById(R.id.ivCharacterImage);
        webButton = (Button) view.findViewById(R.id.bShowWeb);
        favButton = (MaterialFavoriteButton) view.findViewById(R.id.characterFavButton);
        ratingBar = (RatingBar) view.findViewById(R.id.characterRatingBar);


        //comicDb = dbManager.getCharacterInformation(comic.getData().getResults().get(0).getId());

        loadComicData();

        favButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                favComic = favorite;
                Log.d(TAG, "Soy el botón de fav");
            }
        });


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingComic = rating;
                Log.d(TAG, "Soy el botón de rating");
            }
        });

        return view;
    }


    private void loadComicData() {


        if (comic.getStatus().equals("Ok")) {

            final List<Result> comicData = comic.getData().getResults();

            final Result comicDetail = comicData.get(0);

            String imagePath = comicDetail.getThumbnail().getPath();
            String imageExtension = comicDetail.getThumbnail().getExtension();
            String finalImagePath = imagePath + imageSize + "." + imageExtension;

            tvComicName.setText(comicDetail.getTitle());

            TextView chDesc = (TextView) view.findViewById(R.id.tvChIntroduction);
            if (comicDetail.getDescription() == null)
                chDesc.setVisibility(View.INVISIBLE);
            else
                tvComicDescription.setText(comicDetail.getDescription());


            Picasso.with(this.getContext())
                    .load(finalImagePath)
                    .fit()
                    .into(ivComicImage);



            webButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    boolean encontrado = false;
                    String urlResource = null;

                    for (int i=0; i<comicDetail.getUrls().size() && !encontrado; i++) {
                        if (comicDetail.getUrls().get(i).getType().equals("detail")) {
                            urlResource = comicDetail.getUrls().get(i).getUrl();
                            encontrado = true;
                        }
                    }

                    if (urlResource != null) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(urlResource));
                        startActivity(i);
                    }
                }
            });

            /*if (comicDb != null) {

                favComic = (comicDb.getFavorite() == 0)?false:true;
                ratingComic = comicDb.getRating();

                if (favComic == false) favButton.setFavorite(false);
                else favButton.setFavorite(true);

                if (ratingComic != 0) {
                    ratingBar.setRating(ratingComic);
                }

            }*/

        }
    }


    @Override
    public void onStop() {
        Log.d(TAG, "Soy el fragment, en onStop");

        /*Integer idCharacter = comic.getData().getResults().get(0).getId();
        Integer favInsert = (favComic ==true)?1:0;

        CharacterDb characterInsert = new CharacterDb(idCharacter.longValue(), favInsert.longValue(), ratingComic);

        if (comicDb == null) {
            dbManager.insertCharacterInformation(characterInsert);
        } else {
            dbManager.updateCharacterInformation(characterInsert);
        }

        dbManager.close();*/
        super.onStop();
    }

}

package es.unex.asee.proyectoasee.fragments.comics;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.android.proyectoasee.R;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import es.unex.asee.proyectoasee.database.Entities.ComicEntity;
import es.unex.asee.proyectoasee.database.ViewModel.ComicViewModel;
import es.unex.asee.proyectoasee.pojo.marvel.comicDetails.ComicDetails;
import es.unex.asee.proyectoasee.pojo.marvel.comicDetails.Result;

public class ComicInformationFragment extends Fragment{

    private View view;
    private ComicDetails comic;

    private static final String TAG = "ComicIFragment";

    private TextView tvComicName;
    private TextView tvComicDescription;
    private ImageView ivComicImage;
    private ImageButton webButton;
    private RadioButton rbRead;
    private RadioButton rbReading;
    private RadioGroup radioGroup;

    private LinearLayout mLinearLayout;

    private static final String imageSize = "/landscape_incredible";

    private ComicViewModel mComicViewModel;
    private ComicEntity comicDb;
    private Integer idComic;

    private MaterialFavoriteButton favButton;
    private RatingBar ratingBar;

    private boolean favComic = false;
    private boolean readComic = false;
    private boolean readingComic = false;
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable("comic", comic);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.character_information_fragment, container, false);

        mComicViewModel = ViewModelProviders.of(this).get(ComicViewModel.class);

        tvComicName = (TextView) view.findViewById(R.id.tvCharacterName);
        tvComicDescription = (TextView) view.findViewById(R.id.tvCharacterDescription);
        ivComicImage = (ImageView) view.findViewById(R.id.ivCharacterImage);
        webButton = (ImageButton) view.findViewById(R.id.bShowWeb);
        favButton = (MaterialFavoriteButton) view.findViewById(R.id.characterFavButton);
        ratingBar = (RatingBar) view.findViewById(R.id.characterRatingBar);
        rbRead = (RadioButton) view.findViewById(R.id.readComic);
        rbReading = (RadioButton) view.findViewById(R.id.readingComic);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioButtonGroup);

        mLinearLayout = (LinearLayout) view.findViewById(R.id.comicRadioButton);
        mLinearLayout.setVisibility(View.VISIBLE);

        if (savedInstanceState != null) {
            comic = (ComicDetails) savedInstanceState.getSerializable("comic");
        }

        idComic = comic.getData().getResults().get(0).getId();

        comicDb = mComicViewModel.getComic(idComic);

        loadComicData();

        favButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                favComic = favorite;
            }
        });


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingComic = rating;
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.readComic:
                        readComic = true;
                        readingComic = false;
                        break;
                    case R.id.readingComic:
                        readingComic = true;
                        readComic = false;
                        break;
                }
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

            if (comicDb != null) {

                favComic = comicDb.isFavorite();
                ratingComic = comicDb.getRating();
                readComic = comicDb.isRead();
                readingComic = comicDb.isReading();

                if (favComic == false) favButton.setFavorite(false);
                else favButton.setFavorite(true);

                if (ratingComic != 0) {
                    ratingBar.setRating(ratingComic);
                }

                if(readComic) rbRead.setChecked(true);
                else rbRead.setChecked(false);

                if(readingComic) rbReading.setChecked(true);
                else rbReading.setChecked(false);

            }

        }
    }


    @Override
    public void onStop() {

        Result comicDetails = comic.getData().getResults().get(0);

        //Si el usuario ya no está interesado, borramos el registro (en caso de que exista)
        if (favComic == false && readingComic ==false && readComic == false && ratingComic == 0) {

            if (comicDb != null) mComicViewModel.deleteComic(comicDetails.getId());

        } else {

            ComicEntity comicInsert = new ComicEntity(idComic, comicDetails.getTitle(),
                    comicDetails.getThumbnail().getPath(), comicDetails.getThumbnail().getExtension(),
                    favComic, readComic, readingComic, ratingComic);

            if (comicDb == null) {
                mComicViewModel.insertComic(comicInsert);
            } else {
                mComicViewModel.updateComic(comicInsert);
            }

        }

        super.onStop();
    }

}

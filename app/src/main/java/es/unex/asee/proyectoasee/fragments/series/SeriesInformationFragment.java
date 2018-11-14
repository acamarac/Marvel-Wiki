package es.unex.asee.proyectoasee.fragments.series;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import es.unex.asee.proyectoasee.database.Entities.SeriesEntity;
import es.unex.asee.proyectoasee.database.ViewModel.SeriesViewModel;
import es.unex.asee.proyectoasee.pojo.marvel.seriesDetails.Result;
import es.unex.asee.proyectoasee.pojo.marvel.seriesDetails.SeriesDetails;
import es.unex.asee.proyectoasee.preferences.SettingsFragment;

public class SeriesInformationFragment extends Fragment {

    private View view;
    private SeriesDetails series;

    private static final String TAG = "SeriesIFragment";

    private TextView tvComicName;
    private TextView tvComicDescription;
    private ImageView ivComicImage;
    private ImageButton webButton;
    private RadioButton rbSeen;
    private RadioButton rbFollowing;
    private RadioButton rbPending;
    private RadioGroup radioGroup;

    private LinearLayout mLinearLayout;

    private static final String imageSize = "/landscape_incredible";

    private SeriesViewModel mSeriesViewModel;
    private SeriesEntity seriesDb;
    private Integer idSeries;

    private MaterialFavoriteButton favButton;
    private RatingBar ratingBar;

    private boolean favSeries = false;
    private boolean seenSeries = false;
    private boolean followingSeries = false;
    private boolean pendingSeries = false;
    private float ratingComic = 0;

    private SharedPreferences prefs;
    private boolean loadImages;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // This is a public method that the Activity can use to communicate
    // directly with this Fragment
    public void receiveSeries(SeriesDetails series) {
        this.series = series;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable("series", series);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.character_information_fragment, container, false);

        mSeriesViewModel = ViewModelProviders.of(this).get(SeriesViewModel.class);

        tvComicName = (TextView) view.findViewById(R.id.tvCharacterName);
        tvComicDescription = (TextView) view.findViewById(R.id.tvCharacterDescription);
        ivComicImage = (ImageView) view.findViewById(R.id.ivCharacterImage);
        webButton = (ImageButton) view.findViewById(R.id.bShowWeb);
        favButton = (MaterialFavoriteButton) view.findViewById(R.id.characterFavButton);
        ratingBar = (RatingBar) view.findViewById(R.id.characterRatingBar);
        rbSeen = (RadioButton) view.findViewById(R.id.seenSeries);
        rbFollowing = (RadioButton) view.findViewById(R.id.followingSeries);
        rbPending = (RadioButton) view.findViewById(R.id.pendingSeries);
        radioGroup = (RadioGroup) view.findViewById(R.id.seriesRadioButtonGroup);

        prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        loadImages = prefs.getBoolean(SettingsFragment.KEY_PREF_LOAD_IMAGES, true);

        mLinearLayout = (LinearLayout) view.findViewById(R.id.seriesRadioButton);
        mLinearLayout.setVisibility(View.VISIBLE);

        if (savedInstanceState != null) {
            series = (SeriesDetails) savedInstanceState.getSerializable("series");
        }

        idSeries = series.getData().getResults().get(0).getId();

        seriesDb = mSeriesViewModel.getSeries(idSeries);

        loadSeriesData();

        favButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                favSeries = favorite;
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
                    case R.id.seenSeries:
                        seenSeries = true;
                        pendingSeries = false;
                        followingSeries = false;
                        break;
                    case R.id.followingSeries:
                        followingSeries = true;
                        seenSeries = false;
                        pendingSeries = false;
                        break;
                    case R.id.pendingSeries:
                        pendingSeries = true;
                        followingSeries = false;
                        seenSeries = false;
                        break;
                }
            }
        });

        return view;
    }

    private void loadSeriesData() {


        if (series.getStatus().equals("Ok")) {

            final List<Result> comicData = series.getData().getResults();

            final Result comicDetail = comicData.get(0);


            tvComicName.setText(comicDetail.getTitle());

            TextView chDesc = (TextView) view.findViewById(R.id.tvChIntroduction);
            if (comicDetail.getDescription() == null)
                chDesc.setVisibility(View.INVISIBLE);
            else
                tvComicDescription.setText(comicDetail.getDescription());

            if(loadImages) {
                String imagePath = comicDetail.getThumbnail().getPath();
                String imageExtension = comicDetail.getThumbnail().getExtension();
                String finalImagePath = imagePath + imageSize + "." + imageExtension;
                Picasso.with(this.getContext())
                        .load(finalImagePath)
                        .fit()
                        .into(ivComicImage);
            } else {
                Picasso.with(this.getContext())
                        .load(R.drawable.placeholder_landscape)
                        .fit()
                        .into(ivComicImage);
            }




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

            if (seriesDb != null) {

                favSeries = seriesDb.isFavorite();
                ratingComic = seriesDb.getRating();
                seenSeries = seriesDb.isSeen();
                pendingSeries = seriesDb.isPending();
                followingSeries = seriesDb.isFollowing();

                if (favSeries == false) favButton.setFavorite(false);
                else favButton.setFavorite(true);

                if (ratingComic != 0) {
                    ratingBar.setRating(ratingComic);
                }

                if (seenSeries) { rbSeen.setChecked(true); rbPending.setChecked(false); rbFollowing.setChecked(false); }

                if (pendingSeries) { rbPending.setChecked(true); rbFollowing.setChecked(false); rbSeen.setChecked(false); }

                if (followingSeries) { rbFollowing.setChecked(true); rbSeen.setChecked(false); rbPending.setChecked(false); }

            }

        }
    }


    @Override
    public void onStop() {

        Result seriesDetails = series.getData().getResults().get(0);

        //Si el usuario ya no está interesado, borramos el registro (en caso de que exista)
        if (!favSeries && !seenSeries && !pendingSeries && !followingSeries && ratingComic == 0) {

            if (seriesDb != null) mSeriesViewModel.deleteSeries(seriesDetails.getId());

        } else {

            SeriesEntity seriesInsert = new SeriesEntity(idSeries, seriesDetails.getTitle(),
                    seriesDetails.getThumbnail().getPath(), seriesDetails.getThumbnail().getExtension(),
                    ratingComic, favSeries, seenSeries, pendingSeries, followingSeries);

            if (seriesDb == null) {
                mSeriesViewModel.insertSeries(seriesInsert);
            } else {
                mSeriesViewModel.updateSeries(seriesInsert);
            }

        }

        super.onStop();
    }

}

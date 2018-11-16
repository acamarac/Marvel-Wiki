package es.unex.asee.proyectoasee.adapters.series;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.proyectoasee.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import es.unex.asee.proyectoasee.fragments.series.SeriesDetailMainFragment;
import es.unex.asee.proyectoasee.pojo.marvel.series.Result;
import es.unex.asee.proyectoasee.fragments.preferences.SettingsFragment;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder> {

    private List<Result> seriesList;
    private Context context;

    private SeriesAdapterListener mCallback;

    private static final String imageSize = "/standard_large";

    private SharedPreferences prefs;
    private boolean loadImages;

    public SeriesAdapter(Context context, SeriesAdapterListener mCallback) {
        seriesList = new ArrayList<>();
        this.context = context;
        this.mCallback = mCallback;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        loadImages = prefs.getBoolean(SettingsFragment.KEY_PREF_LOAD_IMAGES, true);
    }

    @NonNull
    @Override
    public SeriesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.r_view_character_layout, viewGroup, false);
        SeriesViewHolder comicsViewHolder = new SeriesViewHolder(view);

        return comicsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesViewHolder seriesViewHolder, int i) {

        final Result comic = seriesList.get(i);

        seriesViewHolder.mTextViewCharacter.setText(comic.getTitle());

        if(loadImages) {
            String imagePath = comic.getThumbnail().getPath();
            String imageExtension = comic.getThumbnail().getExtension();
            String finalImagePath = imagePath + imageSize + "." + imageExtension;
            Picasso.with(context).load(finalImagePath).into(seriesViewHolder.mImageViewCharacter);
        } else {
            Picasso.with(context).load(R.drawable.placeholder).into(seriesViewHolder.mImageViewCharacter);
        }

        seriesViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppCompatActivity mainActivity = (AppCompatActivity) v.getContext();

                SeriesDetailMainFragment seriesFragment= new SeriesDetailMainFragment();
                mCallback.sendSeriesId(comic.getId(), seriesFragment);
                mainActivity.getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment, seriesFragment)
                        .addToBackStack(null)
                        .commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return seriesList.size();
    }

    public void addSeriesPagination(List<Result> series) {

        seriesList.addAll(series);
        notifyDataSetChanged();

    }

    public void clearList() {
        seriesList.clear();
    }

    public static class SeriesViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewCharacter;
        private ImageView mImageViewCharacter;
        private CardView mCardView;

        SeriesViewHolder (View v) {
            super(v);

            mTextViewCharacter =  (TextView)v.findViewById(R.id.tvCharacter);
            mImageViewCharacter = (ImageView)v.findViewById(R.id.ivCharacter);
            mCardView = (CardView) v.findViewById(R.id.rvCardView);
        }

    }

    //Method to communicate fragments
    public interface SeriesAdapterListener {
        void sendSeriesId(Integer id, SeriesDetailMainFragment fragment);
    }

}

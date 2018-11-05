package es.unex.asee.proyectoasee.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.proyectoasee.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import es.unex.asee.proyectoasee.fragments.characters.CharacterDetailMainFragment;
import es.unex.asee.proyectoasee.fragments.comics.ComicDetailMainFragment;
import es.unex.asee.proyectoasee.pojo.marvel.comics.Result;

public class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapter.ComicsViewHolder> {

    private List<Result> comicsList;
    private Context context;

    private static final String imageSize = "/standard_large";

    public ComicsAdapter(List<Result> comicsList, Context context) {
        this.comicsList = comicsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ComicsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.r_view_character_layout, viewGroup, false);
        ComicsViewHolder comicsViewHolder = new ComicsViewHolder(view);

        return comicsViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ComicsViewHolder comicsViewHolder, int i) {

        final Result comic = comicsList.get(i);
        String imagePath = comic.getThumbnail().getPath();
        String imageExtension = comic.getThumbnail().getExtension();
        String finalImagePath = imagePath + imageSize + "." + imageExtension;

        comicsViewHolder.mTextViewCharacter.setText(comic.getTitle());
        Picasso.with(context).load(finalImagePath).into(comicsViewHolder.mImageViewCharacter);

        comicsViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppCompatActivity mainActivity = (AppCompatActivity) v.getContext();

                ComicDetailMainFragment comicFragment= new ComicDetailMainFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", comic.getId());
                comicFragment.setArguments(bundle);

                mainActivity.getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment, comicFragment)
                        .addToBackStack(null)
                        .commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return comicsList.size();
    }

    public void addComicsPagination(List<Result> comics) {

        comicsList.addAll(comics);
        notifyDataSetChanged();

    }


    public static class ComicsViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewCharacter;
        private ImageView mImageViewCharacter;
        private CardView mCardView;

        ComicsViewHolder (View v) {
            super(v);

            mTextViewCharacter =  (TextView)v.findViewById(R.id.tvCharacter);
            mImageViewCharacter = (ImageView)v.findViewById(R.id.ivCharacter);
            mCardView = (CardView) v.findViewById(R.id.rvCardView);
        }

    }

}

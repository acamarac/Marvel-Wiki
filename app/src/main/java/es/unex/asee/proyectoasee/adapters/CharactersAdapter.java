package es.unex.asee.proyectoasee.adapters;

import android.content.Context;
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

import es.unex.asee.proyectoasee.pojo.marvel.characters.Result;


public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder> {

    private List<Result> charactersList;
    private Context context;

    private static final String imageSize = "/standard_fantastic";

    public CharactersAdapter(List<Result> charactersList, Context context) {
        this.charactersList = charactersList;
        this.context = context;
    }

    @Override
    public CharactersViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.r_view_character_layout, viewGroup, false);
        CharactersViewHolder charactersViewHolder = new CharactersViewHolder(view);

        return  charactersViewHolder;
    }

    @Override
    public void onBindViewHolder(CharactersViewHolder charactersViewHolder, int i) {

        Result character = charactersList.get(i);
        String imagePath = character.getThumbnail().getPath();
        String imageExtension = character.getThumbnail().getExtension();
        String finalImagePath = imagePath + imageSize + "." + imageExtension;

        charactersViewHolder.mTextViewCharacter.setText(character.getName());
        Picasso.with(context).load(finalImagePath).into(charactersViewHolder.mImageViewCharacter);

    }

    @Override
    public int getItemCount() {
        return charactersList.size();
    }

    public static class CharactersViewHolder extends RecyclerView.ViewHolder {

        private CardView mCardViewCharacter;
        private TextView mTextViewCharacter;
        private ImageView mImageViewCharacter;

        CharactersViewHolder (View v) {
            super(v);

            mCardViewCharacter = (CardView)v.findViewById(R.id.cvCharacter);
            mTextViewCharacter =  (TextView)v.findViewById(R.id.tvCharacter);
            mImageViewCharacter = (ImageView)v.findViewById(R.id.ivCharacter);
        }

    }

}

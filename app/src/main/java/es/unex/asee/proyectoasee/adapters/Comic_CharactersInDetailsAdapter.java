package es.unex.asee.proyectoasee.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.proyectoasee.R;

import java.util.List;

import es.unex.asee.proyectoasee.pojo.marvel.comicDetails.Item_;


public class Comic_CharactersInDetailsAdapter extends RecyclerView.Adapter<Comic_CharactersInDetailsAdapter.CharactersViewHolder> {

    private List<Item_> charactersList;
    private Context context;

    public Comic_CharactersInDetailsAdapter(List<Item_> charactersList, Context context) {
        this.charactersList = charactersList;
        this.context = context;
    }

    @NonNull
    @Override
    public Comic_CharactersInDetailsAdapter.CharactersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_comics_detail_list, viewGroup, false);
        Comic_CharactersInDetailsAdapter.CharactersViewHolder charactersViewHolder = new Comic_CharactersInDetailsAdapter.CharactersViewHolder(v);

        return charactersViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Comic_CharactersInDetailsAdapter.CharactersViewHolder charactersViewHolder, int i) {

        Item_ series = charactersList.get(i);

        charactersViewHolder.mTvCharactersTitle.setText(series.getName());

    }

    @Override
    public int getItemCount() {
        return charactersList.size();
    }


    public static class CharactersViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvCharactersTitle;

        public CharactersViewHolder(@NonNull View itemView) {
            super(itemView);

            mTvCharactersTitle = (TextView) itemView.findViewById(R.id.tvComicName);
        }
    }

}

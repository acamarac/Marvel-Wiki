package es.unex.asee.proyectoasee.adapters.series;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.proyectoasee.R;

import java.util.List;

import es.unex.asee.proyectoasee.fragments.characters.CharacterDetailMainFragment;
import es.unex.asee.proyectoasee.pojo.marvel.seriesDetails.Item_;
import es.unex.asee.proyectoasee.utils.Utils;

public class Series_CharactersInDetailsAdapter extends RecyclerView.Adapter<Series_CharactersInDetailsAdapter.CharactersViewHolder> {

    private List<Item_> charactersList;
    private Context context;
    private Series_CharactersInDetailsAdapterListener mCallback;

    public Series_CharactersInDetailsAdapter(List<Item_> charactersList, Context context, Series_CharactersInDetailsAdapterListener mCallback) {
        this.charactersList = charactersList;
        this.context = context;
        this.mCallback = mCallback;
    }

    @NonNull
    @Override
    public Series_CharactersInDetailsAdapter.CharactersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_comics_detail_list, viewGroup, false);
        Series_CharactersInDetailsAdapter.CharactersViewHolder charactersViewHolder = new Series_CharactersInDetailsAdapter.CharactersViewHolder(v);

        return charactersViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Series_CharactersInDetailsAdapter.CharactersViewHolder charactersViewHolder, int i) {

        Item_ series = charactersList.get(i);

        charactersViewHolder.mTvCharactersTitle.setText(series.getName());

        final Integer id = Utils.getResourceId(series.getResourceURI());

        charactersViewHolder.mCdCharactersLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppCompatActivity mainActivity = (AppCompatActivity) v.getContext();

                CharacterDetailMainFragment charactersFragment= new CharacterDetailMainFragment();
                mCallback.sendId(id, charactersFragment);
                mainActivity.getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment, charactersFragment)
                        .addToBackStack(null)
                        .commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return charactersList.size();
    }


    public static class CharactersViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvCharactersTitle;
        private CardView mCdCharactersLink;

        public CharactersViewHolder(@NonNull View itemView) {
            super(itemView);

            mTvCharactersTitle = (TextView) itemView.findViewById(R.id.tvComicName);
            mCdCharactersLink = (CardView) itemView.findViewById(R.id.rvCardView);
        }
    }


    //Method to communicate fragments
    public interface Series_CharactersInDetailsAdapterListener {
        void sendId(Integer id, CharacterDetailMainFragment fragment);
    }


}

package es.unex.asee.proyectoasee.adapters.series;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.proyectoasee.R;

import java.util.List;

import es.unex.asee.proyectoasee.fragments.comics.ComicDetailMainFragment;
import es.unex.asee.proyectoasee.pojo.marvel.seriesDetails.Item___;
import es.unex.asee.proyectoasee.utils.Utils;

public class Series_ComicsInDetailsAdapter extends RecyclerView.Adapter<Series_ComicsInDetailsAdapter.ComicsViewHolder> {

    private List<Item___> comicsList;
    private Context context;
    private Series_ComicsInDetailsAdapterListener mCallback;

    public Series_ComicsInDetailsAdapter(List<Item___> comicsList, Context context, Series_ComicsInDetailsAdapterListener mCallback) {
        this.comicsList = comicsList;
        this.context = context;
        this.mCallback = mCallback;
    }

    @NonNull
    @Override
    public Series_ComicsInDetailsAdapter.ComicsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_comics_detail_list, viewGroup, false);
        Series_ComicsInDetailsAdapter.ComicsViewHolder comicsViewHolder = new Series_ComicsInDetailsAdapter.ComicsViewHolder(v);

        return comicsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Series_ComicsInDetailsAdapter.ComicsViewHolder comicsViewHolder, int i) {

        Item___ comic = comicsList.get(i);

        comicsViewHolder.mTvComicTitle.setText(comic.getName());

        final Integer id = Utils.getResourceId(comic.getResourceURI());

        comicsViewHolder.mCdComicLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppCompatActivity mainActivity = (AppCompatActivity) v.getContext();

                ComicDetailMainFragment comicsFragment= new ComicDetailMainFragment();
                mCallback.sendId(id, comicsFragment);
                mainActivity.getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment, comicsFragment)
                        .addToBackStack(null)
                        .commit();

            }
        });


    }

    @Override
    public int getItemCount() {
        return comicsList.size();
    }


    public static class ComicsViewHolder extends  RecyclerView.ViewHolder {

        private TextView mTvComicTitle;
        private CardView mCdComicLink;

        public ComicsViewHolder(@NonNull View itemView) {
            super(itemView);

            mTvComicTitle = (TextView) itemView.findViewById(R.id.tvComicName);
            mCdComicLink = (CardView) itemView.findViewById(R.id.rvCardView);
        }
    }


    //Method to communicate fragments
    public interface Series_ComicsInDetailsAdapterListener {
        void sendId(Integer id, ComicDetailMainFragment fragment);
    }

}

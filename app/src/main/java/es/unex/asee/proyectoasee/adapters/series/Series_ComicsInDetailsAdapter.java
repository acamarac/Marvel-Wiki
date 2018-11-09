package es.unex.asee.proyectoasee.adapters.series;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.proyectoasee.R;

import java.util.List;

import es.unex.asee.proyectoasee.pojo.marvel.seriesDetails.Item___;

public class Series_ComicsInDetailsAdapter extends RecyclerView.Adapter<Series_ComicsInDetailsAdapter.ComicsViewHolder> {

    private List<Item___> comicsList;
    private Context context;

    public Series_ComicsInDetailsAdapter(List<Item___> comicsList, Context context) {
        this.comicsList = comicsList;
        this.context = context;
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

    }

    @Override
    public int getItemCount() {
        return comicsList.size();
    }


    public static class ComicsViewHolder extends  RecyclerView.ViewHolder {

        private TextView mTvComicTitle;

        public ComicsViewHolder(@NonNull View itemView) {
            super(itemView);

            mTvComicTitle = (TextView) itemView.findViewById(R.id.tvComicName);
        }
    }

}

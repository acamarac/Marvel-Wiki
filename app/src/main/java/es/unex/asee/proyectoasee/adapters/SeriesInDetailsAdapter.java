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

import es.unex.asee.proyectoasee.pojo.marvel.characterDetails.Item_;

public class SeriesInDetailsAdapter extends RecyclerView.Adapter<SeriesInDetailsAdapter.SeriesViewHolder> {

    private List<Item_> seriesList;
    private Context context;

    public SeriesInDetailsAdapter(List<Item_> seriesList, Context context) {
        this.seriesList = seriesList;
        this.context = context;
    }

    @NonNull
    @Override
    public SeriesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_comics_detail_list, viewGroup, false);
        SeriesViewHolder seriesViewHolder = new SeriesViewHolder(v);

        return seriesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesViewHolder seriesViewHolder, int i) {

        Item_ series = seriesList.get(i);

        seriesViewHolder.mTvSeriesTitle.setText(series.getName());

    }

    @Override
    public int getItemCount() {
        return seriesList.size();
    }


    public static class SeriesViewHolder extends  RecyclerView.ViewHolder {

        private TextView mTvSeriesTitle;

        public SeriesViewHolder(@NonNull View itemView) {
            super(itemView);

            mTvSeriesTitle = (TextView) itemView.findViewById(R.id.tvComicName);
        }
    }


}

package es.unex.asee.proyectoasee.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.proyectoasee.R;

import org.w3c.dom.Text;

import java.util.List;

import es.unex.asee.proyectoasee.pojo.marvel.characterDetails.Comics;
import es.unex.asee.proyectoasee.pojo.marvel.characterDetails.Item;

public class ComicsInDetailsAdapter extends RecyclerView.Adapter<ComicsInDetailsAdapter.ComicsViewHolder> {

    private List<Item> comicsList;
    private Context context;

    public ComicsInDetailsAdapter(List<Item> comicsList, Context context) {
        this.comicsList = comicsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ComicsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_comics_detail_list, viewGroup, false);
        ComicsViewHolder comicsViewHolder = new ComicsViewHolder(v);

        return comicsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ComicsInDetailsAdapter.ComicsViewHolder comicsViewHolder, int i) {

        Item comic = comicsList.get(i);

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

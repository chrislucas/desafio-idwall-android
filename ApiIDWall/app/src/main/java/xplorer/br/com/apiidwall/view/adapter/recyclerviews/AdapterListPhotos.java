package xplorer.br.com.apiidwall.view.adapter.recyclerviews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import xplorer.br.com.apiidwall.model.DogFeed;

import xplorer.br.com.apiidwall.view.adapter.recyclerviews.callback.AdapterOnItemClickListener;
import xplorer.br.com.apiidwall.view.viewholder.ViewHolderPhoto;

public class AdapterListPhotos extends RecyclerView.Adapter<ViewHolderPhoto> {

    private DogFeed feed;
    private AdapterOnItemClickListener<String> adapter;

    public AdapterListPhotos(DogFeed feed, AdapterOnItemClickListener<String> adapter) {
        this.feed = feed;
        this.adapter = adapter;
    }

    @NonNull
    @Override
    public ViewHolderPhoto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPhoto holder, int position) {
        String urlPhoto = feed.getPhotos().get(position);
        /**
         * Carregar a foto usando a URL
         * */

        adapter.onItemClick(urlPhoto);
    }

    @Override
    public int getItemCount() {
        return feed.getPhotos().size();
    }
}

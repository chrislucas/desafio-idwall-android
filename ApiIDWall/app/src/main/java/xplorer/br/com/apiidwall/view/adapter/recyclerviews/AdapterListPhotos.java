package xplorer.br.com.apiidwall.view.adapter.recyclerviews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import xplorer.br.com.apiidwall.R;
import xplorer.br.com.apiidwall.model.DogFeed;

import xplorer.br.com.apiidwall.view.adapter.recyclerviews.callback.AdapterOnItemClickListener;
import xplorer.br.com.apiidwall.view.utils.LoadImage;
import xplorer.br.com.apiidwall.view.viewholder.ViewHolderPhoto;

public class AdapterListPhotos extends RecyclerView.Adapter<ViewHolderPhoto> {

    private DogFeed feed;
    private AdapterOnItemClickListener<String> adapter;

    private LoadImage loadImage;

    public AdapterListPhotos(DogFeed feed, AdapterOnItemClickListener<String> adapter) {
        this.feed = feed;
        this.adapter = adapter;
    }

    @NonNull
    @Override
    public ViewHolderPhoto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_dog_photo, parent, false);
        loadImage = new LoadImage(Picasso.with(parent.getContext()));
        return new ViewHolderPhoto(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderPhoto holder, int position) {
        final String urlPhoto = feed.getPhotos().get(position);
        /**
         * Carregar a foto usando a URL
         * */

        Callback callback = new Callback() {
            @Override
            public void onSuccess() {}

            @Override
            public void onError() {
                loadImage.getPicasso()
                        .load(urlPhoto)
                        .fit()
                        .into(holder.getPhoto());
            }
        };
        loadImage.loadImageFromUrl(urlPhoto
                , R.drawable.doggo_optm
                , R.drawable.image_not_found
                , holder.getPhoto()
                , callback
        );

        try {
            holder.bindOnClick(adapter, urlPhoto);
        } catch (Exception e) {
            String message = e.getMessage();
            Log.e("EXCP_BIND_CLICK_IMAGE"
                    , message == null ? "Não foi possivel recuperar o erro" : message);
        }
    }

    @Override
    public int getItemCount() {
        return feed.getPhotos().size();
    }
}

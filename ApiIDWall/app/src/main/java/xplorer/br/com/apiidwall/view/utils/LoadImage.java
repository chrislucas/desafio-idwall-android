package xplorer.br.com.apiidwall.view.utils;

import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class LoadImage {

    private final Picasso picasso;

    public LoadImage(Picasso picasso) {
        this.picasso = picasso;
    }

    public void loadImageFromUrl(String url
            , @DrawableRes int placeholder
            , @DrawableRes int errorImage
            , ImageView imageView, Callback callback) {
        picasso.load(url)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .fit()
                .placeholder(placeholder)
                .error(errorImage)
                .into(imageView, callback);
    }

    public Picasso getPicasso() {
        return picasso;
    }
}

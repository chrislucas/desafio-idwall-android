package xplorer.br.com.apiidwall.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import xplorer.br.com.apiidwall.view.adapter.recyclerviews.callback.AdapterOnItemClickListener;

public class ViewHolderPhoto extends AbstractViewHolder{

    private ImageView photo;

    public ViewHolderPhoto(View itemView) {
        super(itemView);
    }


    public ImageView getPhoto() {
        return photo;
    }

    @Override
    public void bindOnClick(AdapterOnItemClickListener adapterOnItemClickListener) throws Exception {
;
    }
}

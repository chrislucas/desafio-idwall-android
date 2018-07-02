package xplorer.br.com.apiidwall.view.viewholder;


import android.view.View;
import android.widget.ImageView;

import xplorer.br.com.apiidwall.R;
import xplorer.br.com.apiidwall.view.adapter.recyclerviews.callback.AdapterOnItemClickListener;

public class ViewHolderPhoto extends AbstractViewHolder<String> {

    private ImageView photo;
    private View viewRoot;

    public ViewHolderPhoto(View itemView) {
        super(itemView);
        photo = itemView.findViewById(R.id.image_dog);
        viewRoot = itemView;
    }

    public ImageView getPhoto() {
        return photo;
    }

    @Override
    public void bindOnClick(final AdapterOnItemClickListener<String> adapterOnItemClickListener
            , final String data) throws Exception {
        viewRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterOnItemClickListener.onItemClick(data);
            }
        });
    }
}

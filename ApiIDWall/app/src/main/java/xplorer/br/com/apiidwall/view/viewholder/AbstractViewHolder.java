package xplorer.br.com.apiidwall.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import xplorer.br.com.apiidwall.view.adapter.recyclerviews.callback.AdapterOnItemClickListener;

public abstract class AbstractViewHolder<T> extends RecyclerView.ViewHolder {

    public AbstractViewHolder(View itemView) {
        super(itemView);
    }


    public void bindOnClick(AdapterOnItemClickListener<T> adapterOnItemClickListener) throws Exception {
        throw new Exception("Chamar o metodo na classe concreta");
    }
}

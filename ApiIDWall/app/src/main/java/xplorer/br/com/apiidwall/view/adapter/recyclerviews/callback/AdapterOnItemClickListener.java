package xplorer.br.com.apiidwall.view.adapter.recyclerviews.callback;

import android.support.v7.widget.RecyclerView;

public interface AdapterOnItemClickListener<T> {
    void onItemClick(T entity);
    void onItemClick(RecyclerView.ViewHolder viewHolder);
}

package xplorer.br.com.apiidwall.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Iterator;

import xplorer.br.com.apiidwall.R;

public abstract class BaseFragment extends Fragment {

    public String tagFragment;

    public String getTagFragment() {
        return tagFragment;
    }

    public abstract void setTagFragment();


    public BaseFragment() {
        // Required empty public constructor
        setTagFragment();
    }

    /**
     * Um metodo para facilitar a limpeza de uma lista que popula um recyclerview
     * */
    public <T> void removeAllElements(Iterator<T> iterator) {
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

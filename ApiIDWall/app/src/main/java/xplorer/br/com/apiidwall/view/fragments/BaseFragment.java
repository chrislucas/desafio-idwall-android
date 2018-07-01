package xplorer.br.com.apiidwall.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

package xplorer.br.com.apiidwall.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import xplorer.br.com.apiidwall.R;
import xplorer.br.com.apiidwall.model.DogFeed;
import xplorer.br.com.apiidwall.view.adapter.recyclerviews.AdapterListPhotos;


public class FragmentListDogs extends BaseFragment {


    private DogFeed dogFeed;
    private Spinner optionsDogCategory;

    private AdapterListPhotos adapterListPhotos;


    public static final String BUNDLE_DOG_FEED = "BUNDLE_DOG_FEED";

    @Override
    public void setTagFragment() {
        tagFragment = getClass().getSimpleName();
    }

    public FragmentListDogs() {
        // Required empty public constructor
    }

    public static FragmentListDogs newInstance() {
        FragmentListDogs fragment = new FragmentListDogs();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * Manter o estado dos objetos que compõe este fragment afim
         * de evitar perdar quando o mesmo for destruido e reconstruido, por
         * exemplo quando ocorrer uma rotação do dispositivo
         * */
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_dogs, container, false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (dogFeed == null) {

        }
        else {

        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_DOG_FEED, dogFeed);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
}

package xplorer.br.com.apiidwall.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import xplorer.br.com.apiidwall.API;
import xplorer.br.com.apiidwall.R;
import xplorer.br.com.apiidwall.model.DogFeed;
import xplorer.br.com.apiidwall.model.User;
import xplorer.br.com.apiidwall.presenter.callbacks.CallbackRequest;
import xplorer.br.com.apiidwall.presenter.request.APIListPhotos;
import xplorer.br.com.apiidwall.presenter.request.Endpoint.DogCategory;
import xplorer.br.com.apiidwall.presenter.response.ResponseMessage;
import xplorer.br.com.apiidwall.view.adapter.recyclerviews.AdapterListPhotos;
import xplorer.br.com.apiidwall.view.adapter.recyclerviews.callback.AdapterOnItemClickListener;
import xplorer.br.com.apiidwall.view.adapter.spinners.AdapterOptionsDogCategory;


public class FragmentListDogs extends BaseFragment implements CallbackRequest<DogFeed>
        , AdapterOnItemClickListener<String> {


    private DogFeed dogFeed;
    private Spinner optionsDogCategory;

    private AdapterOptionsDogCategory adapterOptionsDogCategory;
    private AdapterListPhotos adapterListPhotos;

    private User userLogged;

    private APIListPhotos apiListPhotos;

    private static final String BUNDLE_DOG_FEED = "BUNDLE_DOG_FEED";
    private static final String BUNDLE_USER_LOGGED = "BUNDLE_USER_LOGGED";

    @Override
    public void setTagFragment() {
        tagFragment = getClass().getSimpleName();
    }

    public FragmentListDogs() {
        // Required empty public constructor
    }

    public static FragmentListDogs newInstance(User userLogged) {
        FragmentListDogs fragment = new FragmentListDogs();
        fragment.userLogged = userLogged;
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
        if (savedInstanceState == null)
            dogFeed = new DogFeed();
        else
            dogFeed = savedInstanceState.getParcelable(BUNDLE_DOG_FEED);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_dogs, container, false);
        apiListPhotos = new APIListPhotos(API.BASE_URL);
        optionsDogCategory = view.findViewById(R.id.category_dogs);

        final List<String> options = new ArrayList<>();
        options.add(DogCategory.UNDEFINED);
        options.add(DogCategory.HOUND);
        options.add(DogCategory.HUSKY);
        options.add(DogCategory.LABRADOR);
        options.add(DogCategory.PUG);

        adapterOptionsDogCategory = new AdapterOptionsDogCategory(getContext(), R.layout.custom_layout_spinner_text, options);
        optionsDogCategory.setAdapter(adapterOptionsDogCategory);
        optionsDogCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String category = options.get(i);
                if (category.equals(DogCategory.UNDEFINED)) {
                    /**
                     * Mostrar uma mensagem de rrro
                     * */
                }
                else {
                    searchFeed(category);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        RecyclerView recyclerView = view.findViewById(R.id.list_dogs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapterListPhotos = new AdapterListPhotos(dogFeed, this);
        recyclerView.setAdapter(adapterListPhotos);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (dogFeed == null) {
            // pesquisar pela categoria padrao
            searchFeed(DogCategory.HUSKY);
        }
        else {
            /**
             * */
        }
    }

    private void searchFeed(@DogCategory  String category) {
        apiListPhotos.asyncRequestDogFeed(this, category, userLogged.getToken());
    }

    /**
     *
     * {@link CallbackRequest}
     * */

    @Override
    public void onSuccess(DogFeed data) {
        removeAllElements(dogFeed.getPhotos().iterator());
        /**
         *
         * */
        this.dogFeed = data;
        /*
        this.dogFeed = new DogFeed();
        this.dogFeed.setCategegory(data.getCategegory());
        this.dogFeed.getPhotos().addAll(data.getPhotos());
        */
        adapterListPhotos.notifyDataSetChanged();

    }
    /**
     *
     * {@link CallbackRequest}
     * */
    @Override
    public void onSuccess(List<DogFeed> data) {}
    /**
     *
     * {@link CallbackRequest}
     * */
    @Override
    public void onFailure(ResponseMessage responseMessage) {
        /***
         * Mostrar uma mensagem de erro caso nao consiga baixar o feed
         * */
    }


    /**
     * {@link AdapterOnItemClickListener}
     * */
    @Override
    public void onItemClick(String url) {
        /**
         * Carregar uma DialogFrament com a imagem
         * */
    }

    /**
     * {@link AdapterOnItemClickListener}
     * */
    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder) {}


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_USER_LOGGED, userLogged);
        outState.putParcelable(BUNDLE_DOG_FEED, dogFeed);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            userLogged = savedInstanceState.getParcelable(BUNDLE_USER_LOGGED);
            dogFeed = savedInstanceState.getParcelable(BUNDLE_DOG_FEED);
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

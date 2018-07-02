package xplorer.br.com.apiidwall.view.fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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
import xplorer.br.com.apiidwall.utils.ViewProgressMessage;
import xplorer.br.com.apiidwall.view.adapter.recyclerviews.AdapterListPhotos;
import xplorer.br.com.apiidwall.view.adapter.recyclerviews.callback.AdapterOnItemClickListener;
import xplorer.br.com.apiidwall.view.adapter.spinners.AdapterOptionsDogCategory;
import xplorer.br.com.apiidwall.view.customs.CustomDialogFragment;
import xplorer.br.com.apiidwall.view.customs.DialogFragmentCallback;
import xplorer.br.com.apiidwall.view.customs.ZoomableImageView;
import xplorer.br.com.apiidwall.utils.LoadImage;
import xplorer.br.com.apiidwall.utils.ViewMessage;


public class FragmentListDogs extends BaseFragment implements CallbackRequest<DogFeed>
        , AdapterOnItemClickListener<String>, DialogFragmentCallback {

    private DogFeed dogFeed;
    private Spinner optionsDogCategory;
    private AdapterOptionsDogCategory adapterOptionsDogCategory;
    private AdapterListPhotos adapterListPhotos;

    private User userLogged;

    private APIListPhotos apiListPhotos;

    private ViewProgressMessage viewProgressMessage;

    private CustomDialogFragment customDialogFragment;

    private LoadImage loadImage;
    private ViewMessage viewMessage;

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
        if (savedInstanceState == null) {
            dogFeed = new DogFeed();
            dogFeed.setPhotos(new ArrayList<String>());
        }
        else {
            dogFeed = savedInstanceState.getParcelable(BUNDLE_DOG_FEED);
            userLogged = savedInstanceState.getParcelable(BUNDLE_USER_LOGGED);
        }
        viewProgressMessage = new ViewProgressMessage((AppCompatActivity) getActivity(), R.layout.layout_warning_progress);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_dogs, container, false);
        apiListPhotos = new APIListPhotos(API.BASE_URL);
        optionsDogCategory = view.findViewById(R.id.category_dogs);

        if (savedInstanceState != null) {
            dogFeed = savedInstanceState.getParcelable(BUNDLE_DOG_FEED);
            userLogged = savedInstanceState.getParcelable(BUNDLE_USER_LOGGED);
        }

        final List<String> options = new ArrayList<>();
        options.add(DogCategory.UNDEFINED);
        options.add(DogCategory.HOUND);
        options.add(DogCategory.HUSKY);
        options.add(DogCategory.LABRADOR);
        options.add(DogCategory.PUG);

        adapterOptionsDogCategory = new AdapterOptionsDogCategory(getContext()
                , R.layout.custom_layout_spinner_text, options);
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
                    safeShowProgressMessage("Pesquisando Feed"
                            , String.format("Aguarde alguns instantes %s, enquanto pesquisamos por '%s'", userLogged.getEmail(), category)
                            , false
                    );
                    searchFeed(category);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        optionsDogCategory.setSelection(0, true);
        optionsDogCategory.setAdapter(adapterOptionsDogCategory);

        RecyclerView recyclerView = view.findViewById(R.id.list_dogs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterListPhotos = new AdapterListPhotos(dogFeed, this);
        recyclerView.setAdapter(adapterListPhotos);

        customDialogFragment = CustomDialogFragment.newInstance(
                this
                , R.layout.layout_fullscreen_zoomable_imageview
                , R.style.FullScreenDialog
                , true
        );
        if (getActivity() != null) {
            viewMessage = new ViewMessage(getActivity().findViewById(R.id.layout_replace));
        }
        loadImage = new LoadImage(Picasso.with(getContext()));
        return view;
    }

    /**
     * {@link DialogFragmentCallback}
     *
     * Esse metodo me da acesso a view do DialogFragment
     * assim que ele eh completamente criado. Acessando a View
     * podemos acessar os elementos filhos dela atraves do
     * findViewById()
     * */
    @Override
    public void accessViewDialogFragment(View rootView) {
        // recuperando uma customizacao de ImageView dentro do layout
        // dialog fragment. Com a Lib Picasso vamos carregar uma imagem
        // dentro dessa ImageView
        final ZoomableImageView image = rootView.findViewById(R.id.image_on_dialog);
        loadImage.loadImageFromUrl(this.url
                , R.drawable.doggo_optm
                , R.drawable.background_custom_spinner
                , image, new Callback() {
            @Override
            public void onSuccess() {}

            @Override
            public void onError() {
                loadImage.getPicasso()
                        .load(FragmentListDogs.this.url)
                        .fit()
                        .into(image);
            }
        });
    }

    /**
     * {@link DialogFragmentCallback}
     * */
    @Override
    public void accessAlertDialogBuilder(AlertDialog.Builder builder) {}

    /**
     * {@link DialogFragmentCallback}
     *
     * Esse customDialogFragment eh construido com base num AlertDialog. No momento
     * da criacao do nosso DialogFragment é interessante ter acesso ao AlertDialog.
     * No codigo abaixo retiramos os botoes de 'Resposta Positiva e Negativa' pois
     * nao nos interessa, queremos um DialogFragment Fullscreen que mostre so uma imagem
     * no centro
     *
     * */
    @Override
    public void accessDialog(Dialog dialog) {
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button pos = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                pos.setEnabled(false);
                pos.setVisibility(View.GONE);
                Button neg = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
                neg.setEnabled(false);
                neg.setVisibility(View.GONE);
            }
        });
    }

    /**
     * {@link DialogFragmentCallback}
     * */
    @Override
    public void changeDimensionDialog(Dialog view) {}

    /**
     * {@link DialogFragmentCallback}
     *
     * No momento da criacao do DialogFragment na execucao
     * do metodo onCreateDialog() podemos modificar a dimensao
     * do Fragment da forma que precisarmos
     *
     * */
    @Override
    public void changeDimensionViewDialog() {
        View view = customDialogFragment.getViewDialogFragment();
        if (view != null) {
            DisplayMetrics dm  = getResources().getDisplayMetrics();
            double w = dm.widthPixels, h = dm.heightPixels;
            double percentWidth     = 1;
            double percentHeight    = 1;
            view.setMinimumWidth((int)(w*percentWidth));
            view.setMinimumHeight((int)(h*percentHeight));
        }
    }

    /**
     * {@link DialogFragmentCallback}
     * Criar um listener para executar algo quando o DialogFragment for
     * removido da tela
     * */
    @Override
    public DialogInterface.OnDismissListener actionOnDismissListener() {
        return new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {}
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        if (dogFeed.getPhotos().size() == 0) {
            // pesquisar pela categoria padrao
            searchFeed(DogCategory.HUSKY);
            safeShowProgressMessage("Pesquisando Feed"
                    , String.format("Aguarde alguns instantes %s, enquanto pesquisamos por %s", userLogged.getEmail(), DogCategory.HUSKY)
                    , false
            );
        }
    }

    /**
     * Fazer o request do Feed de cachorros
     * */
    private void searchFeed(@DogCategory  String category) {
        apiListPhotos.asyncRequestDogFeed(this
                , category, userLogged.getToken());
    }

    /**
     *
     * {@link CallbackRequest}
     * */

    @Override
    public void onSuccess(DogFeed data) {
        safeDismissProgressMessage();
        removeAllElements(dogFeed.getPhotos().iterator());
        /**
         *
         * */
        this.dogFeed.setCategory(data.getCategory());
        this.dogFeed.getPhotos().addAll(data.getPhotos());
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
        safeDismissProgressMessage();
        /***
         * Mostrar uma mensagem de erro caso nao consiga baixar o feed
         * */
        viewMessage.build(responseMessage.getMessage()
                , ViewMessage.DURATION.INDETERMINATE, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMessage.dismissWithSafety();
            }
        })
        .showWithSafety();
    }


    private void safeShowProgressMessage(String title, String message, boolean cancelable) {
        if (viewProgressMessage.isShowing())
            viewProgressMessage.safeDismiss();

        viewProgressMessage
                .createDefaultAlert(title, message, cancelable)
                .create()
                .safeShow();
    }

    private void safeDismissProgressMessage() {
        viewProgressMessage.safeDismiss();
    }

    private String url;

    /**
     * {@link AdapterOnItemClickListener}
     * */
    @Override
    public void onItemClick(String url) {
        /**
         * Carregar uma DialogFrament com a imagem
         * */
        this.url = url;
        if (customDialogFragment != null) {
            customDialogFragment.show(getFragmentManager(), tagFragment);
        }
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (viewMessage != null)
            viewMessage.dismissWithSafety();
        safeDismissProgressMessage();
    }
}

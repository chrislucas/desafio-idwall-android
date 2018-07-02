package xplorer.br.com.apiidwall.view.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;


import xplorer.br.com.apiidwall.R;
import xplorer.br.com.apiidwall.model.User;
import xplorer.br.com.apiidwall.view.fragments.BaseFragment;
import xplorer.br.com.apiidwall.view.fragments.FragmentListDogs;

public class MainActivity extends AppCompatActivity {


    private User userLogged;
    public static final String USER_LOGGED = "USER_LOGGED";

    private Toolbar toolbar;
    private ActionBar actionBar;

    private void configureToolbar() {
        toolbar = findViewById(R.id.toolbar_app);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Dog's API");
        }
    }

    private void configureBackButtonOnFragment() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (fragmentManager.getBackStackEntryCount() > 1) {
                    ActionBar actionBar = getSupportActionBar();
                    if (actionBar != null) {
                        actionBar.setDisplayHomeAsUpEnabled(true);
                    }
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    });
                }
                else {
                    ActionBar actionBar = getSupportActionBar();
                    if (actionBar != null) {
                        actionBar.setDisplayHomeAsUpEnabled(true);
                    }
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {}
                    });
                }
            }
        });
    }

    private void addFragment(BaseFragment baseFragment) {
        FragmentManager fm = getSupportFragmentManager();
        if (fm != null) {
            /**
             * Nao adicionar o fragment mais de uma vez :)
             * */
            if ( fm.findFragmentByTag(baseFragment.getTagFragment()) == null ) {
                fm.beginTransaction()
                        // adicionando o fragment a pilha e marcando com sua tag
                        .replace(R.id.layout_replace, baseFragment, baseFragment.getTagFragment())
                        .addToBackStack(baseFragment.getTagFragment())
                        .commit();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureToolbar();
        configureBackButtonOnFragment();
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if(intent != null) {
                Bundle  bundle = intent.getExtras();
                if (bundle != null) {
                    userLogged = bundle.getParcelable(USER_LOGGED);
                }
                else {
                    /**
                     * TODO mostrar uma mensagem de erro
                     * */
                }
            }
        }
        else {
            userLogged = savedInstanceState.getParcelable(USER_LOGGED);
        }
        addFragment(FragmentListDogs.newInstance(userLogged));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager fm = getSupportFragmentManager();
        if (fm != null && fm.getBackStackEntryCount() == 0) {
            moveTaskToBack(true);
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            outState.putParcelable(USER_LOGGED, userLogged);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            userLogged = savedInstanceState.getParcelable(USER_LOGGED);
        }
    }
}

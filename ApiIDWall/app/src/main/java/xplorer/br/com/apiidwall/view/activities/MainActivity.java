package xplorer.br.com.apiidwall.view.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import xplorer.br.com.apiidwall.R;
import xplorer.br.com.apiidwall.model.User;
import xplorer.br.com.apiidwall.view.fragments.BaseFragment;
import xplorer.br.com.apiidwall.view.fragments.FragmentListDogs;

public class MainActivity extends AppCompatActivity {


    private User userLogged;
    public static final String USER_LOGGED = "USER_LOGGED";


    private void addFragment(BaseFragment baseFragment) {
        FragmentManager fm = getSupportFragmentManager();
        if (fm != null) {
            fm.beginTransaction()
                    .replace(R.id.layout_replace, baseFragment)
                    .addToBackStack(baseFragment.getTagFragment())
                    .commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}

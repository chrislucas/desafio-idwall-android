package xplorer.br.com.apiidwall.view.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.List;

import xplorer.br.com.apiidwall.API;
import xplorer.br.com.apiidwall.R;
import xplorer.br.com.apiidwall.model.User;
import xplorer.br.com.apiidwall.presenter.callbacks.CallbackRequest;
import xplorer.br.com.apiidwall.presenter.request.APIAuthentication;
import xplorer.br.com.apiidwall.presenter.response.ResponseMessage;
import xplorer.br.com.apiidwall.view.utils.Device;
import xplorer.br.com.apiidwall.view.utils.EmailValidator;

public class ActivityAuthentication extends AppCompatActivity implements CallbackRequest<User>{

    private EditText email;

    private Snackbar snackbar;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        email = findViewById(R.id.edit_text_email);
    }

    public void authentication(View view) {
        if (emailValidation()) {
            /**
             * TODO
             * 1) verificar conexao com a internet
             * 2) executar autenticacao
             * */
            if (Device.simpleIsConnected(this)) {
                // fazer a autenticacao
                APIAuthentication apiAuthentication = new APIAuthentication("api-iddog.idwall.co");
                // TODO
                apiAuthentication.authentication(this, API.BASE_URL);
            }
            else {
                showIndefiniteMessage("Você não possui conexão com a internet");
            }
        }
    }

    private boolean emailValidation() {
        String text = email.getText().toString();
        if (text.length() == 0 || text.matches("\\s+")) {
            email.setError("Email é um campo obrigatório");
            email.performClick();
            return false;
        }

        else if(!EmailValidator.validator(text)) {
            email.setError(String.format("Email '%s' não é um email válido", text));
            email.performClick();
            return false;
        }
        return true;
    }


    @Override
    public void onSuccess(User data) {
        Intent intent  = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(MainActivity.USER_LOGGED, data);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSuccess(List<User> data) {}

    @Override
    public void onFailure(ResponseMessage responseMessage) {

    }

    private void showIndefiniteMessage(CharSequence message) {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.wrapper_form_authentication)
                        , message, Snackbar.LENGTH_INDEFINITE);

        snackbar.show();
    }
}

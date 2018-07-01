package xplorer.br.com.apiidwall.view.activities;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import xplorer.br.com.apiidwall.R;
import xplorer.br.com.apiidwall.presenter.request.APIAuthentication;
import xplorer.br.com.apiidwall.view.utils.Device;
import xplorer.br.com.apiidwall.view.utils.EmailValidator;

public class ActivityAuthentication extends AppCompatActivity {

    private EditText email;

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
                apiAuthentication.authentication(null);
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


    private void showIndefiniteMessage(CharSequence message) {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.wrapper_form_authentication)
                        , message, Snackbar.LENGTH_INDEFINITE);

        snackbar.show();
    }
}

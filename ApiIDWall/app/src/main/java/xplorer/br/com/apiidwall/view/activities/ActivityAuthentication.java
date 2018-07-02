package xplorer.br.com.apiidwall.view.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import xplorer.br.com.apiidwall.API;
import xplorer.br.com.apiidwall.R;
import xplorer.br.com.apiidwall.db.HelperUserTable;
import xplorer.br.com.apiidwall.model.User;
import xplorer.br.com.apiidwall.presenter.callbacks.CallbackRequest;
import xplorer.br.com.apiidwall.presenter.request.APIAuthentication;
import xplorer.br.com.apiidwall.presenter.response.ResponseMessage;
import xplorer.br.com.apiidwall.utils.Device;
import xplorer.br.com.apiidwall.utils.EmailValidator;
import xplorer.br.com.apiidwall.utils.Keyboard;
import xplorer.br.com.apiidwall.utils.ViewMessage;
import xplorer.br.com.apiidwall.utils.ViewProgressMessage;

public class ActivityAuthentication extends AppCompatActivity implements CallbackRequest<User>{

    private EditText email;
    private static final int PERMISSION_REQUEST = 0xff;

    private ViewProgressMessage viewProgressMessage;

    private void getPermissions() {
        String [] permissions = {Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE};
        List<String> pDenied = new ArrayList<>();
        for(String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED)
                pDenied.add(permission);
        }
        if (pDenied.size() >  0) {
            permissions = new String[pDenied.size()];
            for (int i = 0; i < pDenied.size() ; i++) {
                permissions[i] = pDenied.get(i);
            }
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {}
        }
    }

    private ViewMessage viewMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        email = findViewById(R.id.edit_text_email);
        getPermissions();
        viewMessage = new ViewMessage(findViewById(R.id.wrapper_form_authentication));
        viewProgressMessage = new ViewProgressMessage(this, R.layout.layout_warning_progress);
    }

    public void authentication(View view) {
        if (emailValidation()) {
            Keyboard.hiddenSoftKeyPad(this);
            /**
             * TODO
             * 1) verificar conexao com a internet
             * 2) executar autenticacao
             * */
            if (Device.simpleIsConnected(this)) {
                // fazer a autenticacao
                APIAuthentication apiAuthentication = new APIAuthentication(API.BASE_URL);
                // TODO
                User user = new User();
                user.setEmail(email.getText().toString());
                apiAuthentication.authentication(this, user);
                toggleEnableButton(false);

                viewProgressMessage.createDefaultAlert("Autenticação online"
                        , "Aguarde alguns instantes enquanto realizamos sua autenticacao"
                        , false)
                        .create()
                        .safeShow();
            }
            else {
                if (viewMessage.isShowing())
                    viewMessage.dismissWithSafety();
                viewMessage.build("Você não possui conexão com a internet"
                        , ViewMessage.DURATION.INDETERMINATE, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewMessage.dismissWithSafety();
                    }
                })
                .showWithSafety();
            }
        }
    }

    private void toggleEnableButton(boolean flag) {
        findViewById(R.id.button_authentication).setEnabled(flag);
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

    private boolean createOrUpdateUserData(User user) {
        boolean flag = true;
        try {
            HelperUserTable helperUserTable = new HelperUserTable(this);
            if (helperUserTable.exists(user)) {
                String whereArgs = "email=? AND token=?";
                String args [] = {user.getEmail(), user.getToken()};
                try {
                    if ( helperUserTable.update(user, whereArgs, args) < 1) {
                        if (viewMessage.isShowing()) {
                            viewMessage.dismissWithSafety();
                        }
                        viewMessage.build("Problemas para atualizar os dados do usuario"
                                , ViewMessage.DURATION.INDETERMINATE, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        viewMessage.dismissWithSafety();
                                    }
                                })
                                .showWithSafety();
                        flag = false;
                    }
                }
                catch (Exception e) {
                    flag = false;
                    String message = e.getMessage();
                    Log.e("EXCP_UPDATE", message == null ? "Problemas para recuperar a excecação" : message);
                }
            }
            else {
                if (helperUserTable.insert(user) < 1) {
                    if (viewMessage.isShowing()) {
                        viewMessage.dismissWithSafety();
                    }
                    viewMessage.build("Problemas para inserir os dados do usuario"
                            , ViewMessage.DURATION.INDETERMINATE, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    viewMessage.dismissWithSafety();
                                }
                            })
                            .showWithSafety();
                    flag = false;
                }
            }
        }
        catch (Exception e) {
            String message = e.getMessage();
            Log.e("EXCP_UP_OR_INSERT", message == null ? "Não foi possível recuperar o erro" : e.getMessage());
            flag = false;
        }
        return flag;
    }

    @Override
    public void onSuccess(User data) {
        viewProgressMessage.safeDismiss();
        if ( createOrUpdateUserData(data) ) {
            Intent intent  = new Intent(this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(MainActivity.USER_LOGGED, data);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
        else {
            // Se ocorrer algum problema, habilite o botao de autenticacao
            toggleEnableButton(true);
        }
    }

    @Override
    public void onSuccess(List<User> data) {}

    @Override
    public void onFailure(ResponseMessage responseMessage) {
        viewProgressMessage.safeDismiss();
        toggleEnableButton(true);
        if (viewMessage.isShowing())
            viewMessage.dismissWithSafety();
        viewMessage.build(responseMessage.getMessage()
                , ViewMessage.DURATION.INDETERMINATE, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMessage.dismissWithSafety();
            }
        })
        .showWithSafety();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewProgressMessage.safeDismiss();
        if (viewMessage != null)
            viewMessage.dismissWithSafety();
    }
}

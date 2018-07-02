package xplorer.br.com.apiidwall.presenter.request;

import android.support.annotation.NonNull;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xplorer.br.com.apiidwall.model.ErrorMessage;
import xplorer.br.com.apiidwall.model.User;
import xplorer.br.com.apiidwall.presenter.callbacks.CallbackRequest;
import xplorer.br.com.apiidwall.presenter.request.Endpoint.Authentication;
import xplorer.br.com.apiidwall.presenter.request.retrofit.RetrofitInstance;
import xplorer.br.com.apiidwall.presenter.request.retrofit.factories.FactoryConversionUser;

public class APIAuthentication {

    private String baseURL;

    public APIAuthentication(String baseURL) {
        this.baseURL = baseURL;
    }

    public Call<User> authentication(final CallbackRequest<User> callbackRequest, final User u) {
        Authentication authentication = RetrofitInstance.getService(Authentication.class
                , new FactoryConversionUser(), baseURL);
        Call<User> call = authentication.signup(u);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    if (user == null) {
                        String message = "Ocorreu um problema ao recuperar os dados do usuario :( ";
                        ErrorMessage errorMessage = new ErrorMessage(message);
                        callbackRequest.onFailure(errorMessage);
                    }
                    else {
                        user.setEmail(u.getEmail());
                        callbackRequest.onSuccess(user);
                    }
                }
                else {
                    String message = String.format(Locale.getDefault()
                            , "%d. Um erro ocorreu, desculpe-nos pelo transtorno:("
                            , response.code());
                    ErrorMessage errorMessage = new ErrorMessage(message);
                    callbackRequest.onFailure(errorMessage);
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                String message = "Problemas com o serviço de autenticação, desculpe-nos pelo transtorno:(";
                ErrorMessage errorMessage = new ErrorMessage(message);
                callbackRequest.onFailure(errorMessage);
            }
        });
        return call;
    }
}

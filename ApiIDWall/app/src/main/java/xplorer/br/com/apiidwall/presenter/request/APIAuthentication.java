package xplorer.br.com.apiidwall.presenter.request;

import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

    public void authentication(CallbackRequest callbackRequest, String email) {
        Authentication authentication = RetrofitInstance.getService(Authentication.class, new FactoryConversionUser(), baseURL);
        Call<User> call = authentication.signup(email);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {

            }

            @Override
            public void onFailure(@NonNull Call<User> call, Throwable t) {

            }
        });
    }
}

package xplorer.br.com.apiidwall.presenter.request.Endpoint;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import xplorer.br.com.apiidwall.model.User;

public interface Authentication {

    @POST("signup")
    Call<User> signup(@Body String email);
}

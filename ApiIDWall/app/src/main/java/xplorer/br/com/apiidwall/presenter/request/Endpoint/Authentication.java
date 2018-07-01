package xplorer.br.com.apiidwall.presenter.request.Endpoint;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Authentication {

    @POST("signup")
    Call signup(@Body String email);
}

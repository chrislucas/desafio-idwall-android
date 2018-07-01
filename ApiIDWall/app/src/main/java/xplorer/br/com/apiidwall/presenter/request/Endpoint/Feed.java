package xplorer.br.com.apiidwall.presenter.request.Endpoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Feed {
    @GET("feed")
    Call getFeed(@Query("category") @DogCategory String category);
}

package xplorer.br.com.apiidwall.presenter.request.Endpoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import xplorer.br.com.apiidwall.model.DogFeed;

public interface Feed {
    @GET("feed")
    Call<DogFeed> getFeed(@Query("category") @DogCategory String category);
}

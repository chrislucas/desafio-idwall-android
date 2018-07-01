package xplorer.br.com.apiidwall.presenter.request;

import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xplorer.br.com.apiidwall.model.DogFeed;
import xplorer.br.com.apiidwall.model.ErrorMessage;
import xplorer.br.com.apiidwall.presenter.callbacks.CallbackRequest;

import xplorer.br.com.apiidwall.presenter.request.Endpoint.Feed;
import xplorer.br.com.apiidwall.presenter.request.retrofit.RetrofitInstance;

public class APIListPhotos {

    private String baseURL;

    public APIListPhotos(String baseURL) {
        this.baseURL = baseURL;
    }


    public Call<DogFeed>asyncRequestDogFeed(final CallbackRequest<DogFeed> callbackRequest, String category) {
        Feed feed = RetrofitInstance.getService(Feed.class, null, baseURL);
        Call<DogFeed> call = feed.getFeed(category);

        call.enqueue(new Callback<DogFeed>() {
            @Override
            public void onResponse(@NonNull Call<DogFeed> call, @NonNull Response<DogFeed> response) {
                if(response.isSuccessful()) {
                    DogFeed dogFeed = response.body();
                    if (dogFeed != null) {
                        callbackRequest.onSuccess(dogFeed);
                    }
                    else {
                        String message = "Ocorreu um problema ao processar o Feed :( ";
                        ErrorMessage errorMessage = new ErrorMessage(message);
                        callbackRequest.onFailure(errorMessage);
                    }
                }

                else {
                    String message = "%d. Um erro ocrreu, desculpe-nos pelo transtorno:(";
                    ErrorMessage errorMessage = new ErrorMessage(message);
                    callbackRequest.onFailure(errorMessage);
                }
            }

            @Override
            public void onFailure(@NonNull Call<DogFeed> call, @NonNull Throwable t) {
                String message = "Ocorreu um problema em ossa aplicação. Desculpe-nos pelo transtorno:(";
                ErrorMessage errorMessage = new ErrorMessage(message);
                callbackRequest.onFailure(errorMessage);
            }
        });

        return call;
    }
}

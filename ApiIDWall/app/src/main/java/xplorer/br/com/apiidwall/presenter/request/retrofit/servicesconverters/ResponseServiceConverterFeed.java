package xplorer.br.com.apiidwall.presenter.request.retrofit.servicesconverters;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import xplorer.br.com.apiidwall.model.DogFeed;
import xplorer.br.com.apiidwall.presenter.request.retrofit.converters.DogFeedConverter;

public class ResponseServiceConverterFeed implements Converter<ResponseBody, DogFeed> {

    @Override
    public DogFeed convert(@NonNull ResponseBody value) throws IOException {
        return new DogFeedConverter().fromJsonToObject(value.string());
    }
}

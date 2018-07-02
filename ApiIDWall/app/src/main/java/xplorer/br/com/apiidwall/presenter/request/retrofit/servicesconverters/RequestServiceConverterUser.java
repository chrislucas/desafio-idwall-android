package xplorer.br.com.apiidwall.presenter.request.retrofit.servicesconverters;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;
import xplorer.br.com.apiidwall.model.User;
import xplorer.br.com.apiidwall.presenter.request.retrofit.converters.UserConverter;

public class RequestServiceConverterUser implements Converter<User, RequestBody> {

    @Override
    public RequestBody convert(@NonNull User user) throws IOException {
        UserConverter userConverter = new UserConverter();
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        return RequestBody.create(mediaType, userConverter.fromObjectToJson(user));
    }
}

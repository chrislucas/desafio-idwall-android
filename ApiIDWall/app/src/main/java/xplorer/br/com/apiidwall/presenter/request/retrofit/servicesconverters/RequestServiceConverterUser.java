package xplorer.br.com.apiidwall.presenter.request.retrofit.servicesconverters;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.RequestBody;
import retrofit2.Converter;
import xplorer.br.com.apiidwall.model.User;

public class RequestServiceConverterUser implements Converter<User, RequestBody> {

    @Override
    public RequestBody convert(@NonNull User value) throws IOException {
        return null;
    }
}

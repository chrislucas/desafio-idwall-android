package xplorer.br.com.apiidwall.presenter.request.retrofit.servicesconverters;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import xplorer.br.com.apiidwall.model.User;
import xplorer.br.com.apiidwall.presenter.request.retrofit.converters.UserConverter;

public class ResponseServiceConverterUser implements Converter<ResponseBody, User> {

    @Override
    public User convert(ResponseBody value) throws IOException {
        return new UserConverter().fromJsonToObject(value.string());
    }
}

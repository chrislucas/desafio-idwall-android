package xplorer.br.com.apiidwall.presenter.request.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class RetrofitInstance {

    /**
     *
     * Outro metodo que nos retorna uma instancia do Retrofit e espera que a resposa seja um JSON.
     * A classe {@link retrofit2.converter.gson.GsonConverterFactory} é a Factory responsavel por processar o response
     * */
    public static <Clazz> Clazz getService(Class<Clazz> clazz, Converter.Factory factory, String baseUrl) {
        return getRetrofitInstance(factory, baseUrl).create(clazz);
    }


    private static Retrofit getRetrofitInstance(Converter.Factory factory, String url) {
        OkHttpClient.Builder builderHttpClient = new OkHttpClient.Builder();
        OkHttpClient httpClient = builderHttpClient.build();
        builderHttpClient.connectTimeout(1, TimeUnit.MINUTES);
        builderHttpClient.readTimeout(30, TimeUnit.SECONDS);
        builderHttpClient.writeTimeout(30, TimeUnit.SECONDS);
        Retrofit.Builder builderRetrofit = new Retrofit
                .Builder()
                .baseUrl(url)
                .addConverterFactory(factory);
        return builderRetrofit.client(httpClient).build();
    }
}

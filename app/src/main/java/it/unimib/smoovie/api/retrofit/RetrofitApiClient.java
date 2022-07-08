package it.unimib.smoovie.api.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApiClient {

    private static RetrofitApiClient instance;
    private final Retrofit retrofit;

    private RetrofitApiClient() {
         retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static RetrofitApiClient getInstance() {
        if(instance == null) {
            synchronized (RetrofitApiClient.class) {
                instance = new RetrofitApiClient();
            }
        }

        return instance;
    }

    public <T> T getService(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }
}

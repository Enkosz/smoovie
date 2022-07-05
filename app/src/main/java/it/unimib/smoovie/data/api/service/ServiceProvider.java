package it.unimib.smoovie.data.api.service;

import it.unimib.smoovie.data.api.retrofit.RetrofitApiClient;

public class ServiceProvider {

    private static ServiceProvider instance;
    private final RetrofitApiClient retrofitApiClient;

    private ServiceProvider() {
        this.retrofitApiClient = RetrofitApiClient.getInstance();
    }

    public static ServiceProvider getInstance() {
        if (instance == null) {
            synchronized(ServiceProvider.class) {
                instance = new ServiceProvider();
            }
        }

        return instance;
    }

    public MovieApiService getMovieApiService() {
        return retrofitApiClient.getService(MovieApiService.class);
    }
}

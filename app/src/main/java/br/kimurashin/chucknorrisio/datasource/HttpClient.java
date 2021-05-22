package br.kimurashin.chucknorrisio.datasource;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {
    static Retrofit retrofit() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ChuckNorrisAPI.BASE_URL)
                .build();
    }
}

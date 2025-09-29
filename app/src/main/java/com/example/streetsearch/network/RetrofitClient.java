package com.example.streetsearch.network;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RetrofitClient {
    public static final String BASE_URL = "https://nominatim.openstreetmap.org/";
    private static NominatimService service;

    public static NominatimService getNominatimService(){
        if(service == null){
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            Interceptor userAgentInterceptor = chain -> {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("User-Agent", "EnderecoFacil/1.0 (barroso.junior@outlook.com)").build();
                return chain.proceed(request);
            };
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(userAgentInterceptor)
                    .addInterceptor(logging)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service = retrofit.create(NominatimService.class);
        }
        return service;
    }

}

package com.example.cuahangonline.ultil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;
    public static Retrofit getClient(String baseUrl){
        OkHttpClient builder = new OkHttpClient.Builder()
                                            .readTimeout(5000, TimeUnit.MICROSECONDS)
                                            .writeTimeout(5000,TimeUnit.MICROSECONDS)
                                            .connectTimeout(10000,TimeUnit.MICROSECONDS)
                                            .retryOnConnectionFailure(true)
                                            .build();
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(builder)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit;
    }
}

package com.example.cuahangonline.ultil;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();


    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.1.20/shopping/admin/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class) ;




    @Multipart
    @POST("updateImageAC.php")
    Call<String> UploadPhot(@Part MultipartBody.Part photo , @Part MultipartBody.Part id) ;

}

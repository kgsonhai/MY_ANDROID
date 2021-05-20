package com.example.cuahangonline.ultil;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface DataClient {

    @Multipart
    @POST("updateImageAC.php")
    Call<String> UploadIMG(@Part MultipartBody.Part photo);

}

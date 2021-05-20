package com.example.cuahangonline.ultil;

public class APIUntils {
    public static final String Base_url = "http://192.168.1.7/Android/";

    public static DataClient getData(){
        return RetrofitClient.getClient(Base_url).create(DataClient.class);
    }
}

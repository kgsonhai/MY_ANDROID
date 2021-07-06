package com.example.cuahangonline.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangonline.Model.sanpham;
import com.example.cuahangonline.R;
import com.example.cuahangonline.ultil.CheckConnection;
import com.example.cuahangonline.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Splash_activity extends AppCompatActivity {
    Animation anim;
    ImageView img;
    boolean loading = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_activity);


        loadData();
    }


    private void loadData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdanSanPhamMoiNhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    int ID = 0;
                    String tensp = "";
                    Integer giasp = 0;
                    String hinhanhSP = "";
                    String motaSP = "";
                    int IDLoaisp = 0;
                    for (int i=0; i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            tensp = jsonObject.getString("tensp");
                            giasp = jsonObject.getInt("giasp");
                            hinhanhSP = jsonObject.getString("hinhanhsp");
                            motaSP = jsonObject.getString("motasp");
                            IDLoaisp = jsonObject.getInt("idLoaisp");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    loading = true;
                    if (CheckConnection.haveNetworkConnect(getApplicationContext())){
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                if (loading==true){
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                }else{
                        CheckConnection.ShowToast_short(getApplicationContext(),"Đang tải dữ liệu!!");
                                }
                            }
                        });
                    }else{
                        CheckConnection.ShowToast_short(getApplicationContext(),"Kiểm tra kết nối");
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

}
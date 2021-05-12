package com.example.cuahangonline.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangonline.Model.sanpham;
import com.example.cuahangonline.R;
import com.example.cuahangonline.ultil.CheckConnection;
import com.example.cuahangonline.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Dangky_Activity extends AppCompatActivity {
    EditText txtTen,txtUsername,txtPass,txtSdt,txtDiaChi,txtEmail;
    Button btnXacNhan,btnHuy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky_);

        Anhxa();
        EventClickButton();

    }

    private void EventClickButton() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostData();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void PostData(){
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanSignin, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("1")){
                        Toast.makeText(getApplicationContext(),"Đăng ký thành công",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),ThongTin_Activity.class);
                        startActivity(intent);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> params = new HashMap<String, String>();
                    params.put("fullname",txtTen.getText().toString());
                    params.put("username",txtUsername.getText().toString());
                    params.put("password",txtPass.getText().toString());
                    params.put("email",txtEmail.getText().toString());
                    params.put("phone",txtSdt.getText().toString());
                    params.put("diachi",txtDiaChi.getText().toString());

                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }

    private void Anhxa() {
        txtTen = findViewById(R.id.txtTenDangKy);
        txtUsername = findViewById(R.id.txtTenTKDangKy);
        txtPass = findViewById(R.id.txtpasswordDangKy);
        txtSdt = findViewById(R.id.txtsdtDangKy);
        txtDiaChi = findViewById(R.id.txtdiachiDangKy);
        txtEmail = findViewById(R.id.txtemailDangKy);
        btnXacNhan = findViewById(R.id.btnXacNhanDangKy);
        btnHuy = findViewById(R.id.btnHuyDangKy);
    }
}
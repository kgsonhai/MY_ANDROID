package com.example.cuahangonline.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangonline.Model.Information_user;
import com.example.cuahangonline.R;
import com.example.cuahangonline.ultil.CheckConnection;
import com.example.cuahangonline.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class ThongTin_Activity extends AppCompatActivity {
    private EditText txtTenDangNhap,txtMatKhau;
    private Button btnXacNhan,btnHuy;
    int id = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_);

        Anhxa();
        EventButton();

    }


    private void EventButton() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),txtTenDangNhap.getText()+"\n"+txtMatKhau.getText(),Toast.LENGTH_LONG).show();
               GetData();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void GetData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanLogin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String user = "";
                String email = "";
                String ten = "";
                String pass = "";
                String sdt = "";
                String diachi = "";
                String avata = "";
                if (response != null && response.length() != 2){
                    try {
                        MainActivity.displayAccount = true;
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            user = jsonObject.getString("username");
                            pass = jsonObject.getString("password");
                            email = jsonObject.getString("email");
                            ten = jsonObject.getString("ten");
                            sdt = jsonObject.getString("sdt");
                            diachi = jsonObject.getString("diachi");
                            avata = jsonObject.getString("avata_user");
                        }

                        MainActivity.informationUser = new Information_user(id,ten,sdt,diachi,avata,user,email);
                            if (MainActivity.manggiohang.size()>0){
                                Intent intent = new Intent(getApplicationContext(), ThanhToan_Activity.class);
                                startActivity(intent);
                            }else{
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Không tồn tại tài khoản",Toast.LENGTH_LONG).show();
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
                params.put("UserName",txtTenDangNhap.getText().toString().trim());
                params.put("PassWord",txtMatKhau.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public static String convertByteToHex(byte[] data) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            sb.append(Integer.toString((data[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

        public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            return convertByteToHex(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }



    private void Anhxa() {
        txtTenDangNhap = (EditText) findViewById(R.id.txtTenKhachHang);
        txtMatKhau = (EditText) findViewById(R.id.txtSoPasswordKhachHang);
        btnXacNhan = (Button) findViewById(R.id.btnXacNhanThongTinKH);
        btnHuy = (Button) findViewById(R.id.btnQuayLaiThongTinKH);
    }
}
package com.example.cuahangonline.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangonline.Adapter.Thanhtoan_adapter;
import com.example.cuahangonline.Model.Database;
import com.example.cuahangonline.R;
import com.example.cuahangonline.ultil.CheckConnection;
import com.example.cuahangonline.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ThanhToan_Activity extends AppCompatActivity {
    private static int Tongdonhang = 0;
    Toolbar toolbar;
    ListView listViewThanhToan;
    Button btndathang;
    Thanhtoan_adapter thanhtoan_adapter;
    TextView txthoten,txtSdt,txtDiachi;
    static TextView txtTongDonHang,txtTongThanhToan1,txtTongThanhToan2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan_);

        Anhxa();
        ActionToolBar();
        EventDisplayData();
        DisplayThongtinKH();
        EventButton();
    }
    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThanhToan_Activity.this,GioHang_Activity.class);
                startActivity(intent);
            }
        });
    }
    public static void EventDisplayData() {
        for (int i=0;i<MainActivity.manggiohang.size();i++){
            Tongdonhang += MainActivity.manggiohang.get(i).getGiasp();
        }
        long Tongtien = Tongdonhang + 15000;
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTongDonHang.setText(decimalFormat.format(Tongdonhang)+" ??");
        txtTongThanhToan1.setText(decimalFormat.format(Tongtien)+" ??");
        txtTongThanhToan2.setText(decimalFormat.format(Tongtien)+" ??");
    }

    public void DisplayThongtinKH(){
        txthoten.setText("H??? t??n: "+MainActivity.informationUser.getTen());
        txtSdt.setText("S??? ??T: 0"+MainActivity.informationUser.getSdt());
        txtDiachi.setText("?????a ch???: "+MainActivity.informationUser.getDiachi());
    }

    public void DeleteCartToDB(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.duongdanXoaItemGioHang;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
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
                HashMap<String,String> params = new HashMap<String,String>();
                params.put("id_user", String.valueOf(MainActivity.informationUser.getIdUser()));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    public void EventButton(){
        btndathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteCartToDB();

                final int idUser = MainActivity.informationUser.idUser;
                final String ten  = MainActivity.informationUser.getTen().trim();
                final String sdt = MainActivity.informationUser.getSdt().trim();
                final String diachi = MainActivity.informationUser.getDiachi().trim();
                final String totalsp = String.valueOf(Tongdonhang + 15000);
                if (ten.length()>0 && sdt.length()>0 && diachi.length()>0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdandonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            if (Integer.parseInt(madonhang) > 0){
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest request =  new StringRequest(Request.Method.POST, Server.duongdanchitietdonhang,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                MainActivity.manggiohang.clear();
                                                CheckConnection.ShowToast_short(getApplicationContext(),"B???n d?? mua h??ng th??nh c??ng");
                                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                                    startActivity(intent);
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Nullable
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for (int i = 0;i<MainActivity.manggiohang.size();i++){
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("madonhang",madonhang);
                                                jsonObject.put("masanpham",MainActivity.manggiohang.get(i).getIdsp());
                                                jsonObject.put("soluong",MainActivity.manggiohang.get(i).getSoluongsp());
                                                jsonObject.put("gia",MainActivity.manggiohang.get(i).getGiasp());

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String,String> hashMap = new HashMap<String, String>();
                                        hashMap.put("json",jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                queue.add(request);
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
                            HashMap<String,String>  hashMap = new HashMap<String, String>();
                            hashMap.put("idUser", String.valueOf(idUser));
                            hashMap.put("tenkh",ten);
                            hashMap.put("sdtkh",sdt);
                            hashMap.put("diachikh",diachi);
                            hashMap.put("TotalSP",totalsp);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else{
                    CheckConnection.ShowToast_short(getApplicationContext(),"Ki???m tra l???i k???t n???i");
                }
            }
        });
    }
    private void Anhxa(){
        toolbar = findViewById(R.id.toolbarMhThanhToan);
        txthoten = findViewById(R.id.hotenGiaoHang);
        txtSdt = findViewById(R.id.sdtGiaoHang);
        txtDiachi = findViewById(R.id.diachiGiaoHang);
        btndathang = (Button) findViewById(R.id.btnDatHangThanhToan);
        listViewThanhToan = findViewById(R.id.ListViewThanhToan);
        txtTongDonHang = findViewById(R.id.txtDonHangThanhToan);
        txtTongThanhToan1 = findViewById(R.id.txtTongThanhToan1);
        txtTongThanhToan2 = findViewById(R.id.txtTongThanhToan2);
        thanhtoan_adapter = new Thanhtoan_adapter(MainActivity.manggiohang, ThanhToan_Activity.this);
        listViewThanhToan.setAdapter(thanhtoan_adapter);



    }
}
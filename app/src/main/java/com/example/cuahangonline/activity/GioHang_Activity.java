package com.example.cuahangonline.activity;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Lifecycle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangonline.Adapter.GioHangAdapter;
import com.example.cuahangonline.Model.Database;
import com.example.cuahangonline.Model.giohang;
import com.example.cuahangonline.Model.sanpham;
import com.example.cuahangonline.R;
import com.example.cuahangonline.ultil.CheckConnection;
import com.example.cuahangonline.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class GioHang_Activity extends AppCompatActivity {
    ListView listViewGioHang;
    TextView txtThongBao;
    static TextView txtTongTien;
    Button btnThanhToan,btnTiepTucMua;
    Toolbar toolbar;
    GioHangAdapter gioHangAdapter;
    Database database;
    ArrayList<giohang> arrayListGioHang;
    boolean checkAccount = false;
    public static int checkCart = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        Anhxa();
        ActionToolBar();
        GetCartFromDB();
        CheckData();
        CatchOnItemListView();
        EventDisplayData();
        EventButtonMuavaThanhToan();
        if(MainActivity.manggiohang.size() > 0 && MainActivity.displayAccount == true){
            PushCartToDB();
        }
    }

    private void EventButtonMuavaThanhToan() {
        btnTiepTucMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.manggiohang.size()>0){
                    if(MainActivity.displayAccount == false){
                        Intent intent = new Intent(getApplicationContext(),ThongTin_Activity.class);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(getApplicationContext(), ThanhToan_Activity.class);
                        startActivity(intent);
                        PushCartToDB();
                    }
                }else{
                    CheckConnection.ShowToast_short(getApplicationContext(),"Giỏ hàng của bạn chưa có sản phẩm nào");
                }
            }
        });
    }

    private void CatchOnItemListView() {
        listViewGioHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHang_Activity.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (MainActivity.manggiohang.size() <= 0){
                            txtThongBao.setVisibility(View.VISIBLE);
                        }else{
                            if (MainActivity.displayAccount == true) {
                                DeleteCartToDB(MainActivity.manggiohang.get(position).idsp);
                            }
                            MainActivity.manggiohang.remove(position);
                            gioHangAdapter.notifyDataSetChanged();
                            EventDisplayData();
                            if (MainActivity.manggiohang.size() <= 0){
                                txtThongBao.setVisibility(View.VISIBLE);
                            }else{
                                txtThongBao.setVisibility(View.INVISIBLE);
                                gioHangAdapter.notifyDataSetChanged();
                                EventDisplayData();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gioHangAdapter.notifyDataSetChanged();
                        EventDisplayData();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void EventDisplayData() {
        int Tongtien = 0;
        for (int i=0;i<MainActivity.manggiohang.size();i++){
            Tongtien += MainActivity.manggiohang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTongTien.setText(decimalFormat.format(Tongtien)+" Đ");
    }

    private void CheckData() {
        if (MainActivity.manggiohang.size() <= 0){
            gioHangAdapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.VISIBLE);
            listViewGioHang.setVisibility(View.INVISIBLE);
        }else{
            txtThongBao.setVisibility(View.INVISIBLE);
            listViewGioHang.setVisibility(View.VISIBLE);
        }
    }

    public void GetCartFromDB(){
        if(MainActivity.displayAccount == true && checkCart == 0){
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            String duongdan = Server.duongdanLayGioHang;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response != null) {
                        int id = 0;
                        String tensp = "";
                        Integer giasp = 0;
                        String hinhanhsp = "";
                        int soluong = 0;
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i <= jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                id = jsonObject.getInt("id");
                                tensp = jsonObject.getString("tensp");
                                giasp = jsonObject.getInt("giasp");
                                hinhanhsp = jsonObject.getString("hinhanhsp");
                                soluong = jsonObject.getInt("soluongsp");

                                Log.d("xxx",tensp);

                                MainActivity.manggiohang.add(new giohang(id, tensp, giasp, "http://192.168.1.20/shopping/admin/"+hinhanhsp, soluong));
                                checkCart = 1;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("idUser", String.valueOf(MainActivity.informationUser.getIdUser()));
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }

    public void PushCartToDB(){
        if(MainActivity.manggiohang.size() > 0){
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            String duongdan = Server.duongdanPutGioHang;
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
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    JSONArray jsonArray = new JSONArray();
                    for (int i = 0;i<MainActivity.manggiohang.size();i++){
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("id_sanpham",MainActivity.manggiohang.get(i).getIdsp());
                            jsonObject.put("soluong",MainActivity.manggiohang.get(i).getSoluongsp());
                            jsonObject.put("id_user",MainActivity.informationUser.getIdUser());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        jsonArray.put(jsonObject);
                    }
                    HashMap<String,String> hashMap = new HashMap<String, String>();
                    hashMap.put("json",jsonArray.toString());
                    hashMap.put("idUser", String.valueOf(MainActivity.informationUser.getIdUser()));
                    return hashMap;
                }
            };
            requestQueue.add(stringRequest);
        }
    }

    public void DeleteCartToDB(int idsp){
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
                params.put("idSP", String.valueOf(idsp));
                params.put("id_user", String.valueOf(MainActivity.informationUser.getIdUser()));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    private void Anhxa() {
        listViewGioHang = (ListView) findViewById(R.id.listViewGioHang);
        txtThongBao = (TextView) findViewById(R.id.txtThongBaoGioHang);
        txtTongTien = (TextView) findViewById(R.id.txtTongTienGioHang);
        btnThanhToan = (Button) findViewById(R.id.btnThanhToanGioHang);
        btnTiepTucMua = (Button) findViewById(R.id.btnTieptucMuahang);
        toolbar = (Toolbar) findViewById(R.id.toolbarGioHang);
        gioHangAdapter = new GioHangAdapter(MainActivity.manggiohang, GioHang_Activity.this,android.R.layout.simple_list_item_multiple_choice);
        listViewGioHang.setAdapter(gioHangAdapter);
        arrayListGioHang = new ArrayList<>();
        checkAccount = MainActivity.displayAccount;
    }

}
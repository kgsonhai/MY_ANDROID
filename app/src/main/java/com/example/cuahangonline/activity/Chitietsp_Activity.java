package com.example.cuahangonline.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import com.example.cuahangonline.Model.giohang;
import com.example.cuahangonline.Model.sanpham;
import com.example.cuahangonline.R;
import com.example.cuahangonline.ultil.CheckConnection;
import com.example.cuahangonline.ultil.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Chitietsp_Activity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView imgChitet;
    TextView txtTenChitiet,txtGiaChitiet,txtmotaChitiet,txtSLSP;
    Spinner spinner;
    Button btndatHang;

    int id = 0;
    String tenchitiet = "";
    int giachitiet = 0;
    String hinhanhchitiet = "";
    String motachitiet = "";
    int IdloaiSP = 0;
    public static String CountProduct = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsp_);

        Anhxa();
        ActionToolBar();
        GetInformationData();
        CatchEventSpiner();
        EventButton();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuGioHang:
                Intent intent = new Intent(getApplicationContext(), GioHang_Activity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void EventButton() {
        getCountProduct();
        btndatHang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MainActivity.manggiohang.size() > 0) {
                        int SL = Integer.parseInt(spinner.getSelectedItem().toString());
                        boolean exits = false;
                        for (int i = 0; i < MainActivity.manggiohang.size(); i++) {
                            if (MainActivity.manggiohang.get(i).getIdsp() == id) {
                                if ((Integer.parseInt(CountProduct) - (MainActivity.manggiohang.get(i).getSoluongsp() + SL)) >= 0) {
                                    MainActivity.manggiohang.get(i).setSoluongsp(MainActivity.manggiohang.get(i).getSoluongsp() + SL);
                                    if (MainActivity.manggiohang.get(i).getSoluongsp() >= 10) {
                                        MainActivity.manggiohang.get(i).setSoluongsp(10);
                                    }
                                    MainActivity.manggiohang.get(i).setGiasp(giachitiet * MainActivity.manggiohang.get(i).getSoluongsp());
                                    exits = true;
                                    Intent intent = new Intent(getApplicationContext(), GioHang_Activity.class);
                                    startActivity(intent);
                                } else {
                                    exits = true;
                                    CheckConnection.ShowToast_short(getApplicationContext(), "Không đủ số lượng sản phẩm");
                                }
                            }
                        }
                        if (exits == false) {
                            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                            if ((Integer.parseInt(CountProduct) - soluong) >= 0) {
                                int giamoiSP = soluong * giachitiet;
                                MainActivity.manggiohang.add(new giohang(id, tenchitiet, giachitiet, hinhanhchitiet, soluong));
                                Intent intent = new Intent(getApplicationContext(), GioHang_Activity.class);
                                startActivity(intent);
                            }
                        }
                    } else {
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        if ((Integer.parseInt(CountProduct) - soluong) >= 0) {
                            long giamoiSP = soluong * giachitiet;
                            MainActivity.manggiohang.add(new giohang(id, tenchitiet, giachitiet, hinhanhchitiet, soluong));
                            Intent intent = new Intent(getApplicationContext(), GioHang_Activity.class);
                            startActivity(intent);
                        } else {
                            btndatHang.setEnabled(false);
                            CheckConnection.ShowToast_short(getApplicationContext(), "Không đủ số lượng sản phẩm");
                        }

                    }
                }
            });
    }

    private void CatchEventSpiner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInformationData() {
        sanpham sanpham = (com.example.cuahangonline.Model.sanpham) getIntent().getSerializableExtra("thongtinsanpham");
        id = sanpham.getID();
        tenchitiet = sanpham.getTensp();
        giachitiet = sanpham.getGiasp();
        hinhanhchitiet = sanpham.getHinhanhsp();
        motachitiet = sanpham.getMotaSP();
        IdloaiSP = sanpham.getIDLoaiSP();

        txtTenChitiet.setText(tenchitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtGiaChitiet.setText("Giá : "+decimalFormat.format(giachitiet)+ " Đ");
        txtmotaChitiet.setText(motachitiet);
        Picasso.with(getApplicationContext()).load(hinhanhchitiet).into(imgChitet);

    }

    public void getCountProduct(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.duongdanCountSLSP;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null){
                    CountProduct = response.toString();
                    if (Integer.parseInt(CountProduct) != 0){
                        txtSLSP.setText("Còn lại :"+CountProduct+" sản phẩm");
                    }else{
                        txtSLSP.setText("Hết hàng");
                    }
                }else{
                    CountProduct = "Đã hết sản phẩm";
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
                params.put("idSP", String.valueOf(id));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void Anhxa() {
        toolbar =  (Toolbar) findViewById(R.id.toolBarChitietsp);
        imgChitet = (ImageView) findViewById(R.id.imgChitietsp);
        txtTenChitiet = (TextView) findViewById(R.id.txtTenChiTietsp);
        txtGiaChitiet = (TextView) findViewById(R.id.txtGiaChitietsp);
        txtmotaChitiet = (TextView) findViewById(R.id.txtMotaChitietSp);
        txtSLSP = (TextView) findViewById(R.id.txtSLsanphamDB);
        spinner = (Spinner) findViewById(R.id.spinerSoluong);
        btndatHang = (Button) findViewById(R.id.btnDatHang);
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
}
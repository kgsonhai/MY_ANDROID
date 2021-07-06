package com.example.cuahangonline.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangonline.Adapter.DonHang1_Adapter;
import com.example.cuahangonline.Adapter.DonHang_Adapter;
import com.example.cuahangonline.Model.DonHang;
import com.example.cuahangonline.R;
import com.example.cuahangonline.activity.MainActivity;
import com.example.cuahangonline.ultil.CheckConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class HuyDon_fragment extends Fragment {
    RecyclerView recyclerView;
    ImageView imgemptyDH;
    ArrayList<DonHang> donHangArrayList;
    DonHang1_Adapter adapter;
    int status = 4;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_donhuy, container, false);
        Anhxa(view);
        getData();
        return view;
    }

    private void Anhxa(View view) {
        recyclerView = view.findViewById(R.id.rcvDonHuy);
        imgemptyDH = view.findViewById(R.id.imgEmptyDH4);
        donHangArrayList = new ArrayList<>();
        adapter = new DonHang1_Adapter(donHangArrayList,getContext());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String duongdan = "http://192.168.1.20/shopping/admin/getDataDonHang.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id_dathang = 0;
                int idSP = 0;
                String tengiay = "";
                int giagiay = 0;
                int SL = 0;
                String hinhanh = "";
                String createdtime = "";
                String mota = "";
                int IdLoaiSP = 0;
                if (response != null && response.length() != 2){
                    try {
                        imgemptyDH.setVisibility(View.INVISIBLE);
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i=0;i<=jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id_dathang = jsonObject.getInt("id_dathang");
                            idSP = jsonObject.getInt("id_sanpham");
                            tengiay = jsonObject.getString("ten");
                            giagiay = jsonObject.getInt("gia");
                            SL = jsonObject.getInt("soluong");
                            hinhanh = jsonObject.getString("hinhanh");
                            status = jsonObject.getInt("tinhtrang");
                            createdtime = jsonObject.getString("createdtime");
                            mota = jsonObject.getString("mota");
                            IdLoaiSP = jsonObject.getInt("IDLoaiSP");
                            String tinhtrang = "";
                            switch (status){
                                case 0:
                                    tinhtrang = "Chờ xác nhận";
                                    break;
                                case 1:
                                    tinhtrang = "Đã xác nhận";
                                    break;
                                case 2:
                                    tinhtrang = "Đang giao hàng";
                                    break;
                                case 3:
                                    tinhtrang = "Đã giao hàng";
                                    break;
                                case 4:
                                    tinhtrang = "Hủy Đơn";
                                    break;
                            }
                            donHangArrayList.add(new DonHang(id_dathang,idSP,tengiay,giagiay,hinhanh,SL,tinhtrang,createdtime,mota,IdLoaiSP));
                            adapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    recyclerView.setVisibility(View.INVISIBLE);
                    imgemptyDH.setVisibility(View.VISIBLE);
                    CheckConnection.ShowToast_short(getContext(),"No");
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
                params.put("tinhtrang", String.valueOf(status));
                params.put("idUser",String.valueOf(MainActivity.informationUser.getIdUser()));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
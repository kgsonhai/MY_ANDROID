package com.example.cuahangonline.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangonline.Adapter.GiayAdapter;
import com.example.cuahangonline.Model.sanpham;
import com.example.cuahangonline.R;
import com.example.cuahangonline.activity.Danhmucsp_Activity;
import com.example.cuahangonline.ultil.CheckConnection;
import com.example.cuahangonline.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListSearchViewSP extends Fragment {
    private TextView txtTotalSP;
    private RecyclerView recyclerView;
    private ArrayList<sanpham> sanphamArrayList;
    private GiayAdapter adapter;
    private ProgressBar progressBar;
    private NestedScrollView nestedScrollView;
    int idDanhMucSP = 0;
    String tukhoa = "";
    int page = 1;
    String CountProduct = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_search_view_s_p, container, false);
        Anhxa(view);
        if (CheckConnection.haveNetworkConnect(getContext())){
            getCountProduct();
            getData(page);
            nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
                        page++;
                        progressBar.setVisibility(View.VISIBLE);
                        getData(page);
                    }
                }
            });
        }else{
            CheckConnection.ShowToast_short(getContext(),"Kiểm tra kết nối");
        }

        return view;
    }

    public void getData(int page){
        tukhoa = getArguments().getString("key_tukhoa");
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String duongdan = Server.duongdanSanPhamTimKiemSP+String.valueOf(page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String tengiay = "";
                int giagiay = 0;
                String hinhanh = "";
                String mota = "";
                int IdLoaisp = 0;
                if (response != null && response.length() != 2){
                    try {
                        progressBar.setVisibility(View.GONE);
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tengiay = jsonObject.getString("tensp");
                            giagiay = jsonObject.getInt("giasp");
                            hinhanh = jsonObject.getString("hinhanhsp");
                            mota = jsonObject.getString("motasp");
                            IdLoaisp = jsonObject.getInt("idLoaisp");

                            String motaRutGon = mota.substring(0,60)+"...";
                            sanphamArrayList.add(new sanpham(id,tengiay,giagiay,hinhanh,motaRutGon,IdLoaisp));
                            adapter.notifyDataSetChanged();
                            txtTotalSP.setText("Tìm được tất cả "+CountProduct+" sản phẩm");

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    progressBar.setVisibility(View.GONE);
                    txtTotalSP.setText(CountProduct);
                    CheckConnection.ShowToast_short(getContext(),"Đã hết sản phẩm");
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
                params.put("tukhoa",tukhoa);
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void getCountProduct(){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String duongdan = Server.duongdanCountSoSP;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null){
                    CountProduct = response.toString();
                }else{
                    CountProduct = "Không tìm thấy sản phẩm nào!!";
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
                params.put("tukhoa",tukhoa);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void Anhxa(View view) {
        txtTotalSP = view.findViewById(R.id.tongsoSPtimthay);
        progressBar = view.findViewById(R.id.progressBarDanhmucTheoTimkiem);
        recyclerView = view.findViewById(R.id.recyclerViewtheoTimkiem);
        nestedScrollView = view.findViewById(R.id.nestedscrollviewTimKiem);
        sanphamArrayList = new ArrayList<>();
        adapter = new GiayAdapter(getContext(),sanphamArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
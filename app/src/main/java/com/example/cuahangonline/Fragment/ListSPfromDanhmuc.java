package com.example.cuahangonline.Fragment;

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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangonline.Adapter.GiayAdapter;
import com.example.cuahangonline.Model.danhmuc;
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

public class ListSPfromDanhmuc extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<sanpham> sanphamArrayList;
    private GiayAdapter adapter;
    private ProgressBar progressBar;
    private NestedScrollView nestedScrollView;
    int idDanhMucSP = 0;
    int page = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_s_pfrom_danhmuc, container, false);
        Anhxa(view);
        if (CheckConnection.haveNetworkConnect(getContext())){
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
    public void Anhxa(View view){
        progressBar = view.findViewById(R.id.progressBarDanhmucTheoSP);
        recyclerView = view.findViewById(R.id.recyclerViewtheoDanhmuc);
        nestedScrollView = view.findViewById(R.id.nestedscrollviewSptheoDanhmuc);
        sanphamArrayList = new ArrayList<>();
        adapter = new GiayAdapter(getContext(),sanphamArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void getData(int page){
        idDanhMucSP = Danhmucsp_Activity.idDanhmuc;
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String duongdan = Server.duongdanSanPhamTheoDanhmucc+String.valueOf(page);
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
                        for (int i=0;i<=jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tengiay = jsonObject.getString("tensp");
                            giagiay = jsonObject.getInt("giasp");
                            hinhanh = jsonObject.getString("hinhanhsp");
                            mota = jsonObject.getString("motasp");
                            IdLoaisp = jsonObject.getInt("idLoaisp");

                            sanphamArrayList.add(new sanpham(id,tengiay,giagiay,"http://192.168.1.20/shopping/admin/"+hinhanh,mota,IdLoaisp));
                            adapter.notifyDataSetChanged();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    progressBar.setVisibility(View.GONE);
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
                params.put("iddanhmuc",String.valueOf(idDanhMucSP));
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }


}
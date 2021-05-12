package com.example.cuahangonline.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class ListSPforFilter extends Fragment {
    private TextView txtTotalSP;
    private RecyclerView recyclerView;
    private ArrayList<sanpham> sanphamArrayList;
    private GiayAdapter adapter;
    private ProgressBar progressBar;
    private NestedScrollView nestedScrollView;
    int page = 1;
    String CountProduct = "";
    String giaFilter = Danhmucsp_Activity.giaFilter;
    String mauFilter = Danhmucsp_Activity.mauFilter;
    String sizeFilter = Danhmucsp_Activity.sizeFilter;
    String saleFilter = Danhmucsp_Activity.saleFilter;
    String tinhtrangFilter = Danhmucsp_Activity.tinhtrangFilter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_s_pfor_filter, container, false);
        Anhxa(view);
        if (CheckConnection.haveNetworkConnect(getContext())){
            eventCatchFilter();
//            Log.d("giay",giaFilter);
//            Log.d("giay1",mauFilter);
//            Log.d("giay2",saleFilter);
//            Log.d("giay4",sizeFilter);
//            Log.d("giay3",tinhtrangFilter);
            CheckConnection.ShowToast_short(getContext(),giaFilter+"\n"+mauFilter+"\n"+sizeFilter+"\n");
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
    public void eventCatchFilter(){
        if (giaFilter!=null){
            switch (giaFilter){
                case "Lower 1 million":
                    giaFilter = "0 AND 1000000";
                    break;
                case "From 1-2 million":
                    giaFilter = "1000000 AND 2000000";
                    break;
                case "From 2-3 million":
                    giaFilter = "2000000 AND 3000000";
                    break;
                case "Higher 3 million":
                    giaFilter = "3000000 AND 10000000";
                    break;
                default:
                    giaFilter = "";
            }
        }
        if (mauFilter!=null){
            switch (mauFilter){
                case "Trắng":
                    mauFilter = "white";
                    break;
                case "Đen":
                    mauFilter = "black";
                    break;
                case "Tím":
                    mauFilter = "purple";
                    break;
                case "Đỏ":
                    mauFilter = "red";
                    break;
                case "Hồng":
                    mauFilter = "pink";
                    break;
                case "Xanh":
                    mauFilter = "blue";
                    break;
                default:
                    mauFilter = "";
            }
        }
        if (sizeFilter!=null){
            switch (sizeFilter){
                case "38":
                    sizeFilter = "38";
                    break;
                case "39":
                    sizeFilter = "39";
                    break;
                case "40":
                    sizeFilter = "40";
                    break;
                case "41":
                    sizeFilter = "41";
                    break;
                case "42":
                    sizeFilter = "42";
                    break;
                case "43":
                    sizeFilter = "43";
                    break;
                default:
                    sizeFilter = "";
            }
        }
        if (saleFilter!=null){
            switch (saleFilter){
                case "10%":
                    saleFilter = "10%";
                    break;
                case "25%":
                    saleFilter = "25%";
                    break;
                case "30%":
                    saleFilter = "30%";
                    break;
                default:
                    saleFilter = "";
            }
        }
        if (tinhtrangFilter!=null){
            switch (tinhtrangFilter){
                case "Còn hàng":
                    tinhtrangFilter = "1";
                    break;
                case "Hết hàng":
                    tinhtrangFilter = "0";
                    break;
                default:
                    tinhtrangFilter = "";
            }
        }
    }

    public void getData(int page){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String duongdan = Server.duongdanSanPhamLocSP+String.valueOf(page);
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

                            sanphamArrayList.add(new sanpham(id,tengiay,giagiay,hinhanh,mota,IdLoaisp));
                            adapter.notifyDataSetChanged();
                            txtTotalSP.setText("Tìm được tất cả "+CountProduct+" sản phẩm");

                        }
//                            Toast.makeText(getContext(),"OK nhá",Toast.LENGTH_LONG).show();
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
                params.put("giaSP",giaFilter);
                params.put("mauSP",mauFilter);
                params.put("sizeSP",sizeFilter);
                params.put("saleSP",saleFilter);
                params.put("tinhtrangSP",tinhtrangFilter);
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }



    private void Anhxa(View view) {
        txtTotalSP = view.findViewById(R.id.tongsoSPtimthayFilter);
        progressBar = view.findViewById(R.id.progressBarFilter);
        recyclerView = view.findViewById(R.id.recyclerViewFilter);
        nestedScrollView = view.findViewById(R.id.nestedscrollviewFilter);
        sanphamArrayList = new ArrayList<>();
        adapter = new GiayAdapter(getContext(),sanphamArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
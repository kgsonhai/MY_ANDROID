package com.example.cuahangonline.activity;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangonline.Adapter.GiayAdapter;
import com.example.cuahangonline.Adapter.itemDanhmuc_adapter;
import com.example.cuahangonline.Model.danhmuc;
import com.example.cuahangonline.Model.sanpham;
import com.example.cuahangonline.R;
import com.example.cuahangonline.ultil.CheckConnection;
import com.example.cuahangonline.ultil.Server;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Giay_Activity extends AppCompatActivity {
    Toolbar toolbarGiay;
    RecyclerView recyclerViewGiay;
    RecyclerView recyclerViewDanhMuc;
    itemDanhmuc_adapter itemDanhmucAdapter;
    GiayAdapter giayAdapter;
    ArrayList<sanpham> giayArrayList;
    ArrayList<danhmuc> danhmucArrayList;
    int iddt = 0;
    int idDanhmuc = 1;
    int page = 1;
    ProgressBar progressBar;
    SearchView searchView;
    NestedScrollView nestedScrollView;
    String giaFilter = "";
    String mauFilter = "";
    String sizeFilter = "";
    String saleFilter = "";
    String tinhtrangFilter = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giay);

        Anhxa();
        if (CheckConnection.haveNetworkConnect(getApplicationContext())){
            GetLoaiSP();
            ActionToolBar();
            GetDanhMucDB();
            GetData(page);
            nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
                        page++;
                        progressBar.setVisibility(View.VISIBLE);
                        GetData(page);
                    }
                }
            });
        }else{
            CheckConnection.ShowToast_short(getApplicationContext(),"Kiểm tra kết nối");
            finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_find,menu);
        getMenuInflater().inflate(R.menu.menu_filter,menu);

        searchView = (SearchView)menu.findItem(R.id.menuSearch).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getApplicationContext(),Danhmucsp_Activity.class);
                intent.putExtra("tukhoatimkiem",query);
                startActivity(intent);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuFilter){
            showDialogFilter();
        }
        return super.onOptionsItemSelected(item);
    }

    private void GetData(int page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.duongdanGiay+String.valueOf(page);
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
                            giayArrayList.add(new sanpham(id,tengiay,giagiay,"http://192.168.1.20/shopping/admin/"+hinhanh,mota+"..",IdLoaisp));
                            giayAdapter.notifyDataSetChanged();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    progressBar.setVisibility(View.GONE);
                    CheckConnection.ShowToast_short(getApplicationContext(),"Đã hết sản phẩm");
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
                params.put("idLoaisp",String.valueOf(iddt));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarGiay);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarGiay.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetLoaiSP() {
        iddt = getIntent().getIntExtra("IDloaisp",-1);
    }

    private void GetDanhMucDB(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanDanhMuc,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int idDM = 0;
                        String tenDM = "";
                        String hinhanhDM = "";
                        if (response != null){
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length();i++){
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    idDM = jsonObject.getInt("id");
                                    tenDM = jsonObject.getString("tenDM");
                                    hinhanhDM = jsonObject.getString("hinhanhDM");
                                    danhmucArrayList.add(new danhmuc(tenDM,hinhanhDM,idDM));
                                    itemDanhmucAdapter.notifyDataSetChanged();

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
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String, String>();
                param.put("idDanhmuc", String.valueOf(idDanhmuc));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void showDialogFilter(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_filter);

        Window window = dialog.getWindow();
        if (window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttr = window.getAttributes();
        windowAttr.gravity = Gravity.CENTER;
        window.setAttributes(windowAttr);

        dialog.show();

        ChipGroup chipGroup1 = dialog.findViewById(R.id.chipGroup1);
        ChipGroup chipGroup2 = dialog.findViewById(R.id.chipGroup2);
        ChipGroup chipGroup3 = dialog.findViewById(R.id.chipGroup3);
        ChipGroup chipGroup4 = dialog.findViewById(R.id.chipGroup4);
        ChipGroup chipGroup5 = dialog.findViewById(R.id.chipGroup5);

        chipGroup1.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                Chip chip = group.findViewById(checkedId);
                if (chip != null) {
                    giaFilter = chip.getText().toString();
//                    CheckConnection.ShowToast_short(getApplicationContext(),giaFilter);

                }
            }
        });
        chipGroup2.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                Chip chip = group.findViewById(checkedId);
                if (chip != null) {
                    mauFilter = chip.getText().toString();
//                    CheckConnection.ShowToast_short(getApplicationContext(),mauFilter);
                }
            }
        });
        chipGroup3.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                Chip chip = group.findViewById(checkedId);
                if (chip != null) {
                    sizeFilter = chip.getText().toString();
//                    CheckConnection.ShowToast_short(getApplicationContext(),sizeFilter);
                }
            }
        });
        chipGroup4.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                Chip chip = group.findViewById(checkedId);
                if (chip != null) {
                    saleFilter = chip.getText().toString();
                    CheckConnection.ShowToast_short(getApplicationContext(),saleFilter);
                }
            }
        });
        chipGroup5.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                Chip chip = group.findViewById(checkedId);
                if (chip != null) {
                    tinhtrangFilter = chip.getText().toString();
                    CheckConnection.ShowToast_short(getApplicationContext(),tinhtrangFilter);
                }
            }
        });
//

        Button btnApplyFilter = dialog.findViewById(R.id.btnapplyFilter);
        Button btnReset = dialog.findViewById(R.id.btnResetFilter);

        btnApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Danhmucsp_Activity.class);
                    intent.putExtra("giaFilter",giaFilter);
                    intent.putExtra("mauFilter",mauFilter);
                    intent.putExtra("sizeFilter",sizeFilter);
                    intent.putExtra("saleFilter",saleFilter);
                    intent.putExtra("tinhtrangFilter",tinhtrangFilter);
                if (giaFilter.isEmpty() && mauFilter.isEmpty() && sizeFilter.isEmpty() && saleFilter.isEmpty() && tinhtrangFilter.isEmpty()){
                    CheckConnection.ShowToast_short(getApplicationContext(),"Vui lòng chọn thông tin tìm kiếm");
                }else{
                    startActivity(intent);
                }


            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chipGroup1.clearCheck();
                chipGroup2.clearCheck();
                chipGroup3.clearCheck();
                chipGroup4.clearCheck();
                chipGroup5.clearCheck();
                giaFilter = "";
                mauFilter = "";
                sizeFilter = "";
                saleFilter = "";
                tinhtrangFilter = "";
            }
        });
    }



    private void Anhxa() {
        toolbarGiay = findViewById(R.id.toolbarMhGiay);
        nestedScrollView = findViewById(R.id.nestedscrollview);
        progressBar = findViewById(R.id.progressBarGiay);
        recyclerViewGiay = (RecyclerView) findViewById(R.id.recycleviewGiay);
        recyclerViewDanhMuc = (RecyclerView) findViewById(R.id.RecycleViewSelecDanhmuc);

        danhmucArrayList = new ArrayList<>();
        itemDanhmucAdapter = new itemDanhmuc_adapter(getApplicationContext(),danhmucArrayList);
        recyclerViewDanhMuc.setHasFixedSize(true);
        recyclerViewDanhMuc.setAdapter(itemDanhmucAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        recyclerViewDanhMuc.setLayoutManager(gridLayoutManager);
        recyclerViewDanhMuc.setFocusable(false);
        recyclerViewDanhMuc.setNestedScrollingEnabled(false);

        giayArrayList = new ArrayList<>();
        giayAdapter = new GiayAdapter(getApplicationContext(),giayArrayList);
        recyclerViewGiay.setHasFixedSize(true);
        recyclerViewGiay.setAdapter(giayAdapter);
        recyclerViewGiay.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewGiay.setFocusable(false);
        recyclerViewGiay.setNestedScrollingEnabled(false);

    }
    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()){
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

}
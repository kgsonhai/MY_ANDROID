package com.example.cuahangonline.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangonline.Adapter.loaiSPAdapter;
import com.example.cuahangonline.Adapter.sanphamAdapter;
import com.example.cuahangonline.Fragment.ChangeLogin_Fragment;
import com.example.cuahangonline.Fragment.formAccount_Fragment;
import com.example.cuahangonline.Model.Information_user;
import com.example.cuahangonline.Model.giohang;
import com.example.cuahangonline.Model.loaiSP;
import com.example.cuahangonline.Model.sanpham;
import com.example.cuahangonline.R;
import com.example.cuahangonline.ultil.CheckConnection;
import com.example.cuahangonline.ultil.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView ListViewLeft;
    DrawerLayout drawerLayout;
    ArrayList<loaiSP> loaiSPArrayList;
    loaiSPAdapter adapter;
    int id = 0;
    String tenloaiSP = "";
    String hinhanhloaiSP = "";
    ArrayList<sanpham> sanphamArrayList;
    com.example.cuahangonline.Adapter.sanphamAdapter sanphamAdapter;
    public static ArrayList<giohang> manggiohang;
    public static Information_user informationUser;
    public static boolean displayAccount = false;
    public static final String KEY_CONNECTIONS = "KEY_CONNECTIONS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (CheckConnection.haveNetworkConnect(getApplicationContext())){
            anhxa();
            actionBar();
            DisplayAccount();
            ActionViewFlipper();
            getDuLieuLoaiSP();
            getSanPhamMoiNhat();
            catchOnItemListView();
        }else{
            Toast.makeText(getApplicationContext(),"Kiểm tra lại kết nối",Toast.LENGTH_SHORT).show();
            finish();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart,menu);
        return super.onCreateOptionsMenu(menu);
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

    private void catchOnItemListView() {
        ListViewLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        if (CheckConnection.haveNetworkConnect(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_short(getApplicationContext(),"Hãy kiểm tra kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.haveNetworkConnect(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, Giay_Activity.class);
                            intent.putExtra("IDloaisp",loaiSPArrayList.get(position).getId());
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_short(getApplicationContext(),"Hãy kiểm tra kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (CheckConnection.haveNetworkConnect(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,Dep_Activity.class);
                            intent.putExtra("IDloaisp",loaiSPArrayList.get(position).getId());
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_short(getApplicationContext(),"Hãy kiểm tra kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (CheckConnection.haveNetworkConnect(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,Aoquan_activity.class);
                            intent.putExtra("IDloaisp",loaiSPArrayList.get(position).getId());
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_short(getApplicationContext(),"Hãy kiểm tra kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 5:
                        if (CheckConnection.haveNetworkConnect(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, LienHe_Activity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_short(getApplicationContext(),"Hãy kiểm tra kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 6:
                        if (CheckConnection.haveNetworkConnect(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,ThongTin_Activity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_short(getApplicationContext(),"Hãy kiểm tra kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void getSanPhamMoiNhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest  jsonArrayRequest = new JsonArrayRequest(Server.duongdanSanPhamMoiNhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    int ID = 0;
                    String tensp = "";
                    Integer giasp = 0;
                    String hinhanhSP = "";
                    String motaSP = "";
                    int IDLoaisp = 0;
                    for (int i=0; i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            tensp = jsonObject.getString("tensp");
                            giasp = jsonObject.getInt("giasp");
                            hinhanhSP = jsonObject.getString("hinhanhsp");
                            motaSP = jsonObject.getString("motasp");
                            IDLoaisp = jsonObject.getInt("idLoaisp");
                            sanphamArrayList.add(new sanpham(ID,tensp,giasp,hinhanhSP,motaSP,IDLoaisp));
                            sanphamAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void getDuLieuLoaiSP(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdanloaiSP, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    for (int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenloaiSP = jsonObject.getString("tenloaisp");
                            hinhanhloaiSP = jsonObject.getString("hinhanhloaisp");
                            loaiSPArrayList.add(new loaiSP(id,tenloaiSP,hinhanhloaiSP));
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    loaiSPArrayList.add(5,new loaiSP(0,"Liên Hệ","https://canaan.com.vn/wp-content/uploads/2020/09/icon-tu-van.png"));
                    loaiSPArrayList.add(6,new loaiSP(0,"Thông tin","https://d1nhio0ox7pgb.cloudfront.net/_img/g_collection_png/standard/512x512/information.png"));
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        CheckConnection.ShowToast_short(getApplicationContext(),error.toString());
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    private void actionBar(){
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void ActionViewFlipper(){
        ArrayList<String> mangquangcao =  new ArrayList<>();
        mangquangcao.add("http://creativeoverflow.net/wp-content/uploads/2017/04/shoe-11.jpeg");
        mangquangcao.add("https://pbs.twimg.com/media/Bhk-LGVCEAATWsI.jpg");
        mangquangcao.add("https://thumbs.dreamstime.com/z/as-mulheres-internacionais-dia-o-de-mar%C3%A7o-senhoras-picam-sapatas-do-salto-alto-66963610.jpg");
        mangquangcao.add("https://payload.cargocollective.com/1/1/44163/10680806/1_1600_c.jpg");
        for (int i=0;i<mangquangcao.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }

    private void DisplayAccount(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (displayAccount == false){
            ChangeLogin_Fragment fragmentA = new ChangeLogin_Fragment();
            fragmentTransaction.add(R.id.Fragment_display_Account,fragmentA);
        }else{
            displayAccount = true;
            formAccount_Fragment fragmentB = new formAccount_Fragment();
            fragmentTransaction.add(R.id.Fragment_display_Account,fragmentB);
        }
        fragmentTransaction.commit();
    }

    private void anhxa(){
        toolbar = (Toolbar) findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlippermhchinh);
        recyclerView = (RecyclerView) findViewById(R.id.RecycleView);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        ListViewLeft = (ListView) findViewById(R.id.ListViewLeftBar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawbleLayout);

        loaiSPArrayList = new ArrayList<>();
        loaiSPArrayList.add(0,new loaiSP(0,"Trang chính","https://d1nhio0ox7pgb.cloudfront.net/_img/g_collection_png/standard/512x512/home.png"));
        adapter = new loaiSPAdapter(loaiSPArrayList,getApplicationContext());
        ListViewLeft.setAdapter(adapter);

        sanphamArrayList = new ArrayList<>();
        sanphamAdapter = new sanphamAdapter(getApplicationContext(),sanphamArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(sanphamAdapter);
        if (manggiohang != null){

        }else{
            manggiohang = new ArrayList<>();
        }

    }
}
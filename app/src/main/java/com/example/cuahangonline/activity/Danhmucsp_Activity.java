package com.example.cuahangonline.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangonline.Adapter.GiayAdapter;
import com.example.cuahangonline.Adapter.itemDanhmuc_adapter;
import com.example.cuahangonline.Fragment.ChangeLogin_Fragment;
import com.example.cuahangonline.Fragment.ListSPforFilter;
import com.example.cuahangonline.Fragment.ListSPfromDanhmuc;
import com.example.cuahangonline.Fragment.ListSearchViewSP;
import com.example.cuahangonline.Fragment.formAccount_Fragment;
import com.example.cuahangonline.Model.danhmuc;
import com.example.cuahangonline.R;
import com.example.cuahangonline.ultil.CheckConnection;
import com.example.cuahangonline.ultil.Server;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Danhmucsp_Activity extends AppCompatActivity {
    Toolbar toolbarDanhmuc;
    RecyclerView recyclerViewDanhmuc;
    SearchView searchView;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    public static int idDanhmuc = 0;
    String tenDM = "";
    public static boolean searchData = false;
    public static boolean searchFilter = false;
    public static String tukhoa = "";
    public static String giaFilter = "";
    public static String mauFilter = "";
    public static String sizeFilter = "";
    public static String saleFilter = "";
    public static String tinhtrangFilter = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhmucsp_);

        Anhxa();
        if (CheckConnection.haveNetworkConnect(getApplicationContext())){
            ActionToolBar();
            DataPreActivity();
            getIntentFilter();
            DisplayAccount();

        }


    }
    private void DataPreActivity(){
        tukhoa = getIntent().getStringExtra("tukhoatimkiem");
        if (tukhoa == null){
            danhmuc danhmuc = (com.example.cuahangonline.Model.danhmuc) getIntent().getSerializableExtra("danhmucsp");
            if (danhmuc != null){
                idDanhmuc = danhmuc.getIdDM();
                tenDM = danhmuc.getTenDM();
            }
        }else{
            searchData = true;
        }

    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarDanhmuc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(tenDM);
        toolbarDanhmuc.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                searchData = false;
            }
        });
    }



    private void DisplayAccount(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (searchFilter == false){
            if (searchData == true){
                ListSearchViewSP fragmentB = new ListSearchViewSP();
                fragmentTransaction.add(R.id.fragmentDanhsachSP,fragmentB);
                Bundle bundle = new Bundle();
                bundle.putString("key_tukhoa",tukhoa);
                fragmentB.setArguments(bundle);
            }else{
                ListSPfromDanhmuc fragmentA = new ListSPfromDanhmuc();
                fragmentTransaction.add(R.id.fragmentDanhsachSP,fragmentA);
            }
        }else{
            // tao fragment filter o day
            ListSPforFilter fragmentC = new ListSPforFilter();
            fragmentTransaction.add(R.id.fragmentDanhsachSP,fragmentC);
        }
        fragmentTransaction.commit();
    }
    public void getIntentFilter(){
        giaFilter = getIntent().getStringExtra("giaFilter");
        mauFilter = getIntent().getStringExtra("mauFilter");
        sizeFilter = getIntent().getStringExtra("sizeFilter");
        saleFilter = getIntent().getStringExtra("saleFilter");
        tinhtrangFilter = getIntent().getStringExtra("tinhtrangFilter");

        if (giaFilter!=null || mauFilter!=null || sizeFilter!=null || saleFilter!=null || tinhtrangFilter!=null){
            searchFilter = true;
        }else{
            searchFilter = false;
        }
    }

    private void Anhxa() {
        toolbarDanhmuc = findViewById(R.id.toolbarMhDanhmucSP);
    }

    @Override
    public void onBackPressed() {
        searchFilter = false;
        super.onBackPressed();
    }
}
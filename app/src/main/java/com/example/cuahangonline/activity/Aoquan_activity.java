package com.example.cuahangonline.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.cuahangonline.R;
import com.example.cuahangonline.ultil.CheckConnection;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.navigation.NavigationView;

public class Aoquan_activity extends AppCompatActivity{
    Toolbar toolbarAoQuan;
    String giaFilter = "";
    String mauFilter = "";
    String sizeFilter = "";
    String saleFilter = "";
    String tinhtrangFilter = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aoquan_activity);

        Anhxa();
        ActionToolBar();

    }
    private void Anhxa() {
        toolbarAoQuan = findViewById(R.id.toolbarAoquan);
    }



    private void ActionToolBar() {
        setSupportActionBar(toolbarAoQuan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarAoQuan.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuFilter){
//            CheckConnection.ShowToast_short(getApplicationContext(),"ALO 123");
//            showDialogFilter();
        }
        return super.onOptionsItemSelected(item);
    }

//    private void showDialogFilter(){
//        final Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_filter);
//
//        Window window = dialog.getWindow();
//        if (window == null){
//            return;
//        }
//
//        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
//        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        WindowManager.LayoutParams windowAttr = window.getAttributes();
//        windowAttr.gravity = Gravity.CENTER;
//        window.setAttributes(windowAttr);
//
//        dialog.show();
//
//        ChipGroup chipGroup1 = dialog.findViewById(R.id.chipGroup1);
//        ChipGroup chipGroup2 = dialog.findViewById(R.id.chipGroup2);
//        ChipGroup chipGroup3 = dialog.findViewById(R.id.chipGroup3);
//        ChipGroup chipGroup4 = dialog.findViewById(R.id.chipGroup4);
//        ChipGroup chipGroup5 = dialog.findViewById(R.id.chipGroup5);
//
//        chipGroup1.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(ChipGroup group, int checkedId) {
//                    Chip chip = group.findViewById(checkedId);
//                    if (chip != null) {
//                        giaFilter = String.valueOf(chip.getId());
//                        CheckConnection.ShowToast_short(getApplicationContext(),giaFilter);
//
//                    }
//                }
//            });
//        chipGroup2.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(ChipGroup group, int checkedId) {
//                Chip chip = group.findViewById(checkedId);
//                if (chip != null) {
//                    mauFilter = String.valueOf(chip.getId());
//                    CheckConnection.ShowToast_short(getApplicationContext(),mauFilter);
//                }
//            }
//        });
//        chipGroup3.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(ChipGroup group, int checkedId) {
//                Chip chip = group.findViewById(checkedId);
//                if (chip != null) {
//                    sizeFilter = String.valueOf(chip.getId());
//                    CheckConnection.ShowToast_short(getApplicationContext(),sizeFilter);
//                }
//            }
//        });
//        chipGroup4.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(ChipGroup group, int checkedId) {
//                Chip chip = group.findViewById(checkedId);
//                if (chip != null) {
//                    saleFilter = String.valueOf(chip.getId());
//                    CheckConnection.ShowToast_short(getApplicationContext(),saleFilter);
//                }
//            }
//        });
//        chipGroup5.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(ChipGroup group, int checkedId) {
//                Chip chip = group.findViewById(checkedId);
//                if (chip != null) {
//                    tinhtrangFilter = String.valueOf(chip.getId());
//                    CheckConnection.ShowToast_short(getApplicationContext(),tinhtrangFilter);
//                }
//            }
//        });
////
//
//        Button btnApplyFilter = dialog.findViewById(R.id.btnapplyFilter);
//        Button btnReset = dialog.findViewById(R.id.btnResetFilter);
//        btnApplyFilter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),Danhmucsp_Activity.class);
//                if (!giaFilter.isEmpty()){
//                    intent.putExtra("giaFilter",giaFilter);
//                }
//                if (!mauFilter.isEmpty()){
//                    intent.putExtra("mauFilter",mauFilter);
//                }
//                if (!saleFilter.isEmpty()){
//                    intent.putExtra("saleFilter",saleFilter);
//
//                }
//                if (!tinhtrangFilter.isEmpty()){
//                    intent.putExtra("tinhtrangFilter",tinhtrangFilter);
//                }
//                if (!sizeFilter.isEmpty()){
//                    intent.putExtra("sizeFilter",sizeFilter);
//                }
//                startActivity(intent);
//
//            }
//        });
//
//
//
//    }

}

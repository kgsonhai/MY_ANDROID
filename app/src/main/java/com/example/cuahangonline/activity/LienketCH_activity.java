package com.example.cuahangonline.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.cuahangonline.R;

public class LienketCH_activity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbarLienKet;
    CardView cardViewWeb,cardViewPhanHoi,cardViewLienHe,cardViewDiachi,cardViewTintuc,cardViewSukien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lienket_c_h_activity);

        Anhxa();
        ActionToolBar();

    }

    private void Anhxa() {
        toolbarLienKet = findViewById(R.id.toolbarMhLienKet);
        cardViewWeb = findViewById(R.id.CardViewWebsite);
        cardViewLienHe = findViewById(R.id.CardViewLienHe);
        cardViewDiachi = findViewById(R.id.CardViewDiachi);
        cardViewPhanHoi = findViewById(R.id.CardViewPhanHoi);
        cardViewTintuc = findViewById(R.id.CardViewTinTuc);
        cardViewSukien = findViewById(R.id.CardViewSuKien);

        cardViewWeb.setOnClickListener(this);
        cardViewLienHe.setOnClickListener(this);
        cardViewDiachi.setOnClickListener(this);
        cardViewPhanHoi.setOnClickListener(this);
        cardViewTintuc.setOnClickListener(this);
        cardViewSukien.setOnClickListener(this);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarLienKet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLienKet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v == cardViewWeb){
            Intent intent = new Intent (Intent.ACTION_VIEW, Uri.parse("https://website-chgiay.000webhostapp.com/"));
            startActivity(intent);
        }else if (v == cardViewDiachi){
            Intent intent = new Intent(getApplicationContext(),LienHe_activity.class);
            startActivity(intent);
        }else if (v == cardViewLienHe){
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + "0826264430"));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }else if (v == cardViewPhanHoi){

        }
    }
}
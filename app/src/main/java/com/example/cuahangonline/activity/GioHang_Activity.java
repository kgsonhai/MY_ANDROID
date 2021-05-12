package com.example.cuahangonline.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.cuahangonline.Adapter.GioHangAdapter;
import com.example.cuahangonline.Model.Database;
import com.example.cuahangonline.Model.giohang;
import com.example.cuahangonline.R;
import com.example.cuahangonline.ultil.CheckConnection;

import java.text.DecimalFormat;

public class GioHang_Activity extends AppCompatActivity {
    ListView listViewGioHang;
    TextView txtThongBao;
    static TextView txtTongTien;
    Button btnThanhToan,btnTiepTucMua;
    Toolbar toolbar;
    GioHangAdapter gioHangAdapter;
    Database database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        Anhxa();
        ActionToolBar();
        CheckData();
        EventDisplayData();
        CatchOnItemListView();
        EventButtonMuavaThanhToan();
//        checkButtonClick();
//
        ShowCart();
//        database.queryData("DELETE FROM giohang");
//            SaveCart();


    }

    private void EventButtonMuavaThanhToan() {
        btnTiepTucMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.manggiohang.size()>0){
                    if(MainActivity.displayAccount == false){
                        Intent intent = new Intent(getApplicationContext(),ThongTin_Activity.class);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(getApplicationContext(), ThanhToan_Activity.class);
                        startActivity(intent);
                    }
                }else{
                    CheckConnection.ShowToast_short(getApplicationContext(),"Giỏ hàng của bạn chưa có sản phẩm nào");
                }
            }
        });
    }

    private void CatchOnItemListView() {
        listViewGioHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHang_Activity.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (MainActivity.manggiohang.size() <= 0){
                            txtThongBao.setVisibility(View.VISIBLE);
                        }else{
                            MainActivity.manggiohang.remove(position);
                            gioHangAdapter.notifyDataSetChanged();
                            EventDisplayData();
                            if (MainActivity.manggiohang.size() <= 0){
                                txtThongBao.setVisibility(View.VISIBLE);
                            }else{
                                txtThongBao.setVisibility(View.INVISIBLE);
                                gioHangAdapter.notifyDataSetChanged();
                                EventDisplayData();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gioHangAdapter.notifyDataSetChanged();
                        EventDisplayData();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void EventDisplayData() {
        int Tongtien = 0;
        for (int i = 0; i< MainActivity.manggiohang.size(); i++){
            Tongtien += MainActivity.manggiohang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTongTien.setText(decimalFormat.format(Tongtien)+" Đ");
    }

    private void CheckData() {
        if (MainActivity.manggiohang.size() <= 0){
            gioHangAdapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.VISIBLE);
            listViewGioHang.setVisibility(View.INVISIBLE);
        }else{
            txtThongBao.setVisibility(View.INVISIBLE);
            listViewGioHang.setVisibility(View.VISIBLE);
        }
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

//    private void checkButtonClick() {
//        btnThanhToan.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                StringBuffer responseText = new StringBuffer();
//                responseText.append("The following were selected...\n");
//
//                for(int i=0;i<MainActivity.manggiohang.size();i++){
//                    giohang giohang = MainActivity.manggiohang.get(i);
//                    if(giohang.isSeleted()){
//                        responseText.append("\n" + giohang.getTensp()+"\n");
//                    }
//                }
//                Toast.makeText(getApplicationContext(),
//                        responseText, Toast.LENGTH_LONG).show();
//
//            }
//        });
//
//    }
    private void SaveCart(){
        database.queryData("CREATE TABLE IF NOT EXISTS giohang(id INTEGER PRIMARY KEY AUTOINCREMENT, TenSP STRING, GiaSP INTEGER, Hinh STRING, soluong INTEGER )");
        if (MainActivity.manggiohang.size() > 0){
            for (int i=0;i<MainActivity.manggiohang.size();i++){
                String ten = MainActivity.manggiohang.get(i).tensp;
                int gia = MainActivity.manggiohang.get(i).giasp;
                String hinh = MainActivity.manggiohang.get(i).hinhsp;
                int soluong = MainActivity.manggiohang.get(i).soluongsp;
                database.queryData("INSERT INTO giohang VALUES(null,'"+ten+"','"+gia+"','"+hinh+"','"+soluong+"')");
                CheckConnection.ShowToast_short(getApplicationContext(),"Thanh cong");
            }
        }
    }

    private void ShowCart(){
        Cursor dataCart = database.getData("SELECT * FROM giohang");
        MainActivity.manggiohang.clear();
        while (dataCart.moveToNext()){
            int id = dataCart.getInt(0);
            String ten = dataCart.getString(1);
            int gia = dataCart.getInt(2);
            String hinh = dataCart.getString(3);
            int soluong = dataCart.getInt(4);
            MainActivity.manggiohang.add(new giohang(id,ten,gia,hinh,soluong));
            CheckConnection.ShowToast_short(getApplicationContext(),ten);
        }
        gioHangAdapter.notifyDataSetChanged();

    }


    private void Anhxa() {
        listViewGioHang = (ListView) findViewById(R.id.listViewGioHang);
        txtThongBao = (TextView) findViewById(R.id.txtThongBaoGioHang);
        txtTongTien = (TextView) findViewById(R.id.txtTongTienGioHang);
        btnThanhToan = (Button) findViewById(R.id.btnThanhToanGioHang);
        btnTiepTucMua = (Button) findViewById(R.id.btnTieptucMuahang);
        toolbar = (Toolbar) findViewById(R.id.toolbarGioHang);
        gioHangAdapter = new GioHangAdapter(MainActivity.manggiohang, GioHang_Activity.this,android.R.layout.simple_list_item_multiple_choice);
        listViewGioHang.setAdapter(gioHangAdapter);
        database = new Database(this,"giohang.sqlite",null,1);
    }
}
package com.example.cuahangonline.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangonline.Model.giohang;
import com.example.cuahangonline.R;
import com.example.cuahangonline.activity.Chitietsp_Activity;
import com.example.cuahangonline.activity.GioHang_Activity;
import com.example.cuahangonline.activity.MainActivity;
import com.example.cuahangonline.ultil.CheckConnection;
import com.example.cuahangonline.ultil.Server;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GioHangAdapter extends BaseAdapter {
    ArrayList<giohang> giohangArrayList;
    Context context;
    int layout;
    int CountProductDB = 0;

    public GioHangAdapter(ArrayList<giohang> giohangArrayList, Context context, int layout) {
        this.giohangArrayList = giohangArrayList;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return giohangArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return giohangArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void getCountProduct(int id){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String duongdan = Server.duongdanCountSLSP;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    CountProductDB = Integer.parseInt(response);
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
                params.put("idSP", String.valueOf(id));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public class ViewHolder {
        private TextView txtTen, txtGia;
        private ImageView imgGioHang;
        private Button btnCong, btnTru, btnSLsp;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_giohang, null);

            holder.txtTen = (TextView) convertView.findViewById(R.id.txtTenSPGioHang);
            holder.txtGia = (TextView) convertView.findViewById(R.id.txtGiaSPgioHang);
            holder.imgGioHang = (ImageView) convertView.findViewById(R.id.imgGioHang);
            holder.btnCong = (Button) convertView.findViewById(R.id.btnCongSoLuongsp);
            holder.btnTru = (Button) convertView.findViewById(R.id.btnTruSoLuongsp);
            holder.btnSLsp = (Button) convertView.findViewById(R.id.btnSoLuongsp);
            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        giohang giohang = giohangArrayList.get(position);
        holder.txtTen.setText(giohang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGia.setText(decimalFormat.format(giohang.getGiasp()) + "");
        Picasso.with(context).load(giohang.getHinhsp()).into(holder.imgGioHang);
        holder.btnSLsp.setText(giohang.getSoluongsp() + "");

        getCountProduct(MainActivity.manggiohang.get(position).getIdsp());

        int SL = Integer.parseInt(holder.btnSLsp.getText().toString());
        if (SL >= 10) {
            holder.btnCong.setVisibility(View.INVISIBLE);
            holder.btnTru.setVisibility(View.VISIBLE);
        } else if (SL <= 1) {
            holder.btnTru.setVisibility(View.INVISIBLE);
            holder.btnCong.setVisibility(View.VISIBLE);
        }else if (giohangArrayList.get(position).getSoluongsp() >= CountProductDB) {
            holder.btnCong.setVisibility(View.INVISIBLE);
            holder.btnTru.setVisibility(View.VISIBLE);
        } else {
            holder.btnTru.setVisibility(View.VISIBLE);
            holder.btnCong.setVisibility(View.VISIBLE);
        }



        ViewHolder finalHolder = holder;
        holder.btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int SLmoinhat = Integer.parseInt(finalHolder.btnSLsp.getText().toString()) + 1;
                int SLhientai = MainActivity.manggiohang.get(position).getSoluongsp();
                int Giahientai = MainActivity.manggiohang.get(position).getGiasp();
                MainActivity.manggiohang.get(position).setSoluongsp(SLmoinhat);
                int giamoinhat = (Giahientai * SLmoinhat) / SLhientai;
                MainActivity.manggiohang.get(position).setGiasp(giamoinhat);
                finalHolder.txtGia.setText(decimalFormat.format(giamoinhat) + " Đ");
                GioHang_Activity.EventDisplayData();
                getCountProduct(MainActivity.manggiohang.get(position).getIdsp());
                if (SLmoinhat > 9) {
                    finalHolder.btnCong.setVisibility(View.INVISIBLE);
                    finalHolder.btnTru.setVisibility(View.VISIBLE);
                    finalHolder.btnSLsp.setText(String.valueOf(SLmoinhat));
                }else if(SLmoinhat >= CountProductDB){
                    finalHolder.btnCong.setVisibility(View.INVISIBLE);
                    finalHolder.btnTru.setVisibility(View.VISIBLE);
                    finalHolder.btnSLsp.setText(String.valueOf(SLmoinhat));
                }else {
                    finalHolder.btnCong.setVisibility(View.VISIBLE);
                    finalHolder.btnTru.setVisibility(View.VISIBLE);
                    finalHolder.btnSLsp.setText(String.valueOf(SLmoinhat));
                }

            }
        });
        holder.btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int SLmoinhat = Integer.parseInt(finalHolder.btnSLsp.getText().toString()) - 1;
                int SLhientai = MainActivity.manggiohang.get(position).getSoluongsp();
                int Giahientai = MainActivity.manggiohang.get(position).getGiasp();
                MainActivity.manggiohang.get(position).setSoluongsp(SLmoinhat);
                int giamoinhat = (Giahientai * SLmoinhat) / SLhientai;
                MainActivity.manggiohang.get(position).setGiasp(giamoinhat);
                finalHolder.txtGia.setText(decimalFormat.format(giamoinhat) + " Đ");
                GioHang_Activity.EventDisplayData();
                if (SLmoinhat <= 1 ) {
                    finalHolder.btnTru.setVisibility(View.INVISIBLE);
                    finalHolder.btnCong.setVisibility(View.VISIBLE);
                    finalHolder.btnSLsp.setText(String.valueOf(SLmoinhat));
                } else {
                    finalHolder.btnCong.setVisibility(View.VISIBLE);
                    finalHolder.btnTru.setVisibility(View.VISIBLE);
                    finalHolder.btnSLsp.setText(String.valueOf(SLmoinhat));
                }
            }
        });
        return convertView;
    }

}

package com.example.cuahangonline.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangonline.Model.DonHang;
import com.example.cuahangonline.Model.sanpham;
import com.example.cuahangonline.R;
import com.example.cuahangonline.activity.Chitietsp_Activity;
import com.example.cuahangonline.activity.Danhmucsp_Activity;
import com.example.cuahangonline.activity.DonHang_activity;
import com.example.cuahangonline.activity.MainActivity;
import com.example.cuahangonline.ultil.CheckConnection;
import com.example.cuahangonline.ultil.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DonHang_Adapter extends RecyclerView.Adapter<DonHang_Adapter.ViewHolder> {

    ArrayList<DonHang> donHangArrayList;
    Context context;
    int tongtien = 0;

    public DonHang_Adapter(ArrayList<DonHang> donHangArrayList, Context context) {
        this.donHangArrayList = donHangArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public DonHang_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_donhang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonHang_Adapter.ViewHolder holder, int position) {
        holder.txtTen.setText(donHangArrayList.get(position).getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGia.setText("Gi?? : " + decimalFormat.format(donHangArrayList.get(position).getGiasp()) + "??");
        holder.txtSL.setText("S??? l?????ng : " + donHangArrayList.get(position).getSoluongsp() + "");
        Picasso.with(context).load("https://website-chgiay.000webhostapp.com/admin/" + donHangArrayList.get(position).getHinhsp()).into(holder.imgHinh);
        holder.txttime.setText(donHangArrayList.get(position).getCreatedtime());
        holder.txtTinhTrang.setText(donHangArrayList.get(position).getTinhtrang());
        tongtien = donHangArrayList.get(position).getGiasp() * donHangArrayList.get(position).getSoluongsp();
        holder.txtTongTien.setText(decimalFormat.format(tongtien) + "??");

//                Log.d("vitri",DonHang_activity.vitri+"");


    }

    @Override
    public int getItemCount() {
        return donHangArrayList.size();
    }

    private void EditDataStatus(int idDH, int changeStatus) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanUpdateStatus, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    Log.d("status", response.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id_DH", String.valueOf(idDH));
                params.put("changeStatus", String.valueOf(changeStatus));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHinh;
        TextView txttime, txtTinhTrang, txtTen, txtSL, txtGia, txtTongTien;
        Button btnHuyDon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txttime = itemView.findViewById(R.id.item_dh_createdtime);
            txtTen = itemView.findViewById(R.id.item_dh_ten);
            txtTinhTrang = itemView.findViewById(R.id.item_dh_tinhtrang);
            txtGia = itemView.findViewById(R.id.item_dh_gia);
            txtSL = itemView.findViewById(R.id.item_dh_SL);
            txtTongTien = itemView.findViewById(R.id.item_dh_Tongtien);
            imgHinh = itemView.findViewById(R.id.item_dh_image);
            btnHuyDon = itemView.findViewById(R.id.item_dh_button);

            int vitri = DonHang_activity.vitri;
            switch (vitri) {
                case 0:
                    btnHuyDon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditDataStatus(donHangArrayList.get(getPosition()).getId_dathang(), 4);
                            Intent intent = new Intent(context, DonHang_activity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            CheckConnection.ShowToast_short(context, "H???y ????n th??nh c??ng");

                        }
                    });
                    break;
                case 1:
                    btnHuyDon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditDataStatus(donHangArrayList.get(getPosition()).getId_dathang(), 4);
                            Intent intent = new Intent(context, DonHang_activity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            CheckConnection.ShowToast_short(context, "H???y ????n h??ng th??nh c??ng");

                        }
                    });
                    break;
                case 2:
                    btnHuyDon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CheckConnection.ShowToast_short(context,"S???n ph???m n??y ??ang ???????c giao kh??ng th??? h???y!!");
                        }
                    });
                    break;

            }
        }
    }
}

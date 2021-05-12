package com.example.cuahangonline.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuahangonline.Model.sanpham;
import com.example.cuahangonline.R;
import com.example.cuahangonline.activity.Chitietsp_Activity;
import com.example.cuahangonline.ultil.CheckConnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class sanphamAdapter extends RecyclerView.Adapter<sanphamAdapter.ViewHolder> {
    private Context context;
    private ArrayList<sanpham> sanphamArrayList;

    public sanphamAdapter(Context context, ArrayList<sanpham> sanphamArrayList) {
        this.context = context;
        this.sanphamArrayList = sanphamArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_sanphammoinhat,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.with(context).load(sanphamArrayList.get(position).getHinhanhsp()).into(holder.imgHinhAnhSP);
        holder.txtTenSP.setText(sanphamArrayList.get(position).getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiaSP.setText("Giá : "+ decimalFormat.format(sanphamArrayList.get(position).getGiasp()));
    }

    @Override
    public int getItemCount() {
        return sanphamArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgHinhAnhSP;
        public TextView txtTenSP,txtgiaSP;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhAnhSP = (ImageView) itemView.findViewById(R.id.imageViewSanPham);
            txtTenSP = (TextView) itemView.findViewById(R.id.textviewTenSP);
            txtgiaSP = (TextView) itemView.findViewById(R.id.txtGiaSP);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Chitietsp_Activity.class);
                    intent.putExtra("thongtinsanpham",sanphamArrayList.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });
        }
    }
}

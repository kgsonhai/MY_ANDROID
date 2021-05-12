package com.example.cuahangonline.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuahangonline.Model.danhmuc;
import com.example.cuahangonline.R;
import com.example.cuahangonline.activity.Danhmucsp_Activity;
import com.example.cuahangonline.activity.MainActivity;
import com.example.cuahangonline.activity.ThanhToan_Activity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class itemDanhmuc_adapter extends RecyclerView.Adapter<itemDanhmuc_adapter.ViewHolder> {
    private Context context;
    private ArrayList<danhmuc> danhmucArrayList;

    public itemDanhmuc_adapter(Context context, ArrayList<danhmuc> danhmucArrayList) {
        this.context = context;
        this.danhmucArrayList = danhmucArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_danhmuc,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        danhmuc danhmuc = danhmucArrayList.get(position);
        holder.txtItemDanhMuc.setText(danhmuc.getTenDM());
        Picasso.with(context).load(danhmuc.getImgDM()).into(holder.imgDanhMuc);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Danhmucsp_Activity.searchData = false;
                Intent intent = new Intent(context,Danhmucsp_Activity.class);
                intent.putExtra("danhmucsp",danhmuc);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return danhmucArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtItemDanhMuc;
        private ImageView imgDanhMuc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtItemDanhMuc = itemView.findViewById(R.id.txtItemTenDanhMuc);
            imgDanhMuc = itemView.findViewById(R.id.imgItemDanhmuc);
        }

    }
}

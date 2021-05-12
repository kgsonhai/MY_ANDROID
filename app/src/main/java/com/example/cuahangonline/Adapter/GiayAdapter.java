package com.example.cuahangonline.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuahangonline.Model.sanpham;
import com.example.cuahangonline.R;
import com.example.cuahangonline.activity.Chitietsp_Activity;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;

public class GiayAdapter extends RecyclerView.Adapter<GiayAdapter.ViewHolder> implements Filterable {
    Context context;
    ArrayList<sanpham> sanphamArrayList;
    ArrayList<sanpham> sanphamAll;

    public GiayAdapter(Context context, ArrayList<sanpham> sanphamArrayList) {
        this.context = context;
        this.sanphamArrayList = sanphamArrayList;
        sanphamAll = new ArrayList<>(sanphamArrayList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_giay,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTen.setText(sanphamArrayList.get(position).getTensp());
        holder.txtGia.setText(sanphamArrayList.get(position).getGiasp()+"");
        holder.txtMota.setText(sanphamArrayList.get(position).getMotaSP());
        Picasso.with(context).load(sanphamArrayList.get(position).getHinhanhsp()).into(holder.imgHinh);

    }

    @Override
    public int getItemCount() {
        return sanphamArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter  = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<sanpham> sparraylist = new ArrayList<>();
            if (constraint == null || constraint.length() == 0){
                sparraylist.addAll(sanphamAll);
            }else{
                String filterTim = constraint.toString().toLowerCase().trim();
                for (sanpham item: sanphamAll){
                    if (item.getTensp().toLowerCase().contains(filterTim)){
                        sparraylist.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = sparraylist;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            sanphamArrayList.clear();
            sanphamArrayList.addAll((Collection<? extends sanpham>) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTen,txtGia,txtMota;
        ImageView imgHinh;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txtItemTenGiay);
            txtGia = itemView.findViewById(R.id.txtItemGiaGiay);
            txtMota = itemView.findViewById(R.id.txtItemMotaGiay);
            imgHinh = itemView.findViewById(R.id.imageItemGiay);

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

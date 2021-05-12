package com.example.cuahangonline.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cuahangonline.Model.sanpham;
import com.example.cuahangonline.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DepAdapter extends BaseAdapter {
    ArrayList<sanpham> depArrayList;
    Context context;

    public DepAdapter(ArrayList<sanpham> depArrayList, Context context) {
        this.depArrayList = depArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return depArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return depArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder{
        private TextView txtTen,txtGia,txtMota;
        private ImageView imgHinh;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_dep,null);

            //ánh xạ view
            holder.txtTen = (TextView) convertView.findViewById(R.id.txtItemTenDep);
            holder.txtGia = (TextView) convertView.findViewById(R.id.txtItemGiaDep);
            holder.txtMota = (TextView) convertView.findViewById(R.id.txtItemMotaDep);
            holder.imgHinh = (ImageView) convertView.findViewById(R.id.imageItemDep);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        //gán giá trị
        sanpham sanpham = (com.example.cuahangonline.Model.sanpham) getItem(position);
        holder.txtTen.setText(sanpham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGia.setText("Giá : "+decimalFormat.format(sanpham.getGiasp())+" Đ");
        holder.txtMota.setMaxLines(2);
        holder.txtMota.setEllipsize(TextUtils.TruncateAt.END);
        holder.txtMota.setText(sanpham.getMotaSP());
        Picasso.with(context).load(sanpham.getHinhanhsp()).into(holder.imgHinh);

        return convertView;

    }
}

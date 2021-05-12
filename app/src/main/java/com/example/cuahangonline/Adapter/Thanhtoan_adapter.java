package com.example.cuahangonline.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cuahangonline.Model.giohang;
import com.example.cuahangonline.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Thanhtoan_adapter extends BaseAdapter {
    ArrayList<giohang> giohangArrayList;
    Context context;
    int pos = 1;

    public Thanhtoan_adapter(ArrayList<giohang> giohangArrayList, Context context) {
        this.giohangArrayList = giohangArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return giohangArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public class ViewHolder{
        TextView txtTen,txtSLsp,txtGia,txtPosition;
        ImageView imgThanhToan;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_payment, null);

            holder.txtPosition = (TextView) convertView.findViewById(R.id.txtPositionSP);
            holder.txtTen = (TextView) convertView.findViewById(R.id.txtTenMhThanhtoan);
            holder.txtGia = (TextView) convertView.findViewById(R.id.txtGiaSPThanhtoan);
            holder.imgThanhToan = (ImageView) convertView.findViewById(R.id.imgThanhtoan);
            holder.txtSLsp =  convertView.findViewById(R.id.btnSoLuongThanhToan);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        giohang giohang = giohangArrayList.get(position);
        holder.txtPosition.setText(""+pos++);
        holder.txtTen.setText(giohang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGia.setText(decimalFormat.format(giohang.getGiasp())+"");
        Picasso.with(context).load(giohang.getHinhsp()).into(holder.imgThanhToan);
        holder.txtSLsp.setText("Số lượng: "+giohang.getSoluongsp()+"");


        return convertView;
    }
}


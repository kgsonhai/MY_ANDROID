package com.example.cuahangonline.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cuahangonline.Model.loaiSP;
import com.example.cuahangonline.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class loaiSPAdapter extends BaseAdapter {
    ArrayList<loaiSP> loaiSPArrayList;
    Context context;

    public loaiSPAdapter(ArrayList<loaiSP> loaiSPArrayList, Context context) {
        this.loaiSPArrayList = loaiSPArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return loaiSPArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return loaiSPArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        private TextView txtTen;
        private ImageView imageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_listview_loaisp,null);

            //ánh xạ view
            holder.txtTen = (TextView) convertView.findViewById(R.id.tenLoaiSP);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageLoaiSP);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        //gán giá trị
        loaiSP loaiSP = loaiSPArrayList.get(position);
        holder.txtTen.setText(loaiSP.getTenLoaiSP());
        Picasso.with(context).load(loaiSP.getHinhanh()).into(holder.imageView);

        return convertView;
    }
}

package com.example.cuahangonline.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuahangonline.Model.comment;
import com.example.cuahangonline.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class commentAdapter extends RecyclerView.Adapter<commentAdapter.ViewHolder> {
    Context context;
    ArrayList<comment> commentArrayList;

    public commentAdapter(Context context, ArrayList<comment> commentArrayList) {
        this.context = context;
        this.commentArrayList = commentArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        comment comment = commentArrayList.get(position);
        Picasso.with(context).load(comment.getAnh()).into(holder.imganh);
        holder.txtTen.setText(comment.getTen());
        holder.txtContent.setText(comment.getNoidung());
        holder.txtTime.setText(comment.getNgaythang());
        holder.danhgia.setRating(comment.getDanhgia());

    }

    @Override
    public int getItemCount() {
        return commentArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imganh;
        TextView txtTen,txtTime,txtContent;
        RatingBar danhgia;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imganh = itemView.findViewById(R.id.img_userComment);
            txtTen = itemView.findViewById(R.id.txv_nameUser_comment);
            txtContent = itemView.findViewById(R.id.txv_content_comment);
            txtTime = itemView.findViewById(R.id.txv_createdtime_comment);
            danhgia = itemView.findViewById(R.id.danhgiaSP);
        }
    }
}

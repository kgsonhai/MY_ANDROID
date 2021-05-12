package com.example.cuahangonline.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.cuahangonline.R;
import com.example.cuahangonline.activity.Dangky_Activity;
import com.example.cuahangonline.activity.ThongTin_Activity;


public class ChangeLogin_Fragment extends Fragment {
    Button btnDangNhap,btnDangKy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_login_, container, false);
        btnDangNhap = (Button) view.findViewById(R.id.btnDangNhap);
        btnDangKy = (Button) view.findViewById(R.id.btnDangKyAccount);

        EventDangNhap();
        EventDangKy();

        return view;
    }
    private void EventDangNhap(){
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ThongTin_Activity.class);
                startActivity(intent);
            }
        });
    }
    private void EventDangKy(){
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Dangky_Activity.class);
                startActivity(intent);
            }
        });
    }
}
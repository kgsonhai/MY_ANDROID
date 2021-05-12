package com.example.cuahangonline.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.cuahangonline.R;
import com.example.cuahangonline.activity.MainActivity;

public class formAccount_Fragment extends Fragment {
    ImageView imgUser;
    TextView txtTen,txtUsername,txtphone;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form_account, container, false);
        imgUser = (ImageView) view.findViewById(R.id.imageMenuUser);
        txtTen = (TextView) view.findViewById(R.id.txtMenuHoten);
        txtUsername = (TextView) view.findViewById(R.id.txtMenuUsername);
        txtphone = (TextView) view.findViewById(R.id.txtMenuPhone);

        txtTen.setText(MainActivity.informationUser.getTen());
        txtUsername.setText(MainActivity.informationUser.getUsername());
        txtphone.setText("0"+MainActivity.informationUser.getSdt());

        return view;
    }
}
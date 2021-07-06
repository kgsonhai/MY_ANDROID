package com.example.cuahangonline.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.cuahangonline.R;
import com.example.cuahangonline.activity.DonHang_activity;
import com.example.cuahangonline.activity.InformationAC_activity;
import com.example.cuahangonline.activity.MainActivity;
import com.example.cuahangonline.ultil.CheckConnection;
import com.squareup.picasso.Picasso;

public class formAccount_Fragment extends Fragment{
    ImageButton imgUser;
    TextView txtTen,txtUsername;
    Spinner spinner;
    int optionID = 10;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form_account, container, false);
        imgUser = (ImageButton) view.findViewById(R.id.imageMenuUser);
        txtTen = (TextView) view.findViewById(R.id.txtMenuHoten);
        txtUsername = (TextView) view.findViewById(R.id.txtMenuUsername);
        spinner = (Spinner) view.findViewById(R.id.SelInforAC);

            if (MainActivity.displayAccount ==true){
            txtTen.setText(MainActivity.informationUser.getTen());
            txtUsername.setText(MainActivity.informationUser.getDiachi());
            String urlHing = "http://192.168.1.20/shopping/admin/"+ MainActivity.informationUser.getAnhdaidien();
            Picasso.with(getActivity()).load(urlHing).into(imgUser);}

        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), InformationAC_activity.class);
                startActivity(intent);
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.informationAC, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    optionID = (int) id;
                if (optionID == 1) {
                    Intent intent = new Intent(getContext(), InformationAC_activity.class);
                    startActivity(intent);
                } else if(optionID == 2){
                    Intent intent = new Intent(getContext(), DonHang_activity.class);
                    startActivity(intent);
                } else if (optionID == 3 ){
                    MainActivity.displayAccount = false;
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

}
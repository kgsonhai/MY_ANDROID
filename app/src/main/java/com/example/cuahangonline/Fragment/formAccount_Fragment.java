package com.example.cuahangonline.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.cuahangonline.R;
import com.example.cuahangonline.activity.InformationAC_activity;
import com.example.cuahangonline.activity.MainActivity;
import com.example.cuahangonline.ultil.CheckConnection;
import com.squareup.picasso.Picasso;

public class formAccount_Fragment extends Fragment{
    ImageView imgUser;
    TextView txtTen,txtUsername;
    Spinner spinner;
    int optionID = 2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form_account, container, false);
        imgUser = (ImageView) view.findViewById(R.id.imageMenuUser);
        txtTen = (TextView) view.findViewById(R.id.txtMenuHoten);
        txtUsername = (TextView) view.findViewById(R.id.txtMenuUsername);
        spinner = (Spinner) view.findViewById(R.id.SelInforAC);

        txtTen.setText(MainActivity.informationUser.getTen());
        txtUsername.setText(MainActivity.informationUser.getDiachi());
        String urlHing = "https://website-chgiay.000webhostapp.com/admin/"+ MainActivity.informationUser.getAnhdaidien();
        Picasso.with(getContext()).load(urlHing).into(imgUser);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.informationAC, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                optionID = (int) id;
                if (optionID == 0) {
                    Intent intent = new Intent(getContext(), InformationAC_activity.class);
                    startActivity(intent);
                }else if (optionID == 1){
                    CheckConnection.ShowToast_short(getContext(),"OK");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

}
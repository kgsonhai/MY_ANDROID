package com.example.cuahangonline.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangonline.R;
import com.example.cuahangonline.ultil.APIUntils;
import com.example.cuahangonline.ultil.CheckConnection;
import com.example.cuahangonline.ultil.DataClient;
import com.example.cuahangonline.ultil.Server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;


public class InformationAC_activity extends AppCompatActivity implements View.OnClickListener {
    TextView txtten, txtusername, txtemail, txtsdt, txtdiachi, txtpass;
    ImageButton imageButton;
    Button btnUpdate;
    Toolbar toolbar;
    String editName = "";
    int positonitem = 0;
    private static final int PICK_IMAGE_REQUEST = 123;
    private String realPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_a_c_activity);

        Anhxa();
        ActionToolBar();

    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void UpdateUser() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit_user);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttr = window.getAttributes();
        windowAttr.gravity = Gravity.CENTER;
        window.setAttributes(windowAttr);

        dialog.show();

        EditText txtEdit;
        Button btnSave, btnCancel;

        txtEdit = dialog.findViewById(R.id.dialogNeedEdit);
        btnSave = dialog.findViewById(R.id.btnSavedialog);
        btnCancel = dialog.findViewById(R.id.btnHuydialog);

        txtEdit.setText(editName);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (positonitem) {
                    case 1:
                        txtten.setText(txtEdit.getText().toString().trim());
                        break;
                    case 2:
                        txtusername.setText(txtEdit.getText().toString().trim());
                        break;
                    case 3:
                        txtemail.setText(txtEdit.getText().toString().trim());
                        break;
                    case 4:
                        txtsdt.setText(txtEdit.getText().toString().trim());
                        break;
                    case 5:
                        txtdiachi.setText(txtEdit.getText().toString().trim());
                        break;
                }
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toolbarInforAC);
        txtten = findViewById(R.id.txtACname);
        txtusername = findViewById(R.id.txtACusername);
        txtemail = findViewById(R.id.txtACemail);
        txtsdt = findViewById(R.id.txtACsdt);
        txtdiachi = findViewById(R.id.txtACdiachi);
//        txtpass = findViewById(R.id.txtACname);
        imageButton = findViewById(R.id.imgACAvata);
        btnUpdate = findViewById(R.id.btnUpdateInforAC);

        txtten.setText(MainActivity.informationUser.getTen());
        txtusername.setText(MainActivity.informationUser.getUsername());
        txtemail.setText(MainActivity.informationUser.getEmail());
        txtsdt.setText(MainActivity.informationUser.getSdt());
        txtdiachi.setText(MainActivity.informationUser.getDiachi());
//        Picasso.with(getApplicationContext()).load(MainActivity.informationUser.getAnhdaidien()).into(imageButton);

        imageButton.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        txtten.setOnClickListener(this);
        txtusername.setOnClickListener(this);
        txtemail.setOnClickListener(this);
        txtsdt.setOnClickListener(this);
        txtdiachi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtACname:
                editName = txtten.getText().toString();
                positonitem = 1;
                UpdateUser();
                break;
            case R.id.txtACusername:
                editName = txtusername.getText().toString();
                positonitem = 2;
                UpdateUser();
                break;
            case R.id.txtACemail:
                editName = txtemail.getText().toString();
                positonitem = 3;
                UpdateUser();
                break;
            case R.id.txtACsdt:
                editName = txtsdt.getText().toString();
                positonitem = 4;
                UpdateUser();
                break;
            case R.id.txtACdiachi:
                editName = txtdiachi.getText().toString();
                positonitem = 5;
                UpdateUser();
                break;
            case R.id.imgACAvata:
                    showFileChooser();
                break;
            case R.id.btnUpdateInforAC:
                UpdateData();
                break;

        }
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    private void UpdateData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.duongdanEditInforAC;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("1")) {
                    CheckConnection.ShowToast_short(getApplicationContext(), "Cập nhật thông tin thành công");
                    MainActivity.displayAccount = false;
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("idUser", String.valueOf(MainActivity.informationUser.getIdUser()));
                params.put("ten", txtten.getText().toString().trim());
                params.put("tendangnhap", txtusername.getText().toString().trim());
                params.put("email", txtemail.getText().toString().trim());
                params.put("sdt", txtsdt.getText().toString().trim());
                params.put("diachi", txtdiachi.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    public void UploadHinh(){
        File file = new File(realPath);
        String file_path = file.getAbsolutePath();
        String[] mangtenfile = file_path.split("\\.");

        file_path = mangtenfile[0] + System.currentTimeMillis() + "." + mangtenfile[1];
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("image",file_path,requestBody);
        DataClient dataClient =  APIUntils.getData();
        Call<String> callback = dataClient.UploadIMG(body);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                if(response != null){
                    String message = response.body();
                    Log.d("aaa",message);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("aaa",t.getMessage());
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            realPath = getRealPathFromURI(uri);
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                UploadHinh();
                imageButton.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        }


    }
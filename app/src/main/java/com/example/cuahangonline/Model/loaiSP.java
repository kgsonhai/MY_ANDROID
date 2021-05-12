package com.example.cuahangonline.Model;

public class loaiSP {
    public int id;
    public String tenLoaiSP;
    public String hinhanh;

    public loaiSP(int id, String tenLoaiSP, String hinhanh) {
        this.id = id;
        this.tenLoaiSP = tenLoaiSP;
        this.hinhanh = hinhanh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenLoaiSP() {
        return tenLoaiSP;
    }

    public void setTenLoaiSP(String tenLoaiSP) {
        this.tenLoaiSP = tenLoaiSP;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}


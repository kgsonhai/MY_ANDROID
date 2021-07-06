package com.example.cuahangonline.Model;

public class comment {
    private int id;
    private String anh;
    private String ten;
    private float danhgia;
    private String noidung;
    private String ngaythang;

    public comment(int id,String anh,String ten,float danhgia,String noidung, String ngaythang) {
        this.id = id;
        this.anh = anh;
        this.ten = ten;
        this.danhgia = danhgia;
        this.noidung = noidung;
        this.ngaythang = ngaythang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public float getDanhgia() {
        return danhgia;
    }

    public void setDanhgia(float danhgia) {
        this.danhgia = danhgia;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getNgaythang() {
        return ngaythang;
    }

    public void setNgaythang(String ngaythang) {
        this.ngaythang = ngaythang;
    }
}

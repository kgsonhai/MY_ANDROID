package com.example.cuahangonline.Model;

import java.io.Serializable;

public class DonHang implements Serializable {
    private int id_dathang;
    private int idsp;
    private String tensp;
    private int giasp;
    private String hinhsp;
    private int soluongsp;
    private String tinhtrang;
    private String createdtime;
    private String chitietSP;
    private int IdLoaiSP;

    public DonHang(int id_dathang,int idsp,String tensp, int giasp, String hinhsp, int soluongsp, String tinhtrang,String createdtime,String chitietSP,int IdLoaiSP) {
        this.id_dathang = id_dathang;
        this.idsp = idsp;
        this.tensp = tensp;
        this.giasp = giasp;
        this.hinhsp = hinhsp;
        this.soluongsp = soluongsp;
        this.tinhtrang = tinhtrang;
        this.createdtime = createdtime;
        this.chitietSP = chitietSP;
        this.IdLoaiSP = IdLoaiSP;
    }

    public int getId_dathang() {
        return id_dathang;
    }

    public void setId_dathang(int id_dathang) {
        this.id_dathang = id_dathang;
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public String getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(String createdtime) {
        this.createdtime = createdtime;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public int getGiasp() {
        return giasp;
    }

    public void setGiasp(int giasp) {
        this.giasp = giasp;
    }

    public String getHinhsp() {
        return hinhsp;
    }

    public void setHinhsp(String hinhsp) {
        this.hinhsp = hinhsp;
    }

    public int getSoluongsp() {
        return soluongsp;
    }

    public void setSoluongsp(int soluongsp) {
        this.soluongsp = soluongsp;
    }

    public String getTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(String tinhtrang) {
        this.tinhtrang = tinhtrang;
    }

    public String getChitietSP() {
        return chitietSP;
    }

    public void setChitietSP(String chitietSP) {
        this.chitietSP = chitietSP;
    }

    public int getIdLoaiSP() {
        return IdLoaiSP;
    }

    public void setIdLoaiSP(int idLoaiSP) {
        IdLoaiSP = idLoaiSP;
    }
}

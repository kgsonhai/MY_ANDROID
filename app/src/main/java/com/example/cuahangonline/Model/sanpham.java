package com.example.cuahangonline.Model;

import java.io.Serializable;

public class sanpham implements Serializable {
    public int ID;
    public String tensp;
    public Integer giasp;
    public String hinhanhsp;
    public String motaSP;
    public int IDLoaiSP;

    public sanpham(int ID, String tensp, Integer giasp, String hinhanhsp, String motaSP, int IDLoaiSP) {
        this.ID = ID;
        this.tensp = tensp;
        this.giasp = giasp;
        this.hinhanhsp = hinhanhsp;
        this.motaSP = motaSP;
        this.IDLoaiSP = IDLoaiSP;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public Integer getGiasp() {
        return giasp;
    }

    public void setGiasp(Integer giasp) {
        this.giasp = giasp;
    }

    public String getHinhanhsp() {
        return hinhanhsp;
    }

    public void setHinhanhsp(String hinhanhsp) {
        this.hinhanhsp = hinhanhsp;
    }

    public String getMotaSP() {
        return motaSP;
    }

    public void setMotaSP(String motaSP) {
        this.motaSP = motaSP;
    }

    public int getIDLoaiSP() {
        return IDLoaiSP;
    }

    public void setIDLoaiSP(int IDLoaiSP) {
        this.IDLoaiSP = IDLoaiSP;
    }
}

package com.example.cuahangonline.Model;

public class Information_user {
    public int idUser;
    public String ten;
    public String sdt;
    public String diachi;
    public String email;
    public String anhdaidien;
    public String username;

    public Information_user(int idUser,String ten, String sdt, String diachi, String anhdaidien, String username,String email) {
        this.idUser = idUser;
        this.ten = ten;
        this.sdt = sdt;
        this.diachi = diachi;
        this.anhdaidien = anhdaidien;
        this.username = username;
        this.email = email;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getAnhdaidien() {
        return anhdaidien;
    }

    public void setAnhdaidien(String anhdaidien) {
        this.anhdaidien = anhdaidien;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

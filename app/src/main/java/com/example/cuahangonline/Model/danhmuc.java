package com.example.cuahangonline.Model;

import java.io.Serializable;

public class danhmuc implements Serializable {
    private int idDM;
    private String tenDM;
    private String imgDM;

    public danhmuc(String tenDM, String imgDM, int idDM) {
        this.idDM = idDM;
        this.tenDM = tenDM;
        this.imgDM = imgDM;
    }

    public int getIdDM() {
        return idDM;
    }

    public void setIdDM(int idDM) {
        this.idDM = idDM;
    }

    public String getTenDM() {
        return tenDM;
    }

    public void setTenDM(String tenDM) {
        this.tenDM = tenDM;
    }

    public String getImgDM() {
        return imgDM;
    }

    public void setImgDM(String imgDM) {
        this.imgDM = imgDM;
    }
}

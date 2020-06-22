package com.example.giloliadmin.Model;

import androidx.annotation.NonNull;

public class TheLoai {
    private   String maLoai;
     private String tenLoai;
    private String uRLAnh;

    public TheLoai(){}
    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getuRLAnh() {
        return uRLAnh;
    }

    public void setuRLAnh(String uRLAnh) {
        this.uRLAnh = uRLAnh;
    }

    public TheLoai(String maLoai, String tenLoai, String uRLAnh) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.uRLAnh = uRLAnh;
    }

}

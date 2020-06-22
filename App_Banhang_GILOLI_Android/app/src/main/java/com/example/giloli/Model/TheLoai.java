package com.example.giloli.Model;

public class TheLoai {
    private String MaLoai;
    private String TenLoai;
    private String uRLAnh;

    public TheLoai() {
    }

    public TheLoai(String maLoai, String tenLoai, String urlanh) {
        MaLoai = maLoai;
        TenLoai = tenLoai;
        this.uRLAnh = urlanh;
    }

    public String getMaLoai() {
        return MaLoai;
    }
    public void setMaLoai(String maLoai) {
        MaLoai = maLoai;
    }
    public String getTenLoai() {
        return TenLoai;
    }
    public void setTenLoai(String tenLoai) {
        TenLoai = tenLoai;
    }
    public String getuRLAnh() {
        return uRLAnh;
    }
    public void setuRLAnh(String urlanh) {
        this.uRLAnh = urlanh;
    }
}

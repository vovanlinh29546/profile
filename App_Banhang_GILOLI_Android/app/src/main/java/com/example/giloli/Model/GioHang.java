package com.example.giloli.Model;

public class GioHang {
    int idGioiHang;
    String email;
    String tenHang;
    String soLuong;
    String urlhang;
    String giaTien;
    String maSp;

    public GioHang(int idGioiHang, String email, String tenHang, String soLuong, String urlhang, String giaTien, String maSp) {
        this.idGioiHang = idGioiHang;
        this.email = email;
        this.tenHang = tenHang;
        this.soLuong = soLuong;
        this.urlhang = urlhang;
        this.giaTien = giaTien;
        this.maSp = maSp;
    }

    public int getIdGioiHang() {
        return idGioiHang;
    }
    public void setIdGioiHang(int idGioiHang) {
        this.idGioiHang = idGioiHang;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTenHang() {
        return tenHang;
    }
    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }
    public String getSoLuong() {
        return soLuong;
    }
    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }
    public String getUrlhang() {
        return urlhang;
    }
    public void setUrlhang(String urlhang) {
        this.urlhang = urlhang;
    }
    public String getGiaTien() {
        return giaTien;
    }
    public void setGiaTien(String giaTien) {
        this.giaTien = giaTien;
    }
    public String getMaSp() {
        return maSp;
    }
    public void setMaSp(String maSp) {
        this.maSp = maSp;
    }
    public GioHang() {
    }
}

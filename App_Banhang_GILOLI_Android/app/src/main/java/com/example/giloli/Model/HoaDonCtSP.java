package com.example.giloli.Model;

public class HoaDonCtSP {
    String maSp;
    String tenSp;
    String giaSp;
    String urlImage;
    int Soluong;
    String maHoaDonCt;

    public HoaDonCtSP(String maSp, String tenSp, String giaSp, String urlImage, int soluong, String maHoaDonCt) {
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.giaSp = giaSp;
        this.urlImage = urlImage;
        Soluong = soluong;
        this.maHoaDonCt = maHoaDonCt;
    }
    public HoaDonCtSP(){

    }

    public String getMaSp() {
        return maSp;
    }

    public void setMaSp(String maSp) {
        this.maSp = maSp;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public String getGiaSp() {
        return giaSp;
    }

    public void setGiaSp(String giaSp) {
        this.giaSp = giaSp;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public int getSoluong() {
        return Soluong;
    }

    public void setSoluong(int soluong) {
        Soluong = soluong;
    }

    public String getMaHoaDonCt() {
        return maHoaDonCt;
    }

    public void setMaHoaDonCt(String maHoaDonCt) {
        this.maHoaDonCt = maHoaDonCt;
    }
}

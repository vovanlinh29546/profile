package com.example.giloli.Model;

public class HoaDonCT {
    String maHoaDonCt;
    int soLuong;
    String maSP;
    String giaThanhSp;
    String maHoaDon;

    public HoaDonCT(String maHoaDonCt, int soLuong, String maSP, String giaThanhSp, String maHoaDon) {
        this.maHoaDonCt = maHoaDonCt;
        this.soLuong = soLuong;
        this.maSP = maSP;
        this.giaThanhSp = giaThanhSp;
        this.maHoaDon = maHoaDon;
    }
    public HoaDonCT(){

    }

    public String getMaHoaDonCt() {
        return maHoaDonCt;
    }

    public void setMaHoaDonCt(String maHoaDonCt) {
        this.maHoaDonCt = maHoaDonCt;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getGiaThanhSp() {
        return giaThanhSp;
    }

    public void setGiaThanhSp(String giaThanhSp) {
        this.giaThanhSp = giaThanhSp;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }
}

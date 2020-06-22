package com.example.giloli.Model;

public class DatHang {
    String tenKieu;
    String gia;
    String soNgayGiao;

    public DatHang(String tenKieu, String gia, String soNgayGiao) {
        this.tenKieu = tenKieu;
        this.gia = gia;
        this.soNgayGiao = soNgayGiao;
    }
    public DatHang(){

    }

    public String getTenKieu() {
        return tenKieu;
    }

    public void setTenKieu(String tenKieu) {
        this.tenKieu = tenKieu;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getSoNgayGiao() {
        return soNgayGiao;
    }

    public void setSoNgayGiao(String soNgayGiao) {
        this.soNgayGiao = soNgayGiao;
    }
}

package com.example.giloli.Model;

import java.util.List;
public class SanPham {
    String maSanPham;
    String tenSanPham;
    int soLuong;
    String  giaThanh;
    String moTa;
    String trongLuong;
    String nhaCungCap;
    String thuongHieu;
    String huongDan;
    String maLoai;
    List<String> linkUrl;
    public List<String> getLinkUrl(){
        return linkUrl;
    }
    public void setLinkUrl(List<String> linkUrl){
        this.linkUrl = linkUrl;
    }
    public SanPham(String maSanPham, String tenSanPham, int soLuong, String giaThanh, String moTa, String trongLuong,
                   String nhaCungCap, String thuongHieu, String huongDan, String maLoai, String URLSP, List<String> linkUrl) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.soLuong = soLuong;
        this.giaThanh = giaThanh;
        this.moTa = moTa;
        this.trongLuong = trongLuong;
        this.nhaCungCap = nhaCungCap;
        this.thuongHieu = thuongHieu;
        this.huongDan = huongDan;
        this.maLoai = maLoai;
        this.linkUrl = linkUrl;
    }

    public SanPham() {
    }
    public String getMaSanPham() {
        return maSanPham;
    }
    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }
    public String getTenSanPham() {
        return tenSanPham;
    }
    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }
    public int getSoLuong() {
        return soLuong;
    }
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
    public String getGiaThanh() {
        return giaThanh;
    }
    public void setGiaThanh(String giaThanh) {
        this.giaThanh = giaThanh;
    }
    public String getMoTa() {
        return moTa;
    }
    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
    public String getTrongLuong() {
        return trongLuong;
    }
    public void setTrongLuong(String trongLuong) {
        this.trongLuong = trongLuong;
    }
    public String getNhaCungCap() {
        return nhaCungCap;
    }
    public void setNhaCungCap(String nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }
    public String getThuongHieu() { return thuongHieu; }
    public void setThuongHieu(String thuongHieu) {
        this.thuongHieu = thuongHieu;
    }
    public String getHuongDan() {
        return huongDan;
    }
    public void setHuongDan(String huongDan) {
        this.huongDan = huongDan;
    }
    public String getMaLoai() {
        return maLoai;
    }
    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }


}


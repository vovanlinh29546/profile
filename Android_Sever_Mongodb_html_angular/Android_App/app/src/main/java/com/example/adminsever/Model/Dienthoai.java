package com.example.adminsever.Model;

public class Dienthoai {
    public String  _id;
    public String  madt;
    public String tendt;
    public String nhanhieu;
    public String mau;
    public String noisx;

    public Dienthoai() {
    }

    public Dienthoai(String madt, String tendt, String nhanhieu, String mau, String noisx) {
        this.madt = madt;
        this.tendt = tendt;
        this.nhanhieu = nhanhieu;
        this.mau = mau;
        this.noisx = noisx;
    }
}

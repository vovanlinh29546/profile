package com.example.giloliadmin.Model;

public class DiaChi_ThiXa {
    String tenQuan;
    String tenPhuongXa;

    public DiaChi_ThiXa() {
    }

    public String getTenQuan() {
        return tenQuan;
    }

    public void setTenQuan(String tenQuan) {
        this.tenQuan = tenQuan;
    }

    public String getTenPhuongXa() {
        return tenPhuongXa;
    }

    public void setTenPhuongXa(String tenPhuongXa) {
        this.tenPhuongXa = tenPhuongXa;
    }

    public DiaChi_ThiXa(String tenQuan, String tenPhuongXa) {
        this.tenQuan = tenQuan;
        this.tenPhuongXa = tenPhuongXa;
    }
}

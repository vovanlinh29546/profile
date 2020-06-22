package com.example.giloli.Model;

import androidx.annotation.NonNull;

public class DiaChi_ThiXa {
    String tenQuan;
    String tenPhuongXa;
    public DiaChi_ThiXa() { }
    public String getTenQuan() {
        return tenQuan; }
    public void setTenQuan(String tenQuan) {
        this.tenQuan = tenQuan; }
    public String getTenPhuongXa() {
        return tenPhuongXa; }
    public void setTenPhuongXa(String tenPhuongXa) {
        this.tenPhuongXa = tenPhuongXa; }
    public DiaChi_ThiXa(String tenQuan, String tenPhuongXa) {
        this.tenQuan = tenQuan;
        this.tenPhuongXa = tenPhuongXa; }
    @NonNull
    @Override
    public String toString() { return tenPhuongXa; }
}

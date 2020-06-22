package com.example.giloli.Model;

public class DiaChiNguoiDung {
    int iDDiaChiDone;
    String hoten,soDienThoai,tenQuan,tenPhuong,diaChi,email;
    public DiaChiNguoiDung() {
    }
    public DiaChiNguoiDung(int iDDiaChiDone, String hoten, String soDienThoai, String tenQuan,
                           String tenPhuong, String diaChi, String email) {
        this.iDDiaChiDone = iDDiaChiDone;
        this.hoten = hoten;
        this.soDienThoai = soDienThoai;
        this.tenQuan = tenQuan;
        this.tenPhuong = tenPhuong;
        this.diaChi = diaChi;
        this.email = email;
    }

    public int getiDDiaChiDone() {
        return iDDiaChiDone;
    }
    public void setiDDiaChiDone(int iDDiaChiDone) {
        this.iDDiaChiDone = iDDiaChiDone;
    }
    public String getHoten() {
        return hoten;
    }
    public void setHoten(String hoten) {
        this.hoten = hoten;
    }
    public String getSoDienThoai() {
        return soDienThoai;
    }
    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
    public String getTenQuan() {
        return tenQuan;
    }
    public void setTenQuan(String tenQuan) {
        this.tenQuan = tenQuan;
    }
    public String getTenPhuong() {
        return tenPhuong;
    }
    public void setTenPhuong(String tenPhuong) {
        this.tenPhuong = tenPhuong;
    }
    public String getDiaChi() {
        return diaChi;
    }
    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}

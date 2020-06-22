package com.example.giloliadmin.Model;

public class NguoiDung {

    int iDNguoiDung;
  public    String soDienThoai,email,hoten,matKhau;
   public   String  ngaySinh;


    public int getiDNguoiDung() {
        return iDNguoiDung;
    }

    public void setiDNguoiDung(int iDNguoiDung) {
        this.iDNguoiDung = iDNguoiDung;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public NguoiDung(int iDNguoiDung, String soDienThoai, String email, String hoten, String matKhau, String ngaySinh) {
        this.iDNguoiDung = iDNguoiDung;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.hoten = hoten;
        this.matKhau = matKhau;
        this.ngaySinh = ngaySinh;
    }

    public NguoiDung() {
    }


}

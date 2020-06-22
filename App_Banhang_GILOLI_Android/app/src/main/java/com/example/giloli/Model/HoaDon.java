package com.example.giloli.Model;

public class HoaDon {
    String maHoaDon;
    String emailNguoiDung;
    String dateHoaDon;
    String tongCong;
    String trangThai;
    String phuongThuc;
    String tenNguoiNhan;
    String sdtNguoiNhan;
    String DiaChiNguoiNhan;
    String DonViVanChuyen;
    public HoaDon(){

    }

    public HoaDon(String maHoaDon, String emailNguoiDung, String dateHoaDon, String tongCong, String trangThai, String phuongThuc, String tenNguoiNhan, String sdtNguoiNhan, String diaChiNguoiNhan, String donViVanChuyen) {
        this.maHoaDon = maHoaDon;
        this.emailNguoiDung = emailNguoiDung;
        this.dateHoaDon = dateHoaDon;
        this.tongCong = tongCong;
        this.trangThai = trangThai;
        this.phuongThuc = phuongThuc;
        this.tenNguoiNhan = tenNguoiNhan;
        this.sdtNguoiNhan = sdtNguoiNhan;
        DiaChiNguoiNhan = diaChiNguoiNhan;
        DonViVanChuyen = donViVanChuyen;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getEmailNguoiDung() {
        return emailNguoiDung;
    }

    public void setEmailNguoiDung(String emailNguoiDung) {
        this.emailNguoiDung = emailNguoiDung;
    }

    public String getDateHoaDon() {
        return dateHoaDon;
    }

    public void setDateHoaDon(String dateHoaDon) {
        this.dateHoaDon = dateHoaDon;
    }

    public String getTongCong() {
        return tongCong;
    }

    public void setTongCong(String tongCong) {
        this.tongCong = tongCong;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getPhuongThuc() {
        return phuongThuc;
    }

    public void setPhuongThuc(String phuongThuc) {
        this.phuongThuc = phuongThuc;
    }

    public String getTenNguoiNhan() {
        return tenNguoiNhan;
    }

    public void setTenNguoiNhan(String tenNguoiNhan) {
        this.tenNguoiNhan = tenNguoiNhan;
    }

    public String getSdtNguoiNhan() {
        return sdtNguoiNhan;
    }

    public void setSdtNguoiNhan(String sdtNguoiNhan) {
        this.sdtNguoiNhan = sdtNguoiNhan;
    }

    public String getDiaChiNguoiNhan() {
        return DiaChiNguoiNhan;
    }

    public void setDiaChiNguoiNhan(String diaChiNguoiNhan) {
        DiaChiNguoiNhan = diaChiNguoiNhan;
    }

    public String getDonViVanChuyen() {
        return DonViVanChuyen;
    }

    public void setDonViVanChuyen(String donViVanChuyen) {
        DonViVanChuyen = donViVanChuyen;
    }
}

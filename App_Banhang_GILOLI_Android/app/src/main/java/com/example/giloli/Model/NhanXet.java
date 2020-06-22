package com.example.giloli.Model;

public class NhanXet {
    String idnhanxet;
    String email;
    String masp;
    String nhanxet;
    String ngaynhanxet;
    String dohailong;
    String hoten;


    public NhanXet() {
    }

    public NhanXet(String idnhanxet, String email, String masp, String nhanxet, String ngaynhanxet, String dohailong, String hoten) {
        this.idnhanxet = idnhanxet;
        this.email = email;
        this.masp = masp;
        this.nhanxet = nhanxet;
        this.ngaynhanxet = ngaynhanxet;
        this.dohailong = dohailong;
        this.hoten = hoten;
    }

    public String getIdnhanxet() {
        return idnhanxet;
    }

    public void setIdnhanxet(String idnhanxet) {
        this.idnhanxet = idnhanxet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public String getNhanxet() {
        return nhanxet;
    }

    public void setNhanxet(String nhanxet) {
        this.nhanxet = nhanxet;
    }

    public String getNgaynhanxet() {
        return ngaynhanxet;
    }

    public void setNgaynhanxet(String ngaynhanxet) {
        this.ngaynhanxet = ngaynhanxet;
    }

    public String getDohailong() {
        return dohailong;
    }

    public void setDohailong(String dohailong) {
        this.dohailong = dohailong;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }
}

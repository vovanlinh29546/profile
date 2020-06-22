package com.example.giloli.Model;

public class ThongBao {
    public ThongBao(){}
    public ThongBao(String mathongbao, String tieude, String noidung, String urlanhthongbao) {
        this.mathongbao = mathongbao;
        this.tieude = tieude;
        this.noidung = noidung;
        this.urlanhthongbao = urlanhthongbao;
    }

    public String getMathongbao(){return mathongbao;}
    public void setMathongbao(String mathongbao){this.mathongbao = mathongbao;}
    public String getTieude() {
        return tieude;
    }
    public void setTieude(String tieude) {
        this.tieude = tieude;
    }
    public String getNoidung() {
        return noidung;
    }
    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }
    public String getUrlanhthongbao() {
        return urlanhthongbao;
    }
    public void setUrlanhthongbao(String urlanhthongbao) {
        this.urlanhthongbao = urlanhthongbao;
    }

    public String tieude;
    public String noidung;
    public String urlanhthongbao;
    public String mathongbao;
}

package com.example.giloliadmin.Model;

import androidx.annotation.NonNull;

public class DiaChi_QuanHuyen {
    String tenQuan;

    public DiaChi_QuanHuyen() {
    }

    public String getTenQuan() {
        return tenQuan;
    }

    public void setTenQuan(String tenQuan) {
        this.tenQuan = tenQuan;
    }

    public DiaChi_QuanHuyen(String tenQuan) {
        this.tenQuan = tenQuan;
    }

    @NonNull
    @Override
    public String toString() {
        return tenQuan;
    }
}

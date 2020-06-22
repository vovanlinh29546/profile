package com.example.giloli.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import com.example.giloli.Adapter.Adapter_GridviewSanPham;
import com.example.giloli.DAO.SanPhamDAO;
import com.example.giloli.DAO.TheLoaiDAO;
import com.example.giloli.Model.SanPham;
import com.example.giloli.Model.TheLoai;
import com.example.giloli.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChiTietDanhMucSanPham extends AppCompatActivity {
    GridView gridView;
    TextView textView, textMl;
    public static Adapter_GridviewSanPham adapter_gridviewSanPham;
    List<SanPham> sanPhams;
    TheLoaiDAO theLoaiDAO;
    SanPhamDAO sanPhamDAO;
    String idDm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_danh_muc_san_pham);
        Intent intent = getIntent();
        if (intent.getAction().equals("FromTT")){
            idDm = intent.getStringExtra("IDDMTT");
        }
        if (intent.getAction().equals("IDDMGV")){
            idDm = intent.getStringExtra("IDDM");
        }
        gridView = findViewById(R.id.gridviewCTDMSP);
        textView = findViewById(R.id.txtCTDMSP);
        textMl = findViewById(R.id.txtMaLoai);
        theLoaiDAO = new TheLoaiDAO(this);
        theLoaiDAO.layDanhMuc(idDm,textView);
        textMl.setText(idDm);
        sanPhams = new ArrayList<>();
        sanPhamDAO = new SanPhamDAO(this);
        sanPhams = sanPhamDAO.layHetSanPhamTheoDm(textMl.getText().toString());
        adapter_gridviewSanPham = new Adapter_GridviewSanPham(ChiTietDanhMucSanPham.this,sanPhams);
        gridView.setAdapter(adapter_gridviewSanPham);



    }
}

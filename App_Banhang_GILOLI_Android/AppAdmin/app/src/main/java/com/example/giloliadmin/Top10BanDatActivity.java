package com.example.giloliadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.giloliadmin.Adapter.AdapterHoaDonChiTiet;
import com.example.giloliadmin.DAO.HoaDonCtDAO;
import com.example.giloliadmin.DAO.HoaDonDAO;
import com.example.giloliadmin.Model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class Top10BanDatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<SanPham> list;
    private HoaDonCtDAO hoaDonDAO;
    public static AdapterHoaDonChiTiet adapterHoaDonChiTiet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top10_ban_dat);
        recyclerView = findViewById(R.id.recyclerViewTop10);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        list = new ArrayList<SanPham>();
        hoaDonDAO = new HoaDonCtDAO(this);
        list = hoaDonDAO.getTop10Sp();
        adapterHoaDonChiTiet = new AdapterHoaDonChiTiet(this,list);
        recyclerView.setAdapter(adapterHoaDonChiTiet);

    }
}

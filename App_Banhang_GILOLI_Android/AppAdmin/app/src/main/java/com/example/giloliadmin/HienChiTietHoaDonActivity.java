package com.example.giloliadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.giloliadmin.Adapter.HoaDonCtAdapter;
import com.example.giloliadmin.DAO.HoaDonCtDAO;
import com.example.giloliadmin.Model.HoaDonCT;
import com.example.giloliadmin.Model.HoaDonCtSP;

import java.util.ArrayList;
import java.util.List;

public class HienChiTietHoaDonActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView textView;
    private List<HoaDonCtSP> hoaDonCTS;
    private HoaDonCtDAO hoaDonCtDAO;
    public static HoaDonCtAdapter hoaDonCtAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hien_chi_tiet_hoa_don);
        recyclerView = findViewById(R.id.recyclerViewCTHD);
        textView = findViewById(R.id.maHoaDonAo);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("box");
        String maHoaDon = bundle.getString("maHoaDon");
        textView.setText(maHoaDon);
        hoaDonCTS = new ArrayList<HoaDonCtSP>();
        hoaDonCtDAO = new HoaDonCtDAO(this);
        hoaDonCTS = hoaDonCtDAO.getHoaDonCt(textView.getText().toString());
        hoaDonCtAdapter = new HoaDonCtAdapter(this,hoaDonCTS);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //Tạo đường kẻ, phân biệt giữa các phần tử
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(hoaDonCtAdapter);

    }
}

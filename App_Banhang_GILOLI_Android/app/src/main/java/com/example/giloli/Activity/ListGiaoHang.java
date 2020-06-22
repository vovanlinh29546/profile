package com.example.giloli.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.giloli.Adapter.AdapterDatHang;
import com.example.giloli.DAO.AddListDatHang;
import com.example.giloli.Model.DatHang;
import com.example.giloli.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class ListGiaoHang extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<DatHang> list;
    public static AdapterDatHang adapterDatHang;
    private AddListDatHang addListDatHang;
    ImageView imgArrowBlack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_giao_hang);
        addListDatHang = new AddListDatHang(this);
        recyclerView = findViewById(R.id.recyclerListHang);
        list = new ArrayList<>();
        list = addListDatHang.layListPt();
        adapterDatHang = new AdapterDatHang(this,list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterDatHang);

    }

}

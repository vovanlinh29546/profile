package com.example.adminsever;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.adminsever.Adapter.DtAdapter;
import com.example.adminsever.Adapter.StudentAdapter;
import com.example.adminsever.DAO.DtDAO;
import com.example.adminsever.DAO.StudentDAO;
import com.example.adminsever.Model.Dienthoai;
import com.example.adminsever.Model.Students;

import java.util.ArrayList;

public class quanlydt extends AppCompatActivity {

    public String _id;
    ListView lv;
    Dienthoai dt;
    DtDAO dtDao;
    ArrayList<Dienthoai> dienthoais;
    public DtAdapter dienthoaiadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanlydt);
        lv=findViewById(R.id.lv2);


        dtDao = new DtDAO(this);

        capnhatLV();
    }
    public void capnhatLV(){

        // getAll Student
        dienthoais = (ArrayList<Dienthoai>) dtDao.getAlldt();

        // gan adapter

        dienthoaiadapter = new DtAdapter(this, dienthoais);

        lv.setAdapter(dienthoaiadapter);
    }
}

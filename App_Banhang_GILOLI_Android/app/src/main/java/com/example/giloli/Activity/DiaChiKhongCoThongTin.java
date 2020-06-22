package com.example.giloli.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.giloli.R;
import com.example.giloli.fragment.CaNhanFragmentDone;

public class DiaChiKhongCoThongTin extends AppCompatActivity {
Button btnthemdiachikhongcothongtin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_chi_khong_co_thong_tin);
        /*
        Toolbar toolbar = findViewById(R.id.toolbar_diachikhongcothongtin);
        toolbar.setTitle("Sổ địa chỉ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        btnthemdiachikhongcothongtin=findViewById(R.id.btnthemdiachikhongcothongtin);
        btnthemdiachikhongcothongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iq = new Intent(DiaChiKhongCoThongTin.this, ThemSoDiaChi.class);
                 startActivity(iq);
            }
        });

         */
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
               // Intent i = new Intent(DiaChiKhongCoThongTin.this, CaNhanFragmentDone.class);
               // startActivity(i);
                   onBackPressed();
                //   fragmentManager.beginTransaction().replace(R.id.content_frame,fragment).addToBackStack("tag").commit();
                return true;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}

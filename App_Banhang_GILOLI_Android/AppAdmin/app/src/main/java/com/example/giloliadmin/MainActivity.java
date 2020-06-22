package com.example.giloliadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.giloliadmin.fragment.AddDiaChiFragment;
import com.example.giloliadmin.fragment.HoaDonFragment;
import com.example.giloliadmin.fragment.KhachHangFragment;
import com.example.giloliadmin.fragment.PhanLoaiFragment;
import com.example.giloliadmin.fragment.SanPhamFragment;
import com.example.giloliadmin.fragment.ThongBaoFragment;
import com.example.giloliadmin.fragment.ThongKeFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        // Mặc đinh mới vào là Fragment Thống Kê
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new KhachHangFragment()).commit();
            navigationView.setCheckedItem(R.id.khachhang);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.khachhang:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new KhachHangFragment()).commit();
                break;
            case R.id.phanloai:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PhanLoaiFragment()).commit();
                break;
            case R.id.hoadon:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HoaDonFragment()).commit();
                break;
            case R.id.thongke:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ThongKeFragment()).commit();
                break;
            case R.id.sanpham:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SanPhamFragment()).commit();
                break;
            case R.id.thongbao:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ThongBaoFragment()).commit();
                break;
            case R.id.diachi:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AddDiaChiFragment()).commit();
                break;
            case R.id.thoat:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);
                builder.setTitle("Thoát Ứng Dụng");
                builder.setCancelable(false);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Đóng ứng dụng
                        Intent out= new Intent(Intent.ACTION_MAIN);
                        out.addCategory(Intent.CATEGORY_HOME);
                        startActivity(out);
                        finish();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }}

package com.example.adminsever;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;


import com.example.adminsever.Adapter.Adapter_vPager_dndk;
import com.google.android.material.tabs.TabLayout;


public class DangNhap_DangKi_Activity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangnhap_dangki);
        Toolbar toolbar = findViewById(R.id.toolbar_dangnhapdangki);
        tabLayout = findViewById(R.id.tablayout_dndk);
        viewPager = findViewById(R.id.viewpager_dndk);
        viewPager.setAdapter(new Adapter_vPager_dndk(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

    }



    @Override
    public void onBackPressed() {
        if ( getFragmentManager().getBackStackEntryCount() > 0)
        {
            getFragmentManager().popBackStack();
            return;
        }
        super.onBackPressed();
    }
}

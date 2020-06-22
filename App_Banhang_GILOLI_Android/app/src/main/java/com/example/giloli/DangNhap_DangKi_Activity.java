package com.example.giloli;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.giloli.Adapter.Adapter_vPager_dndk;
import com.example.giloli.fragment.CaNhanFragment;
import com.example.giloli.fragment.TrangChuFragment;
import com.google.android.material.tabs.TabLayout;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.home:
               onBackPressed();
                 break;
                 default:break;
        }

        return super.onOptionsItemSelected(item);
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

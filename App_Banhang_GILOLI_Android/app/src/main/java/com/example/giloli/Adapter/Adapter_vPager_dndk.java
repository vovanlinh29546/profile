package com.example.giloli.Adapter;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.example.giloli.fragment.DangKiFragment;
import com.example.giloli.fragment.DangNhapFragment;


public class Adapter_vPager_dndk extends FragmentStatePagerAdapter {

    private String listTab[] = {"ĐĂNG NHẬP", "ĐĂNG KÍ"};
    private DangNhapFragment dangNhapFragment;
    private DangKiFragment dangKiFragment;
    public Adapter_vPager_dndk(FragmentManager fm) {
        super(fm);
        dangNhapFragment = new DangNhapFragment();
        dangKiFragment = new DangKiFragment();
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
        if(i==0){
            return dangNhapFragment;
        }else if(i==1){
            return dangKiFragment;
        }else
        {}
        return null;
    }


    @Override
    public int getCount( ) {
        return listTab.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTab[position];
    }
}

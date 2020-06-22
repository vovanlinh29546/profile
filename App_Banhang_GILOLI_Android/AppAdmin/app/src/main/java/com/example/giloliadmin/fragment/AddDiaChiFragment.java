package com.example.giloliadmin.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.giloliadmin.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddDiaChiFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_adddiachi, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Them Dia Chi");
        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container_adddiachi,new ThemQuanFragment());
        transaction.commit();
        BottomNavigationView bottomnav=view.findViewById(R.id.bottom_navi_adddiachi);
        bottomnav.setOnNavigationItemSelectedListener(Navlister);
    }
    BottomNavigationView.OnNavigationItemSelectedListener Navlister=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment=null;
            switch (menuItem.getItemId()){
                case R.id.nav_themquan:
                    selectedFragment=new ThemQuanFragment();
                    break;
                case R.id.nav_themphuongxa:
                    selectedFragment=new ThemPhuongXaFragment();
                    break;
            }
            FragmentTransaction transaction=getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container_adddiachi,selectedFragment);
            transaction.commit();
            return true;
        }
    };
}

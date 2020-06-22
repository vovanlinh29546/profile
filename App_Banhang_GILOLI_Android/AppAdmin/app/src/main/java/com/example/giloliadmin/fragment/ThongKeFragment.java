package com.example.giloliadmin.fragment;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.giloliadmin.R;
import com.example.giloliadmin.ThongKeActivity;
import com.example.giloliadmin.Top10BanDatActivity;

public class ThongKeFragment extends Fragment {
    private Button btnThongKe, btnGetTop10, btnGetTop10e, btnGetTopLove;
    private Button btnHetHang;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thongke, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Thống kê");
        btnThongKe = view.findViewById(R.id.btnThongKe);
        btnGetTop10 = view.findViewById(R.id.btnTop10d);
        btnGetTop10e = view.findViewById(R.id.btnTop10e);
        btnGetTopLove = view.findViewById(R.id.btnTop10lLove);
        btnHetHang = view.findViewById(R.id.btnHetHang);
        setBtnThongKe();
        setBtnGetTop10();
        setBtnHetHang();
    }
    private void setBtnThongKe(){
        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),ThongKeActivity.class));
            }
        });
    }
    private void setBtnGetTop10(){
        btnGetTop10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Xin lỗi bạn, ứng dụng đang phát triển chức năng này",
                        Toast.LENGTH_SHORT).show();
            }
        });
        btnGetTop10e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Xin lỗi bạn, ứng dụng đang phát triển chức năng này",
                        Toast.LENGTH_SHORT).show();
            }
        });
        btnGetTopLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Xin lỗi bạn, ứng dụng đang phát triển chức năng này",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setBtnHetHang(){
        btnHetHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Xin lỗi bạn, ứng dụng đang phát triển chức năng này",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}

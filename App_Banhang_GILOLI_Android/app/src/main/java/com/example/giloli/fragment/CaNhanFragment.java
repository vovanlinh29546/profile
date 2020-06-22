package com.example.giloli.fragment;


import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.example.giloli.DangNhap_DangKi_Activity;
import com.example.giloli.R;


public class CaNhanFragment extends Fragment {
    TextView tv_dndkUser;
    RelativeLayout relative_dangnhapdangki;
    RelativeLayout QLDH, GDTN, DHDCVC, DHTC, DHDH, SDC, STTTT, SPDM, SPYT, SPMS, CM, HL, HC;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_canhan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Mọi thứ về Toolbar
        Toolbar toolbar = view.findViewById(R.id.toolbar_canhan);
        toolbar.setTitle("Cá nhân");
        // Ánh xạ
        tv_dndkUser = view.findViewById(R.id.tv_dndk_user);
        relative_dangnhapdangki = view.findViewById(R.id.relative_dangnhapdangki);
        QLDH = view.findViewById(R.id.QLDH);
        GDTN = view.findViewById(R.id.GDTN);
        DHDCVC = view.findViewById(R.id.DHDCVC);
        DHTC = view.findViewById(R.id.DHTC);
        DHDH = view.findViewById(R.id.DHDH);
        SDC = view.findViewById(R.id.SDC);
        STTTT = view.findViewById(R.id.STTTT);
        SPDM = view.findViewById(R.id.SPDM);
        SPYT = view.findViewById(R.id.SPYT);
        SPMS = view.findViewById(R.id.SPMS);
        CM = view.findViewById(R.id.CM);
        HL = view.findViewById(R.id.HL);
        HC = view.findViewById(R.id.HC);
        onClickk();
        relative_dangnhapdangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DangNhap_DangKi_Activity.class);
                startActivity(intent);
            }
        });

    }
    private void onClickk(){
        QLDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DangNhap_DangKi_Activity.class);
                startActivity(intent);
            }
        });
        GDTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DangNhap_DangKi_Activity.class);
                startActivity(intent);
            }
        });
        DHDCVC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DangNhap_DangKi_Activity.class);
                startActivity(intent);
            }
        });
        DHTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DangNhap_DangKi_Activity.class);
                startActivity(intent);
            }
        });
        DHDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DangNhap_DangKi_Activity.class);
                startActivity(intent);
            }
        });
        SDC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DangNhap_DangKi_Activity.class);
                startActivity(intent);
            }
        });
        STTTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DangNhap_DangKi_Activity.class);
                startActivity(intent);
            }
        });
        SPDM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DangNhap_DangKi_Activity.class);
                startActivity(intent);
            }
        });
        SPYT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DangNhap_DangKi_Activity.class);
                startActivity(intent);
            }
        });
        SPMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DangNhap_DangKi_Activity.class);
                startActivity(intent);
            }
        });
        CM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DangNhap_DangKi_Activity.class);
                startActivity(intent);
            }
        });
        HL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DangNhap_DangKi_Activity.class);
                startActivity(intent);
            }
        });
        HC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DangNhap_DangKi_Activity.class);
                startActivity(intent);
            }
        });

    }

}

package com.example.adminsever.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.adminsever.Model.Dienthoai;

import com.example.adminsever.R;

import java.util.ArrayList;

public class DtAdapter extends ArrayAdapter {
    TextView tvmadt,tvtendt,tvnhanhieu,tvmau,tvnoisx;
    ImageButton btnXoadt;
    Context context;
    ArrayList<Dienthoai> dienthoais;

    public DtAdapter(@NonNull Context context, ArrayList<Dienthoai> dienthoais) {
        super(context, 0,dienthoais);
        this.context = context;
        this.dienthoais = dienthoais;
    }


    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View v = convertView;

        if (v == null){
            v= LayoutInflater.from(context).inflate(R.layout.item_listdt,parent,false);
        }
        final Dienthoai sv = dienthoais.get(position);
        if (sv !=null) {
            //anh xa
            tvmadt = (TextView) v.findViewById(R.id.tvmadt);
            tvtendt = (TextView) v.findViewById(R.id.tvtendt);
            tvnhanhieu = (TextView) v.findViewById(R.id.tvnhanhieu);
            tvnoisx = (TextView) v.findViewById(R.id.tvnoisx);
            tvmau = (TextView) v.findViewById(R.id.tvmau);
            btnXoadt = (ImageButton)v.findViewById(R.id.btnXoadt);
            //set data len layout custom

            tvmadt.setText(sv.madt);
            tvtendt.setText(sv.tendt);
            tvnhanhieu.setText(sv.nhanhieu);
            tvmau.setText(sv.mau);
            tvnoisx.setText(sv.noisx);

        }




        return v;
    }
}


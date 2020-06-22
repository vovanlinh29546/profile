package com.example.giloliadmin.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.giloliadmin.DAO.NguoiDungDAO;
import com.example.giloliadmin.Dialog.NguoiDungChiTiet;
import com.example.giloliadmin.MainActivity;
import com.example.giloliadmin.Model.NguoiDung;
import com.example.giloliadmin.R;

import java.util.ArrayList;

public class NguoiDungAdaptercv extends RecyclerView.Adapter<NguoiDungAdaptercv.ViewHolder> {
    Context context;
    ArrayList<NguoiDung> dulieund;
    NguoiDungDAO dao;

    public NguoiDungAdaptercv(Context context, ArrayList<NguoiDung> dulieund) {
        this.context = context;
        this.dulieund = dulieund;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView sdt,fullname,email,ngaysinh;
        public ImageView popupxoasua;
        public ViewHolder(View itemview){
            super(itemview);

            popupxoasua=itemview.findViewById(R.id.popupxoasuand);
            fullname=itemview.findViewById(R.id.cvtxt_hoten);
            email=itemview.findViewById(R.id.cvtxt_email);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_nguoidung,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
      final NguoiDung nd=dulieund.get(i);
      if (nd!=null){
          viewHolder.fullname.setText(nd.hoten);
          viewHolder.email.setText(nd.email);
      }
        viewHolder.popupxoasua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup=new PopupMenu(v.getContext(),v);// co the de view thay cho text view van dc
                MenuInflater inflater=popup.getMenuInflater();
                inflater.inflate(R.menu.popup_xem,popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.action_view:
                                Intent intent = new Intent(context, NguoiDungChiTiet.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("sdt",nd.soDienThoai);
                                bundle.putString("em",nd.email);
                                bundle.putString("mk",nd.matKhau);
                                bundle.putString("ht",nd.hoten);
                                bundle.putString("ns",nd.ngaySinh);
                                intent.putExtra("suaThongTin",bundle);
                                ((MainActivity)context).startActivity(intent);
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return dulieund.size();
    }


}


package com.example.giloli.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giloli.DAO.NhanXetDao;
import com.example.giloli.Model.NhanXet;
import com.example.giloli.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class NhanXetAdapteritem3 extends RecyclerView.Adapter<NhanXetAdapteritem3.ViewHolder> {
    Context context;
    ArrayList<NhanXet> dulieund;
    NhanXetDao dao;

    public NhanXetAdapteritem3(Context context, ArrayList<NhanXet> dulieund) {
        this.context = context;
        this.dulieund = dulieund;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dohailong,hoten,ngaynhanxet,loinhanxet;


        public ViewHolder(View itemview){
            super(itemview);
            dohailong=itemview.findViewById(R.id.item_nhanxet_dohailong);
            hoten=itemview.findViewById(R.id.item_nhanxet_hoten);
            ngaynhanxet=itemview.findViewById(R.id.item_nhanxet_ngaynx);
            loinhanxet=itemview.findViewById(R.id.item_nhanxet_loinx);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_nhanxet,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
      final NhanXet nd=dulieund.get(i);
        dao = new NhanXetDao(context);
        NumberFormat formatter = new DecimalFormat("#,###");
        double tienhang;

      if (nd!=null){
          viewHolder.dohailong.setText(nd.getDohailong());
          viewHolder.hoten.setText(nd.getHoten());
          viewHolder.ngaynhanxet.setText(nd.getNgaynhanxet());
          viewHolder.loinhanxet.setText(nd.getNhanxet());
      }
    }


    @Override
    public int getItemCount() {
        return dulieund.size();
    }



}


package com.example.giloliadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giloliadmin.Model.HoaDonCtSP;
import com.example.giloliadmin.R;

import java.util.List;

public class HoaDonCtAdapter extends RecyclerView.Adapter<HoaDonCtAdapter.ViewHolder> {
    private Context context;
    private List<HoaDonCtSP> hoaDonCtSPS;
    public HoaDonCtAdapter(Context context, List<HoaDonCtSP> hoaDonCtSPS){
        this.context = context;
        this.hoaDonCtSPS = hoaDonCtSPS;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.cardviewhoadonctsp, parent, false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final HoaDonCtSP hoaDonCtSP = hoaDonCtSPS.get(position);
        if (hoaDonCtSP != null){
            holder.txtMaHoaDonCt.setText(hoaDonCtSP.getMaHoaDonCt());
            holder.txtMaSanPham.setText(hoaDonCtSP.getMaSp());
            holder.txtSoLuong.setText(String.valueOf(hoaDonCtSP.getSoluong()));
            holder.txtTenSanPham.setText(hoaDonCtSP.getTenSp());
        }
    }

    @Override
    public int getItemCount() {
        return hoaDonCtSPS.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaHoaDonCt, txtMaSanPham, txtSoLuong, txtTenSanPham;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaHoaDonCt = itemView.findViewById(R.id.txtMaHDCT1);
            txtMaSanPham = itemView.findViewById(R.id.txtMaSanPham1);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong1);
            txtTenSanPham = itemView.findViewById(R.id.txtTenSanPham1);
        }}
}

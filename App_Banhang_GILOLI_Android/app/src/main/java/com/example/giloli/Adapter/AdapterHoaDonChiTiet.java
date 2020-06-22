package com.example.giloli.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.giloli.Model.HoaDonCtSP;
import com.example.giloli.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class AdapterHoaDonChiTiet extends RecyclerView.Adapter<AdapterHoaDonChiTiet.ViewHolder> {
    private Context context;
    private List<HoaDonCtSP> hoaDonCtSPS;
    public AdapterHoaDonChiTiet(Context context, List<HoaDonCtSP> hoaDonCtSPS){
        this.context = context;
        this.hoaDonCtSPS = hoaDonCtSPS;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.cardview_hoadonchitiet, parent, false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HoaDonCtSP hoaDonCtSP = hoaDonCtSPS.get(position);
        if (hoaDonCtSP!=null){
            Glide.with(context).load(hoaDonCtSP.getUrlImage()).into(holder.imageView);
            holder.txtTenSanPham.setText(hoaDonCtSP.getTenSp());
            NumberFormat numberFormat = new DecimalFormat("#,###");
            String giaTien = numberFormat.format(Double.parseDouble(hoaDonCtSP.getGiaSp()));
            holder.txtGiaSanPham.setText(giaTien + " đ");
        }
    }

    @Override
    public int getItemCount() {
        return hoaDonCtSPS.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTenSanPham, txtGiaSanPham;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenSanPham = itemView.findViewById(R.id.txtTenSanPham);
            txtGiaSanPham = itemView.findViewById(R.id.txtGiaSanPham);
            imageView = itemView.findViewById(R.id.imgSanPham);
        }}
}

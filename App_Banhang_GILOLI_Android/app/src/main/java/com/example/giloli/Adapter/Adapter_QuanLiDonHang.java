package com.example.giloli.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giloli.Activity.HienHoaDonChiTietActivity;
import com.example.giloli.DAO.HoaDonDAO;
import com.example.giloli.Model.HoaDon;
import com.example.giloli.R;

import java.util.List;

public class Adapter_QuanLiDonHang extends RecyclerView.Adapter<Adapter_QuanLiDonHang.ViewHolder> {
    private Context context;
    private List<HoaDon> list;
    public Adapter_QuanLiDonHang(Context context, List<HoaDon> list){
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_quanlidonhang,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final HoaDon hoaDon = list.get(position);
        if (hoaDon!=null){
            holder.maHoaDon.setText(String.valueOf(hoaDon.getMaHoaDon()));
            holder.ngayDatHang.setText(hoaDon.getDateHoaDon());
            holder.trangThai.setText(hoaDon.getTrangThai());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, HienHoaDonChiTietActivity.class);
                    Bundle bundle =  new Bundle();
                    bundle.putString("maHoaDon",hoaDon.getMaHoaDon());
                    intent.putExtra("box",bundle);
                    intent.setAction("FromAdapter");
                    context.startActivity(intent);
                }
            });
            if (hoaDon.getTrangThai().equals("Chờ duyệt đơn")){
                holder.imageView.setVisibility(View.VISIBLE);
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final AlertDialog.Builder builder=new AlertDialog.Builder(context,android.R.style.Theme_DeviceDefault_Light_Dialog);
                        builder.setTitle("Huỷ đơn hàng");
                        builder.setMessage("Bạn có chắc chắn huỷ đơn");
                        builder.setIcon(R.drawable.ic_delete_alert);
                        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int w) {
                                hoaDon.setTrangThai("Yêu cầu huỷ đơn hàng");
                                HoaDonDAO hoaDonDAO = new HoaDonDAO(context);
                                hoaDonDAO.capNhatHoaDon(hoaDon);
                                Toast.makeText(context,"Chờ duyệt huỷ đơn hàng",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });builder.show();
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView maHoaDon, ngayDatHang, trangThai;
        private CardView cardView;
        private ImageView imageView;
        public ViewHolder(View itemview){
            super(itemview);
            maHoaDon = itemview.findViewById(R.id.txtMaDonHang1);
            ngayDatHang = itemview.findViewById(R.id.txtNgayDatHang1);
            trangThai = itemview.findViewById(R.id.txtTrangThai1);
            cardView = itemview.findViewById(R.id.cardviewDonHang);
            imageView = itemview.findViewById(R.id.imgHuyDon);

        }
    }
}

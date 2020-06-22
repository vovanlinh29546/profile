package com.example.giloliadmin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giloliadmin.DAO.SanPhamDAO;
import com.example.giloliadmin.Edit_Activity.suaSanPham_activity;
import com.example.giloliadmin.MainActivity;
import com.example.giloliadmin.Model.SanPham;
import com.example.giloliadmin.Model.TheLoai;
import com.example.giloliadmin.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class sanphamAdapter extends RecyclerView.Adapter<sanphamAdapter.ViewHolder> {
    ArrayList<SanPham> danhsachsanpham;
    Context context;
    SanPhamDAO sanPhamDAO;
    public static View.OnClickListener listener;

    public sanphamAdapter(ArrayList<SanPham> danhsachsanpham, Context context) {
        this.danhsachsanpham = danhsachsanpham;
        this.context = context;
    }

    @NonNull
    @Override
    public sanphamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemview = inflater.inflate(R.layout.cardview_sanpham, viewGroup, false);
        return new ViewHolder(itemview);
    }
    @Override
    public void onBindViewHolder(@NonNull sanphamAdapter.ViewHolder viewHolder, final int position) {
        final SanPham sanPham=danhsachsanpham.get(position);
        final int i = position + 1;
        viewHolder.txtstt.setText(i + "");
        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedNumber = formatter.format(Double.parseDouble(danhsachsanpham.get(position).getGiaThanh()));
        viewHolder.txtmasanpham.setText(danhsachsanpham.get(position).getMaSanPham());
        viewHolder.txttensanpham.setText(danhsachsanpham.get(position).getTenSanPham());
        viewHolder.txttonkho.setText(danhsachsanpham.get(position).getSoLuong() + "");
        viewHolder.txtgia.setText(formattedNumber+" đ");
        viewHolder.popup_sanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.popup_sua_xoa, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Xóa ================================================
                        switch (item.getItemId()) {
                            case R.id.action_delete:
                                AlertDialog.Builder builder = new AlertDialog.Builder(context, android.R.style.Theme_DeviceDefault_Light_Dialog);
                                builder.setTitle("Xóa");
                                builder.setMessage("Xác nhận xóa sản phẩm " + "\"" + sanPham.getTenSanPham() + "\"\n");
                                builder.setIcon(R.drawable.ic_delete_alert);
                                builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int w) {
                                        sanPhamDAO = new SanPhamDAO(context);
                                        sanPhamDAO.xoaSanPham(sanPham);
                                        Toast.makeText(context, "Đã xóa \"" + danhsachsanpham.get(position).getTenSanPham() + "\"", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int w) {

                                    }
                                });
                                builder.show();
                                break;
                            // Cập nhật =========================================
                            case R.id.action_edit:
                            {
                                Intent intent = new Intent(context, suaSanPham_activity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("edtmaloai", sanPham.getMaLoai());
                                bundle.putString("edtmasanpham", sanPham.getMaSanPham());
                                bundle.putString("edttensanpham", sanPham.getTenSanPham());
                                bundle.putInt("edtsoluong", sanPham.getSoLuong());
                                bundle.putString("edtgia", sanPham.getGiaThanh());
                                bundle.putString("edtmota", sanPham.getMoTa());
                                bundle.putString("edttrongluong", sanPham.getTrongLuong());
                                bundle.putString("edtnhacungcap", sanPham.getNhaCungCap());
                                bundle.putString("edtthuonghieu", sanPham.getThuongHieu());
                                bundle.putString("edthuongdan", sanPham.getHuongDan());
                                intent.putExtra("SuaSanPham", bundle);
                                ((MainActivity) context).startActivity(intent);
                                break;
                            } }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return danhsachsanpham.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtmasanpham, txtstt, txttensanpham, txttonkho, txtgia;
        private ImageView popup_sanpham;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtmasanpham = (TextView) itemView.findViewById(R.id.masanpham_1row);
            txtstt = (TextView) itemView.findViewById(R.id.stt_sanpham);
            txttensanpham = (TextView) itemView.findViewById(R.id.tensanpham_1row);
            txttonkho = (TextView) itemView.findViewById(R.id.tonkho_1row);
            txtgia = (TextView) itemView.findViewById(R.id.giatien_1row);
            popup_sanpham = (ImageView) itemView.findViewById(R.id.popup_sanpham);
        }}
    }

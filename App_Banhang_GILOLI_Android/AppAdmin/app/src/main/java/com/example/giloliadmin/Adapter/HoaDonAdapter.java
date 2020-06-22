package com.example.giloliadmin.Adapter;


import android.content.Context;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giloliadmin.DAO.HoaDonCtDAO;
import com.example.giloliadmin.DAO.HoaDonDAO;
import com.example.giloliadmin.HienChiTietHoaDonActivity;
import com.example.giloliadmin.Model.HoaDon;
import com.example.giloliadmin.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.ViewHolder> {
    private Context context;
    private List<HoaDon> list;

    public HoaDonAdapter(Context context,List<HoaDon> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.cardviewhoadon, parent, false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final HoaDon hoaDon = list.get(position);
        holder.txtTenHd.setText(hoaDon.getTenNguoiNhan());
        holder.txtDiaChiHd.setText(hoaDon.getDiaChiNguoiNhan());
        holder.txtSodt.setText(hoaDon.getSdtNguoiNhan());
        holder.txtNgay.setText(hoaDon.getDateHoaDon());
        holder.txtPhuongThuc.setText(hoaDon.getPhuongThuc());
        holder.txtTrangThai.setText(hoaDon.getTrangThai());
        NumberFormat numberFormat = new DecimalFormat("#,###");
        String tien = numberFormat.format(Double.parseDouble(hoaDon.getTongCong()));
        holder.txtTongCong.setText(tien+" đ");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HienChiTietHoaDonActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("maHoaDon",hoaDon.getMaHoaDon());
                intent.putExtra("box",bundle);
                context.startActivity(intent);
            }
        });
        holder.editHd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context,view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.popup_hoa_don, popupMenu.getMenu());
                final HoaDonDAO hoaDonDAO = new HoaDonDAO(context);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.action_choDuyet:
                                holder.txtTrangThai.setText("Chờ duyệt đơn");
                                hoaDon.setMaHoaDon(hoaDon.getMaHoaDon());
                                hoaDon.setTenNguoiNhan(hoaDon.getTenNguoiNhan());
                                hoaDon.setDiaChiNguoiNhan(hoaDon.getDiaChiNguoiNhan());
                                hoaDon.setSdtNguoiNhan(hoaDon.getSdtNguoiNhan());
                                hoaDon.setDateHoaDon(hoaDon.getDateHoaDon());
                                hoaDon.setPhuongThuc(hoaDon.getPhuongThuc());
                                hoaDon.setTrangThai(holder.txtTrangThai.getText().toString());
                                hoaDon.setTongCong(hoaDon.getTongCong());
                                hoaDon.setDonViVanChuyen(hoaDon.getDonViVanChuyen());
                                hoaDon.setEmailNguoiDung(hoaDon.getEmailNguoiDung());
                                hoaDonDAO.suaHoaDon(hoaDon);
                                break;
                            case R.id.action_daDuyet:
                                holder.txtTrangThai.setText("Đã duyệt đơn");
                                hoaDon.setMaHoaDon(hoaDon.getMaHoaDon());
                                hoaDon.setTenNguoiNhan(hoaDon.getTenNguoiNhan());
                                hoaDon.setDiaChiNguoiNhan(hoaDon.getDiaChiNguoiNhan());
                                hoaDon.setSdtNguoiNhan(hoaDon.getSdtNguoiNhan());
                                hoaDon.setDateHoaDon(hoaDon.getDateHoaDon());
                                hoaDon.setPhuongThuc(hoaDon.getPhuongThuc());
                                hoaDon.setTrangThai(holder.txtTrangThai.getText().toString());
                                hoaDon.setTongCong(hoaDon.getTongCong());
                                hoaDon.setDonViVanChuyen(hoaDon.getDonViVanChuyen());
                                hoaDon.setEmailNguoiDung(hoaDon.getEmailNguoiDung());
                                hoaDonDAO.suaHoaDon(hoaDon);
                                break;
                            case R.id.action_giaoHang:
                                holder.txtTrangThai.setText("Đang vận chuyển");
                                hoaDon.setMaHoaDon(hoaDon.getMaHoaDon());
                                hoaDon.setTenNguoiNhan(hoaDon.getTenNguoiNhan());
                                hoaDon.setDiaChiNguoiNhan(hoaDon.getDiaChiNguoiNhan());
                                hoaDon.setSdtNguoiNhan(hoaDon.getSdtNguoiNhan());
                                hoaDon.setDateHoaDon(hoaDon.getDateHoaDon());
                                hoaDon.setPhuongThuc(hoaDon.getPhuongThuc());
                                hoaDon.setTrangThai(holder.txtTrangThai.getText().toString());
                                hoaDon.setTongCong(hoaDon.getTongCong());
                                hoaDon.setDonViVanChuyen(hoaDon.getDonViVanChuyen());
                                hoaDon.setEmailNguoiDung(hoaDon.getEmailNguoiDung());
                                hoaDonDAO.suaHoaDon(hoaDon);
                                break;
                            case R.id.action_huyDonHang:
                                holder.txtTrangThai.setText("Đã huỷ đơn hàng");
                                hoaDon.setMaHoaDon(hoaDon.getMaHoaDon());
                                hoaDon.setTenNguoiNhan(hoaDon.getTenNguoiNhan());
                                hoaDon.setDiaChiNguoiNhan(hoaDon.getDiaChiNguoiNhan());
                                hoaDon.setSdtNguoiNhan(hoaDon.getSdtNguoiNhan());
                                hoaDon.setDateHoaDon(hoaDon.getDateHoaDon());
                                hoaDon.setPhuongThuc(hoaDon.getPhuongThuc());
                                hoaDon.setTrangThai(holder.txtTrangThai.getText().toString());
                                hoaDon.setTongCong(hoaDon.getTongCong());
                                hoaDon.setDonViVanChuyen(hoaDon.getDonViVanChuyen());
                                hoaDon.setEmailNguoiDung(hoaDon.getEmailNguoiDung());
                                hoaDonDAO.suaHoaDon(hoaDon);
                                break;
                            case R.id.action_daGiaoHang:
                                holder.txtTrangThai.setText("Đã vận chuyển");
                                hoaDon.setMaHoaDon(hoaDon.getMaHoaDon());
                                hoaDon.setTenNguoiNhan(hoaDon.getTenNguoiNhan());
                                hoaDon.setDiaChiNguoiNhan(hoaDon.getDiaChiNguoiNhan());
                                hoaDon.setSdtNguoiNhan(hoaDon.getSdtNguoiNhan());
                                hoaDon.setDateHoaDon(hoaDon.getDateHoaDon());
                                hoaDon.setPhuongThuc(hoaDon.getPhuongThuc());
                                hoaDon.setTrangThai(holder.txtTrangThai.getText().toString());
                                hoaDon.setTongCong(hoaDon.getTongCong());
                                hoaDon.setDonViVanChuyen(hoaDon.getDonViVanChuyen());
                                hoaDon.setEmailNguoiDung(hoaDon.getEmailNguoiDung());
                                hoaDonDAO.suaHoaDon(hoaDon);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTenHd, txtDiaChiHd, txtSodt, txtPhuongThuc, txtNgay, txtTrangThai, txtTongCong ;
        private ImageView editHd;
        private CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenHd = itemView.findViewById(R.id.txtTenNguoiNhanHD);
            txtDiaChiHd = itemView.findViewById(R.id.txtDiaChiHD);
            txtSodt = itemView.findViewById(R.id.txtSoDienThoaiHD);
            txtPhuongThuc = itemView.findViewById(R.id.txtPtvcHD);
            txtNgay = itemView.findViewById(R.id.txtNgayHD);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThaiHD);
            txtTongCong = itemView.findViewById(R.id.txtGiaTienHD);
            editHd = itemView.findViewById(R.id.imgWritting);
            cardView = itemView.findViewById(R.id.cardviewHoaDon);

        }}

}

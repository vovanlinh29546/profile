package com.example.giloli.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.giloli.Activity.GioHangActivity;
import com.example.giloli.DAO.GioHangDAO;
import com.example.giloli.MainActivity;
import com.example.giloli.Model.GioHang;
import com.example.giloli.Model.GioHangDifff;
import com.example.giloli.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class GioHangAdaptercv extends RecyclerView.Adapter<GioHangAdaptercv.ViewHolder> {
    Context context;
    ArrayList<GioHang> dulieund;
    GioHangDAO dao;

    public GioHangAdaptercv(Context context, ArrayList<GioHang> dulieund) {
        this.context = context;
        this.dulieund = dulieund;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tenhang,giahang,soluong;
        public ImageView urlhang,exit;
        ImageButton add,minus;

        public ViewHolder(View itemview){
            super(itemview);
            tenhang=itemview.findViewById(R.id.txtGioHang_TenHang);
            giahang=itemview.findViewById(R.id.txtGioHang_GiaTien);
            urlhang=itemview.findViewById(R.id.img_item_giohang);
            exit=itemview.findViewById(R.id.img_item_xoahang);
            soluong=itemview.findViewById(R.id.txt_GioHang_SoLuong);
            add=itemview.findViewById(R.id.img_item_add);
            minus=itemview.findViewById(R.id.img_item_minus);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_giohang,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
      final GioHang nd=dulieund.get(i);
        dao = new GioHangDAO(context);
        NumberFormat formatter = new DecimalFormat("#,###");
        double tienhang;

      if (nd!=null){
          viewHolder.tenhang.setText(nd.getTenHang());
          tienhang= Double.parseDouble(nd.getGiaTien());
          String formattedNumber = formatter.format(tienhang);
          viewHolder.giahang.setText(formattedNumber+" đ");
          Glide.with(context).asBitmap().load(nd.getUrlhang()).into(viewHolder.urlhang);
          viewHolder.soluong.setText(nd.getSoLuong()+"");
      }
        viewHolder.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder=new AlertDialog.Builder(context,android.R.style.Theme_DeviceDefault_Light_Dialog);
                builder.setTitle("Xóa");
                builder.setMessage("Xác nhận xóa giỏ hàng" + "\"" + nd.getTenHang() + "\"");
                builder.setIcon(R.drawable.ic_delete_alert);
                builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int w) {

                        dao = new GioHangDAO(context);
                        dao.deleteGioHang(nd);
                        Toast.makeText(context, "Đã xóa địa chỉ: " + nd.getTenHang(), Toast.LENGTH_SHORT).show();
                        dulieund.remove(nd); // cach nay hay hon it ton du lieu
                        GioHangAdaptercv.this.notifyDataSetChanged();
                       GioHangActivity.anhienrl();


                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int w) {

                    }
                });
                builder.show();
            }
        });
      // ;
int sldebiet= Integer.parseInt(nd.getSoLuong());
if (sldebiet<=1){
    viewHolder.minus.setVisibility(View.INVISIBLE);
}
else {
    viewHolder.add.setVisibility(View.VISIBLE);
    viewHolder.minus.setVisibility(View.VISIBLE);
}


      viewHolder.add.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              final int slmn= Integer.parseInt(viewHolder.soluong.getText().toString())+1;
              int slht = Integer.parseInt(nd.getSoLuong());
              DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
long giaht=Long.parseLong(nd.getGiaTien());
long giamn=(giaht*slmn)/slht;
              GioHang gh = new GioHang(  nd.getIdGioiHang(),nd.getEmail(),nd.getTenHang(),String.valueOf(slmn),nd.getUrlhang(),String.valueOf(giamn),nd.getMaSp());
              dao.updateGioHang(gh);

              viewHolder.soluong.setText(String.valueOf(slmn));
              viewHolder.giahang.setText(decimalFormat.format(giamn)+"đ");
              if (slmn>=2){
                  viewHolder.minus.setVisibility(View.VISIBLE);
              }
              else {
                  viewHolder.minus.setVisibility(View.VISIBLE);
              }
              GioHangActivity.anhienrl();





          }
      });

        viewHolder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
                final int slmn= Integer.parseInt(viewHolder.soluong.getText().toString())-1;
                int slht = Integer.parseInt(nd.getSoLuong());
                long giaht=Long.parseLong(nd.getGiaTien());
                long giamn=(giaht*slmn)/slht;
                if (slmn<2){
                    viewHolder.minus.setVisibility(View.INVISIBLE);
                }
                else {
                    viewHolder.minus.setVisibility(View.VISIBLE);
                }
                GioHang gh = new GioHang(  nd.getIdGioiHang(),nd.getEmail(),nd.getTenHang(),String.valueOf(slmn),nd.getUrlhang(),String.valueOf(giamn),nd.getMaSp());
                dao.updateGioHang(gh);
                viewHolder.soluong.setText(String.valueOf(slmn));
                viewHolder.giahang.setText(decimalFormat.format(giamn)+"đ");
                GioHangActivity.anhienrl();


            }
        });


    }


    @Override
    public int getItemCount() {
        return dulieund.size();
    }



}


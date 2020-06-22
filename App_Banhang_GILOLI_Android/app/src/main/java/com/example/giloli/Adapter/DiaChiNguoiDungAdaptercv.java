package com.example.giloli.Adapter;

import android.app.Activity;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giloli.Activity.DatHangActivity;
import com.example.giloli.Activity.ThemSoDiaChi;
import com.example.giloli.DAO.AddDiaChiNguoiDungDao;
import com.example.giloli.Model.DiaChiNguoiDung;
import com.example.giloli.R;


import java.util.ArrayList;

public class DiaChiNguoiDungAdaptercv extends RecyclerView.Adapter<DiaChiNguoiDungAdaptercv.ViewHolder> {
    Context context;
    ArrayList<DiaChiNguoiDung> dulieund;
    AddDiaChiNguoiDungDao dao;

    public DiaChiNguoiDungAdaptercv(Context context, ArrayList<DiaChiNguoiDung> dulieund) {
        this.context = context;
        this.dulieund = dulieund;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView hoten,diachi,sdt;
        public ImageView popupxoasua;
        private CardView cardView;
        private Activity mActivity;
        public ViewHolder(View itemview, Activity activity){
            super(itemview);
            popupxoasua=itemview.findViewById(R.id.popupxoasuadc);
            hoten=itemview.findViewById(R.id.txtDiaChi_Hoten);
            diachi=itemview.findViewById(R.id.txtDiaChi_Diachi);
            sdt=itemview.findViewById(R.id.txtDiaChi_Sdt);
            cardView = itemview.findViewById(R.id.cardviewDC);
            this.mActivity = activity;

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_diachi_done,viewGroup,false);
        return new ViewHolder(v,(Activity)context);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
      final DiaChiNguoiDung nd=dulieund.get(i);
      if (nd!=null){
          viewHolder.hoten.setText(nd.getHoten());
          viewHolder.diachi.setText(nd.getDiaChi());
          viewHolder.sdt.setText(nd.getSoDienThoai());
          viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      Intent returIntent = new Intent();
                          returIntent.putExtra("resutl",i);
                          viewHolder.mActivity.setResult(Activity.RESULT_OK,returIntent);
                          viewHolder.mActivity.finish();
                  }
              });


      }
        viewHolder.popupxoasua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup=new PopupMenu(v.getContext(),v);// co the de view thay cho text view van dc
                MenuInflater inflater=popup.getMenuInflater();
                inflater.inflate(R.menu.popup_sua_xoa_dc,popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                                case R.id.action_deletedc:
                                final AlertDialog.Builder builder=new AlertDialog.Builder(context,android.R.style.Theme_DeviceDefault_Light_Dialog);
                                builder.setTitle("Xóa");
                                builder.setMessage("Xác nhận xóa địa chỉ" + "\"" + nd.getHoten() + "\"");
                                builder.setIcon(R.drawable.ic_delete_alert);
                                builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int w) {

                                        dao = new AddDiaChiNguoiDungDao(context);
                                        dao.deleteDiaChiDone(nd);
                                        Toast.makeText(context, "Đã xóa địa chỉ: " + nd.getDiaChi(), Toast.LENGTH_SHORT).show();
                                        dulieund.remove(nd); // cach nay hay hon it ton du lieu
                                        DiaChiNguoiDungAdaptercv.this.notifyDataSetChanged();
                                    }
                                });
                                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int w) {

                                    }
                                });
                                builder.show();
                                break;
                            case R.id.action_editdc:

                                Intent intent = new Intent(context, ThemSoDiaChi.class);

                                Bundle bundle = new Bundle();
                                bundle.putInt("id",nd.getiDDiaChiDone());
                                bundle.putString("ht",nd.getHoten());
                                bundle.putString("sdt",nd.getSoDienThoai());
                                bundle.putString("quan",nd.getTenQuan());
                                bundle.putString("px",nd.getTenPhuong());
                                bundle.putString("dc",nd.getDiaChi());

                                intent.putExtra("suaThongTin",bundle);

                                context.startActivity(intent);
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


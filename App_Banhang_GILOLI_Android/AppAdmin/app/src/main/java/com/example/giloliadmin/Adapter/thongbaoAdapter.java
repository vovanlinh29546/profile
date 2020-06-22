package com.example.giloliadmin.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.giloliadmin.DAO.ThongBaoDAO;
import com.example.giloliadmin.Edit_Activity.suaLoai_activity;
import com.example.giloliadmin.Edit_Activity.suaThongBao_activity;
import com.example.giloliadmin.MainActivity;
import com.example.giloliadmin.Model.ThongBao;
import com.example.giloliadmin.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class thongbaoAdapter extends RecyclerView.Adapter<thongbaoAdapter.ImageViewHolder> {

    Context mContext;
    ThongBaoDAO thongBaoDAO;
    ArrayList<ThongBao> mUploads;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    public thongbaoAdapter(ArrayList<ThongBao> listthongbao, Context context){
        mContext = context;
        mUploads = listthongbao;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_thongbao, parent, false);
        return  new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, final int position) {
        storageReference = FirebaseStorage.getInstance().getReference("ThongBao");
        databaseReference = FirebaseDatabase.getInstance().getReference("ThongBao");
        final ThongBao thongbaocurrent = mUploads.get(position);
        holder.tv_tieude.setText(thongbaocurrent.getTieude());
        Glide.with( mContext ).load(thongbaocurrent.getUrlanhthongbao()).centerCrop().into(holder.imv_anhthongbao);
        holder.popup_thongbao.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(mContext, view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.popup_sua_xoa, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener( new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.action_delete:
                                AlertDialog.Builder builder=new AlertDialog.Builder(mContext,android.R.style.Theme_DeviceDefault_Light_Dialog);
                                builder.setTitle("Xóa");
                                builder.setMessage("Xác nhận xóa thông báo " + "\"" + thongbaocurrent.getTieude() + "\"");
                                builder.setIcon(R.drawable.ic_delete_alert);
                                builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int w) {
                                        thongBaoDAO = new ThongBaoDAO(mContext);
                                        thongBaoDAO.deleteThongBao(thongbaocurrent);
                                        Toast.makeText(mContext, "Đã xóa \"" + mUploads.get(position).getTieude() + "\"", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int w) {
                                    }
                                });
                                builder.show();
                                break;
                            // Sửa =========================================================
                            case R.id.action_edit:
                            {
                                Intent intent = new Intent(mContext, suaThongBao_activity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("edtma", thongbaocurrent.getMathongbao());
                                bundle.putString("edttieude", thongbaocurrent.getTieude());
                                bundle.putString("edtnoidung", thongbaocurrent.getNoidung());
                                bundle.putString("imv_anhthongbao", thongbaocurrent.getUrlanhthongbao());
                                intent.putExtra("Suathongbao", bundle);
                                ((MainActivity) mContext).startActivity(intent);
                                break;
                            }}
                        return false;
                    }});
                popupMenu.show();
            }});
    }
    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_tieude;
        public  ImageView imv_anhthongbao, popup_thongbao;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tieude = itemView.findViewById(R.id.tv_tieude);
            imv_anhthongbao = itemView.findViewById(R.id.imv_thongbao);
            popup_thongbao = itemView.findViewById( R.id.popup_thongbao );
        }}
}

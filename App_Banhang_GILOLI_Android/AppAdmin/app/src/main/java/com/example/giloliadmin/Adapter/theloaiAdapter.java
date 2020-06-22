package com.example.giloliadmin.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.giloliadmin.DAO.SanPhamDAO;
import com.example.giloliadmin.DAO.TheLoaiDAO;
import com.example.giloliadmin.Edit_Activity.suaLoai_activity;
import com.example.giloliadmin.MainActivity;
import com.example.giloliadmin.Model.TheLoai;
import com.example.giloliadmin.R;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

public class theloaiAdapter extends RecyclerView.Adapter<theloaiAdapter.ViewHolder> {
    ArrayList<TheLoai> danhsachtheloai;
    Context context;
    public static View.OnClickListener listener;
    TheLoaiDAO theLoaiDAO;
    SanPhamDAO sanPhamDAO;
    StorageReference storageReference;
    DatabaseReference databaseReference;


    public theloaiAdapter(ArrayList<TheLoai> danhsachtheloai, Context context) {
        this.danhsachtheloai = danhsachtheloai;
        this.context = context;
    }

    @NonNull
    @Override
    public theloaiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemview = inflater.inflate(R.layout.cardview_theloai, viewGroup, false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull theloaiAdapter.ViewHolder viewHolder, final int position) {
        storageReference = FirebaseStorage.getInstance().getReference("TheLoai");
        databaseReference = FirebaseDatabase.getInstance().getReference("TheLoai");
        final TheLoai theLoai=danhsachtheloai.get(position);
        final int i = position + 1;
        viewHolder.txtstt.setText(i + "");
        viewHolder.txttentheloai.setText(danhsachtheloai.get(position).getTenLoai());
        viewHolder.txtmatheloai.setText(danhsachtheloai.get(position).getMaLoai());

        viewHolder.popup_theloai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.popup_sua_xoa, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Xóa ==============================================
                        switch (item.getItemId()){
                            case R.id.action_delete:
                            AlertDialog.Builder builder=new AlertDialog.Builder(context,android.R.style.Theme_DeviceDefault_Light_Dialog);
                        builder.setTitle("Xóa");
                        builder.setMessage("Xác nhận xóa thể loại" + "\"" + theLoai.getTenLoai() + "\"" + "\nLưu ý: các sản phẩm trong loại cũng sẽ bị xóa!");
                        builder.setIcon(R.drawable.ic_delete_alert);
                        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int w) {
                                theLoaiDAO = new TheLoaiDAO(context);
                                theLoaiDAO.deleteCategoryBook(theLoai);
                                sanPhamDAO = new SanPhamDAO(context);
                                sanPhamDAO.xoaSanPhamTheoMaLoai(theLoai);
                                Toast.makeText(context, "Đã xóa thể loại \"" + danhsachtheloai.get(position).getTenLoai() + "\"", Toast.LENGTH_SHORT).show();
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
                            Intent intent = new Intent(context, suaLoai_activity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("edtten", theLoai.getTenLoai());
                            bundle.putString("edtma", theLoai.getMaLoai());
                            bundle.putString("url_imv", theLoai.getuRLAnh());
                            intent.putExtra("SuaLoai", bundle);
                            ((MainActivity) context).startActivity(intent);
                            break;
                        }}
                        return false;
                    }});
                popupMenu.show();
            }});
    }



    @Override
    public int getItemCount() {
        return danhsachtheloai.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txttentheloai, txtstt, txtmatheloai;
        private ImageView popup_theloai;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtmatheloai = (TextView) itemView.findViewById(R.id.matheloai_1row);
            txtstt = (TextView) itemView.findViewById(R.id.stt_theloai);
            txttentheloai = (TextView) itemView.findViewById(R.id.tentheloai_1row);
            popup_theloai = (ImageView) itemView.findViewById(R.id.popup_theloai);
        }}

    }

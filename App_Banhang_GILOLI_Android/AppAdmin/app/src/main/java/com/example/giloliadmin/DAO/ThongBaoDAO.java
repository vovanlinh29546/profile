package com.example.giloliadmin.DAO;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.giloliadmin.Edit_Activity.suaSanPham_activity;
import com.example.giloliadmin.Model.TheLoai;
import com.example.giloliadmin.Model.ThongBao;
import com.example.giloliadmin.fragment.PhanLoaiFragment;
import com.example.giloliadmin.fragment.SanPhamFragment;
import com.example.giloliadmin.fragment.ThongBaoFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ThongBaoDAO {
   private DatabaseReference mDatabase;
   Context context;
   String maTheLoai;
    public static List<ThongBao> list = new ArrayList<ThongBao>();

   public ThongBaoDAO(Context context){
       this.mDatabase = FirebaseDatabase.getInstance().getReference("ThongBao");
       this.context = context;

   }

    public List<ThongBao> layHetThongBao() {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                list.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    ThongBao s = data.getValue(ThongBao.class);
                    list.add(s); }
                ThongBaoFragment.capNhatAdapterThongBao(); }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        };
        mDatabase.addValueEventListener(listener);
        return list;
    }

    public List<ThongBao>getIDThongBao() {

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                list.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    ThongBao s = data.getValue(ThongBao.class);
                    list.add(s);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mDatabase.addValueEventListener(listener);
        return list;
    }

    public void updateThongBao(final ThongBao thongBao) {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("mathongbao").getValue(String.class).equals(thongBao.getMathongbao())){
                        String mathongbao = data.getKey();
                        mDatabase.child(mathongbao).setValue(thongBao)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }}); } } }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }


    public void deleteThongBao(final ThongBao thongBao){
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    if (data.child("mathongbao").getValue(String.class).equals( thongBao.getMathongbao())){
                        String mathongbao = data.getKey();
                        mDatabase.child(mathongbao).setValue(null)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) { }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }}); } } }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }});
    }
}

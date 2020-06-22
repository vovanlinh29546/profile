package com.example.giloli.DAO;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.giloli.Activity.ThemSoDiaChi;
import com.example.giloli.Model.DiaChi_QuanHuyen;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddQuanDao {
   private DatabaseReference mDatabase;
   Context context;
   String maQuan;
    public static List<DiaChi_QuanHuyen> dcq=new ArrayList<>();
   public AddQuanDao(Context context){
       this.mDatabase = FirebaseDatabase.getInstance().getReference("DiaChiQuanHuyen");
       this.context = context;
   }

    public List<DiaChi_QuanHuyen>getQuan() {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                dcq.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    DiaChi_QuanHuyen s = data.getValue(DiaChi_QuanHuyen.class);
                    dcq.add(s);
//
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(listener);
        return dcq;
    }
    public void addTenQuan(DiaChi_QuanHuyen quanHuyen){
        maQuan = mDatabase.push().getKey();
        mDatabase.child(maQuan).setValue(quanHuyen).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //  Toast.makeText(context,"Thêm thành công",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Toast.makeText(context,"Thêm thất bại",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public List<DiaChi_QuanHuyen>getTenQuan() {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                dcq.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    DiaChi_QuanHuyen s = data.getValue(DiaChi_QuanHuyen.class);
                    dcq.add(s);
//
                }
                ThemSoDiaChi.adapterTenQuan.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(listener);
        return dcq;
    }


}

package com.example.giloli.DAO;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.giloli.Activity.ThemSoDiaChi;
import com.example.giloli.Model.DiaChi_ThiXa;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddPhuongXaDao {
   private DatabaseReference mDatabase;
   Context context;
   String maPhuongXa;
    public static List<DiaChi_ThiXa> dct=new ArrayList<>();
   public AddPhuongXaDao(Context context){
       this.mDatabase = FirebaseDatabase.getInstance().getReference("DiaChi_ThiXa");
       this.context = context;
   }
    public List<DiaChi_ThiXa>getXa() {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                dct.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    DiaChi_ThiXa s = data.getValue(DiaChi_ThiXa.class);
                    dct.add(s);
//
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(listener);
        return dct;
    }
    public void addPhuongXa(DiaChi_ThiXa phuongxa){
        maPhuongXa = mDatabase.push().getKey();
        mDatabase.child(maPhuongXa).setValue(phuongxa).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    public List<DiaChi_ThiXa>layTenPhuongXaTheoTenQuantest(String tenquan) {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                dct.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    DiaChi_ThiXa tenpx = data.getValue(DiaChi_ThiXa.class);
                    dct.add(tenpx);
//
                }
                ThemSoDiaChi.adapterTenPhuongXa.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        Query query = FirebaseDatabase.getInstance().getReference("DiaChi_ThiXa")
                .orderByChild("tenQuan").equalTo(tenquan);
        query.addListenerForSingleValueEvent(listener);
        return dct;
    }


}

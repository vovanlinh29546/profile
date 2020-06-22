package com.example.giloli.DAO;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.giloli.Activity.ThemSoDiaChi;
import com.example.giloli.Activity.ThemSoDiaChiDone;
import com.example.giloli.Model.DiaChiNguoiDung;
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

public class AddDiaChiNguoiDungDao {
   private DatabaseReference mDatabase;
   Context context;
   String maDiaChiND;
    public static List<DiaChiNguoiDung> dct=new ArrayList<>();
   public AddDiaChiNguoiDungDao(Context context){
       this.mDatabase = FirebaseDatabase.getInstance().getReference("DiaChiNguoiDung");
       this.context = context;
   }
    public List<DiaChiNguoiDung>getDiaChiNguoiDung() {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                dct.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    DiaChiNguoiDung s = data.getValue(DiaChiNguoiDung.class);
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
    public void addDiaChiNguoiDung(DiaChiNguoiDung diachi){
        maDiaChiND = mDatabase.push().getKey();
        mDatabase.child(maDiaChiND).setValue(diachi).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    public List<DiaChiNguoiDung>layDiaChiTheoEmail(String email) {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                dct.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    DiaChiNguoiDung tenpx = data.getValue(DiaChiNguoiDung.class);
                    dct.add(tenpx);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        Query query = FirebaseDatabase.getInstance().getReference("DiaChiNguoiDung")
                .orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(listener);
        return dct;
    }
    public List<DiaChiNguoiDung>layDiaChiTheoEmailDone(String email) {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                dct.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    DiaChiNguoiDung tenpx = data.getValue(DiaChiNguoiDung.class);
                    dct.add(tenpx);
//
                }
                      ThemSoDiaChiDone.adapterdcdone.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        Query query = FirebaseDatabase.getInstance().getReference("DiaChiNguoiDung")
                .orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(listener);
        return dct;
    }

    public void deleteDiaChiDone(final DiaChiNguoiDung diachi){
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    if (data.child("iDDiaChiDone").getValue(Integer.class).equals(diachi.getiDDiaChiDone())){
                        maDiaChiND = data.getKey();
                        mDatabase.child(maDiaChiND).setValue(null)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) { }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
                    } }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void updateDiaChiND(final DiaChiNguoiDung nguoidung) {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("iDDiaChiDone").getValue(Integer.class).equals(nguoidung.getiDDiaChiDone())) {
                        maDiaChiND = data.getKey();
                        mDatabase.child(maDiaChiND).setValue(nguoidung)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}

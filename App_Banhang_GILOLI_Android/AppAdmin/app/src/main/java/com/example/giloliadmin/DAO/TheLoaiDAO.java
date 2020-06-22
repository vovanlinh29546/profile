package com.example.giloliadmin.DAO;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.giloliadmin.Edit_Activity.suaLoai_activity;
import com.example.giloliadmin.Edit_Activity.suaSanPham_activity;
import com.example.giloliadmin.Model.TheLoai;
import com.example.giloliadmin.fragment.PhanLoaiFragment;
import com.example.giloliadmin.fragment.SanPhamFragment;
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

public class TheLoaiDAO {
   private DatabaseReference mDatabase;
   Context context;
   String maTheLoai;
    public static List<TheLoai> list = new ArrayList<TheLoai>();

   public TheLoaiDAO(Context context){
       this.mDatabase = FirebaseDatabase.getInstance().getReference("TheLoai");
       this.context = context;

   }

    public List<TheLoai> layHetTheLoai() {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                list.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    TheLoai s = data.getValue(TheLoai.class);
                    list.add(s);
                }
                PhanLoaiFragment.capNhatAdapterTheLoai();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mDatabase.addValueEventListener(listener);
        return list;
    }

    public void themTheLoai(TheLoai theLoai) {
        maTheLoai = mDatabase.push().getKey();
        mDatabase.child(maTheLoai).setValue(theLoai).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public ArrayList<String> layMaLoai() {
        final ArrayList<String> arrayList = new ArrayList<String>();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s = null;
                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    TheLoai theLoai = snapshot.getValue(TheLoai.class);
                    s = theLoai.getMaLoai();
                    s += " - " + theLoai.getTenLoai();
                    arrayList.add(s); }
                 SanPhamFragment.adapter.notifyDataSetChanged(); }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        mDatabase.addValueEventListener(valueEventListener);
        return arrayList;
    }
    public ArrayList<String> layMaLoai1() {
        final ArrayList<String> arrayList = new ArrayList<String>();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s = null;
                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    TheLoai theLoai = snapshot.getValue(TheLoai.class);
                    s = theLoai.getMaLoai();
                    s += " - " + theLoai.getTenLoai();
                    arrayList.add(s);
                }
                suaSanPham_activity.capNhatAdapterSpinnerSua();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(valueEventListener);
        return arrayList;
    }

    public void updateTheLoai(final TheLoai theLoai) {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("maLoai").getValue(String.class).equals(theLoai.getMaLoai())) {
                        maTheLoai = data.getKey();
                        mDatabase.child(maTheLoai).setValue(theLoai)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) { }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {}
                        });
                    } } }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }




    public void deleteCategoryBook(final TheLoai theLoai){
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    if (data.child("maLoai").getValue(String.class).equals(theLoai.getMaLoai())){
                        maTheLoai = data.getKey();
                        mDatabase.child(maTheLoai).setValue(null)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) { }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }});}} }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });}
}

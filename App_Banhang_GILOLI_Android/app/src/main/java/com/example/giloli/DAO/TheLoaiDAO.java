package com.example.giloli.DAO;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.giloli.Model.TheLoai;
import com.example.giloli.fragment.DanhMucFragment;
import com.example.giloli.fragment.TrangChuFragment;
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
        list = new ArrayList<TheLoai>();
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                list.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    TheLoai s = data.getValue(TheLoai.class);
                    list.add(s);
                }
                DanhMucFragment.capNhatAdapter();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mDatabase.addValueEventListener(listener);
        return list;
    }

    public List<TheLoai> layHetTheLoai1() {
        list = new ArrayList<TheLoai>();
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                list.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    TheLoai s = data.getValue(TheLoai.class);
                    list.add(s); }
                TrangChuFragment.capNhatAdapter();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(listener);
        return list;
    }
    public void layDanhMuc(String maLoai, final TextView textView) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    TheLoai theLoai = snapshot.getValue(TheLoai.class);
                    textView.setText(theLoai.getTenLoai()); }}
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }};
        Query query = FirebaseDatabase.getInstance().getReference("TheLoai")
                .orderByChild("maLoai").equalTo(maLoai);
        query.addListenerForSingleValueEvent(valueEventListener);
    }




}

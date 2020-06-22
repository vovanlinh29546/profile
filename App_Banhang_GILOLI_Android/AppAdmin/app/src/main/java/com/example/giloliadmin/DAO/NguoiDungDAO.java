package com.example.giloliadmin.DAO;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.giloliadmin.Model.NguoiDung;
import com.example.giloliadmin.fragment.KhachHangFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NguoiDungDAO {
   private DatabaseReference mDatabase;
   Context context;
   String maNguoiDung;

   public NguoiDungDAO(Context context){
       this.mDatabase = FirebaseDatabase.getInstance().getReference("NguoiDung");
       this.context = context;
   }
    public List<NguoiDung>getNguoiDung() {
      final List<NguoiDung> nguoidungs=new ArrayList<NguoiDung>();
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                nguoidungs.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    NguoiDung s = data.getValue(NguoiDung.class);
                    nguoidungs.add(s); }
                KhachHangFragment.capnhatLV();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mDatabase.addValueEventListener(listener);
        return nguoidungs;
    }


}

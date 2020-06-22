package com.example.giloli.DAO;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.giloli.Activity.ChiTietSPActivity;
import com.example.giloli.Activity.NhanXetCuaToiActivity;
import com.example.giloli.Activity.NhanxetActivity;
import com.example.giloli.Activity.ThemSoDiaChi;
import com.example.giloli.Activity.XemTatCaNhanXet;
import com.example.giloli.Model.DiaChi_QuanHuyen;
import com.example.giloli.Model.NhanXet;
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

public class NhanXetDao {
   private DatabaseReference mDatabase;
   Context context;
   String maNX;
    public static List<NhanXet> dcq=new ArrayList<>();
   public NhanXetDao(Context context){
       this.mDatabase = FirebaseDatabase.getInstance().getReference("NhanXet");
       this.context = context;
   }

    public List<NhanXet>getAllNhanXet() {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                dcq.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    NhanXet s = data.getValue(NhanXet.class);
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
    public void addNhanXet(NhanXet nhanXet){
        maNX = mDatabase.push().getKey();
        mDatabase.child(maNX).setValue(nhanXet).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public List<NhanXet>getNhanXetTheoMaSP(String masp) {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                dcq.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    NhanXet s = data.getValue(NhanXet.class);
                    dcq.add(s);
//
                }

                XemTatCaNhanXet.adapternhanxet.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        Query query = FirebaseDatabase.getInstance().getReference("NhanXet")
                .orderByChild("masp").equalTo(masp);
        query.addListenerForSingleValueEvent(listener);
        return dcq;
    }
    public List<NhanXet>getNhanXetTheoMaSPstatic(String masp) {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                dcq.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    NhanXet s = data.getValue(NhanXet.class);
                    dcq.add(s);
//
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        Query query = FirebaseDatabase.getInstance().getReference("NhanXet")
                .orderByChild("masp").equalTo(masp);
        query.addListenerForSingleValueEvent(listener);
        return dcq;
    }
    public ArrayList<NhanXet>getNhanXetTheoEmail(String email) {
       final ArrayList<NhanXet> nhanXets = new ArrayList<NhanXet>();
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                nhanXets.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    NhanXet s = data.getValue(NhanXet.class);
                    nhanXets.add(s);
//
                }
                NhanXetCuaToiActivity.nhanXetAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        Query query = FirebaseDatabase.getInstance().getReference("NhanXet")
                .orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(listener);
        return nhanXets;
    }
    public List<NhanXet>getNhanXetTheoMaSP3SPCTSP(String masp) {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                dcq.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    NhanXet s = data.getValue(NhanXet.class);
                    dcq.add(s);
                 //   if (dcq.size()>=3){
               //         break;
                //    }

                }
               ChiTietSPActivity.adapternhanxetctsp.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        Query query = FirebaseDatabase.getInstance().getReference("NhanXet")
                .orderByChild("masp").equalTo(masp);
        query.addListenerForSingleValueEvent(listener);
        return dcq;
    }

}

package com.example.giloliadmin.DAO;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.giloliadmin.Model.SanPham;
import com.example.giloliadmin.Model.TheLoai;
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

public class SanPhamDAO {
    private DatabaseReference mDatabase;
    Context context;
    String maSanPham;
    public static List<SanPham> list = new ArrayList<SanPham>();

    public SanPhamDAO(Context context) {
        this.mDatabase = FirebaseDatabase.getInstance().getReference("SanPham");
        this.context = context;
    }

    public List<SanPham> layhetSanPham(String position) {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    SanPham s = data.getValue(SanPham.class);
                    list.add(s);
                }
                SanPhamFragment.capNhatAdapter();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(listener);
        return list;
    }


    public List<SanPham> laysanphamtheoma(String matheloai){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    SanPham sanphamtheoma = data.getValue(SanPham.class);
                    list.add( sanphamtheoma );
                }
                SanPhamFragment.sanphamAdapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        Query query = FirebaseDatabase.getInstance().getReference("SanPham")
                .orderByChild("maLoai").equalTo(matheloai);
        query.addListenerForSingleValueEvent(valueEventListener);
        return list;
    }




    public void themSanPham(SanPham sanPham) {
        maSanPham = mDatabase.push().getKey();
        mDatabase.child(maSanPham).setValue(sanPham).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    public void capNhatSanPham(final SanPham sanPham) {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("maSanPham").getValue(String.class).equals(sanPham.getMaSanPham())) {
                        maSanPham = data.getKey();
                        mDatabase.child(maSanPham).setValue(sanPham)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context, "Cập nhật thành công",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Cập nhật thất bại",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    } } }@Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void xoaSanPham(final SanPham sanPham) {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("maSanPham").getValue(String.class).equals(sanPham.getMaSanPham())) {
                        maSanPham = data.getKey();
                        mDatabase.child(maSanPham).setValue(null)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });}}}
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void xoaSanPhamTheoMaLoai(final TheLoai theLoai) {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("maLoai").getValue(String.class).equals(theLoai.getMaLoai())) {
                        maSanPham = data.getKey();
                        mDatabase.child(maSanPham).setValue(null)
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
                } }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

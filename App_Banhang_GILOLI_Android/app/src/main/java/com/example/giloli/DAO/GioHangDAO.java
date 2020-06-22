package com.example.giloli.DAO;

import android.content.Context;
import android.widget.Toast;
import android.view.View;
import androidx.annotation.NonNull;
import android.widget.Toast;
import android.view.View;
import androidx.annotation.NonNull;

import com.example.giloli.Activity.ChiTietSPActivity;
import com.example.giloli.Activity.GioHangActivity;
import com.example.giloli.MainActivity;
import com.example.giloli.Model.DiaChiNguoiDung;
import com.example.giloli.Model.GioHang;
import com.example.giloli.Model.SanPham;
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

public class GioHangDAO {
    private DatabaseReference mDatabase;
    Context context;
    String maGioHang;
    public static List<GioHang> list = new ArrayList<GioHang>();

    public GioHangDAO(Context context) {
        this.mDatabase = FirebaseDatabase.getInstance().getReference("GioHang");
        this.context = context;
    }

    public List<GioHang>layGioHang() {
        final ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                list.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    GioHang s = data.getValue(GioHang.class);
                    list.add(s);
//
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(listener);
        return list;
    }


    public List<GioHang> layGioHangTheoEmail(String email) {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    GioHang s = data.getValue(GioHang.class);
                    list.add(s);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        Query query = FirebaseDatabase.getInstance().getReference("GioHang")
                .orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(listener);
        return list;
    }
    public List<GioHang> layGioHangTheoEmailRCVGioHang(String email) {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    GioHang s = data.getValue(GioHang.class);
                    list.add(s);
                }

                    GioHangActivity.adaptergiohang.notifyDataSetChanged();



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        Query query = FirebaseDatabase.getInstance().getReference("GioHang")
                .orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(listener);
        return list;
    }

    public List<GioHang> layGioHangTheoEmailRCVGioHanggh(String email) {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    GioHang s = data.getValue(GioHang.class);
                    list.add(s);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        Query query = FirebaseDatabase.getInstance().getReference("GioHang")
                .orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(listener);
        return list;
    }





    public void addGioHang(GioHang gioHang){
        maGioHang = mDatabase.push().getKey();
        mDatabase.child(maGioHang).setValue(gioHang).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //  Toast.makeText(context,"Thêm thành công",Toast.LENGTH_SHORT).show();
               ChiTietSPActivity.demgiohangctsp();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Toast.makeText(context,"Thêm thất bại",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void deleteGioHang(final GioHang gioHang) {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("idGioiHang").getValue(Integer.class).equals(gioHang.getIdGioiHang())) {
                        maGioHang = data.getKey();
                        mDatabase.child(maGioHang).setValue(null)
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
    public void updateGioHang(final GioHang gioHang) {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("email").getValue(String.class).equals(gioHang.getEmail())) {
                        maGioHang = data.getKey();
                        mDatabase.child(maGioHang).setValue(gioHang)
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

package com.example.giloli.DAO;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.giloli.Activity.HienHoaDonChiTietActivity;
import com.example.giloli.Activity.QuanLiDonHangActivity;
import com.example.giloli.Model.HoaDon;
import com.example.giloli.Model.HoaDonCT;
import com.example.giloli.Model.HoaDonCtSP;
import com.example.giloli.Model.NguoiDung;
import com.example.giloli.Model.SanPham;
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

public class HoaDonDAO {
    private DatabaseReference mDatabase;
    Context context;
    String maHoaDon;
    public static List<HoaDon> list = new ArrayList<HoaDon>();
    public  HoaDonDAO(Context context){
        this.context = context;
        mDatabase = FirebaseDatabase.getInstance().getReference("HoaDon");
    }
    public List<HoaDon>getHoaDonSize() {
        final ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                list.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    HoaDon s = data.getValue(HoaDon.class);
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
    public List<HoaDon> getHoaDon(String email) {
        final ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                list.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    HoaDon s = data.getValue(HoaDon.class);
                    list.add(s);
                }
                QuanLiDonHangActivity.adapter_quanLiDonHang.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        Query query = FirebaseDatabase.getInstance().getReference("HoaDon")
                .orderByChild("emailNguoiDung").equalTo(email);
        query.addListenerForSingleValueEvent(listener);
        return list;
    }
    public List<HoaDon>getHoaDonTheoTt(final String tt, final String emailNguoiDung) {
        final ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                        list.clear();
                        for (DataSnapshot data: dataSnapshot.getChildren()) {
                            HoaDon s = data.getValue(HoaDon.class);
                            String trangthai = s.getTrangThai();
                            if (trangthai.equals(tt)){
                                list.add(s);
                            }
                        }
                        QuanLiDonHangActivity.adapter_quanLiDonHang.notifyDataSetChanged();
                    }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        Query query = FirebaseDatabase.getInstance().getReference("HoaDon")
                .orderByChild("emailNguoiDung").equalTo(emailNguoiDung);
        query.addListenerForSingleValueEvent(listener);
        return list;
    }

    public void themHoaDon(HoaDon hoaDon){
        maHoaDon = mDatabase.push().getKey();
        mDatabase.child(maHoaDon).setValue(hoaDon).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"Vui lòng kiểm tra mạng Internet",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void capNhatHoaDon(final HoaDon hoaDon) {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("maHoaDon").getValue(String.class).equals(hoaDon.getMaHoaDon())) {
                        maHoaDon = data.getKey();
                        mDatabase.child(maHoaDon).setValue(hoaDon)
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
    public List<HoaDonCtSP> getHoaDonCtTheoEmail(String email) {
        final List<HoaDonCtSP> sanPhamList = new ArrayList<HoaDonCtSP>();
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                sanPhamList.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    HoaDon hoaDon = data.getValue(HoaDon.class);
                    String tt = hoaDon.getTrangThai();
                    if (tt.equals("Đã vận chuyển")) {
                        String maHoaDon1 = hoaDon.getMaHoaDon();
                        Query query = FirebaseDatabase.getInstance().getReference("HoaDonCt")
                                .orderByChild("maHoaDon").equalTo(maHoaDon1);
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                    HoaDonCT hoaDonCT = dataSnapshot1.getValue(HoaDonCT.class);
                                    String maSP = hoaDonCT.getMaSP();
                                    int soLuong = hoaDonCT.getSoLuong();
                                    String maHoaDCt = hoaDonCT.getMaHoaDonCt();
                                    Query query = FirebaseDatabase.getInstance().getReference("SanPham")
                                            .orderByChild("maSanPham").equalTo(maSP);
                                    final int finalSoLuong = soLuong;
                                    final String finalMaHoaDCt = maHoaDCt;
                                    query.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                                                SanPham sanPham = dataSnapshot1.getValue(SanPham.class);
                                                List<String> list = sanPham.getLinkUrl();
                                                HoaDonCtSP hoaDonCtSP = new HoaDonCtSP(sanPham.getMaSanPham(),sanPham.getTenSanPham(),
                                                        sanPham.getGiaThanh(),list.get(0), finalSoLuong, finalMaHoaDCt);
                                                sanPhamList.add(hoaDonCtSP);
                                            }
                                            HienHoaDonChiTietActivity.hoaDonCtAdapter.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

//
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        Query query = FirebaseDatabase.getInstance().getReference("HoaDon")
                .orderByChild("emailNguoiDung").equalTo(email);
        query.addListenerForSingleValueEvent(listener);
        return sanPhamList;
    }

}

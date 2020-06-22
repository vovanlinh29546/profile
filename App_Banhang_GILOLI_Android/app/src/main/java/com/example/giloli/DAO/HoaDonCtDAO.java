package com.example.giloli.DAO;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.giloli.Activity.HienHoaDonChiTietActivity;
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

public class HoaDonCtDAO {
    private DatabaseReference mDatabase;
    Context context;
    String maHoaDonCt;
    public static List<HoaDonCT> hoaDonCTS = new ArrayList<HoaDonCT>();
    public  HoaDonCtDAO(Context context){
        this.context = context;
        mDatabase = FirebaseDatabase.getInstance().getReference("HoaDonCt");
    }
    public List<HoaDonCtSP> getHoaDonCt(String maHoaDon) {
        final List<HoaDonCtSP> sanPhamList = new ArrayList<HoaDonCtSP>();
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                sanPhamList.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    final HoaDonCT s = data.getValue(HoaDonCT.class);
                    String maSP = s.getMaSP();
                    final int soLuong = s.getSoLuong();
                    final String maHoaDCt = s.getMaHoaDonCt();
                    Query query = FirebaseDatabase.getInstance().getReference("SanPham")
                            .orderByChild("maSanPham").equalTo(maSP);
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                                SanPham sanPham = dataSnapshot1.getValue(SanPham.class);
                                List<String> list = sanPham.getLinkUrl();
                                HoaDonCtSP hoaDonCtSP = new HoaDonCtSP(sanPham.getMaSanPham(),sanPham.getTenSanPham(),
                                        sanPham.getGiaThanh(),list.get(0),soLuong,maHoaDCt);
                                sanPhamList.add(hoaDonCtSP);
                            }
                            HienHoaDonChiTietActivity.hoaDonCtAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
//
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        Query query = FirebaseDatabase.getInstance().getReference("HoaDonCt")
                .orderByChild("maHoaDon").equalTo(maHoaDon);
        query.addListenerForSingleValueEvent(listener);
        return sanPhamList;
    }

    public void themHoaDonCt(HoaDonCT hoaDonCT){
        maHoaDonCt = mDatabase.push().getKey();
        mDatabase.child(maHoaDonCt).setValue(hoaDonCT).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context,"Đặt hàng thành công",
                        Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"Vui lòng kiểm trả Internet",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}

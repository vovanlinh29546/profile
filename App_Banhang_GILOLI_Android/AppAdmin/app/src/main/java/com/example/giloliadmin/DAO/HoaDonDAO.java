package com.example.giloliadmin.DAO;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.giloliadmin.Model.HoaDon;
import com.example.giloliadmin.fragment.HoaDonFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {
    private DatabaseReference mDatabase;
    Context context;
    String maHoaDon;
    public static List<HoaDon> list = new ArrayList<HoaDon>();
    public HoaDonDAO(Context context){
        this.context = context;
        mDatabase = FirebaseDatabase.getInstance().getReference("HoaDon");
    }
    public List<HoaDon>getHoaDon(String dateHoaDon) {
        final ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                list.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    HoaDon s = data.getValue(HoaDon.class);
                    list.add(s);
                }
                HoaDonFragment.hoaDonAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        Query query = FirebaseDatabase.getInstance().getReference("HoaDon")
                .orderByChild("dateHoaDon").equalTo(dateHoaDon);
        query.addListenerForSingleValueEvent(listener);
        return list;
    }
    public void layTongHoaDonTheoNgay (final String dateHoaDon, final TextView textView) {
        final ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    HoaDon s = data.getValue(HoaDon.class);
                    String date = s.getDateHoaDon();
                    if (date.equals(dateHoaDon)){
                        Query query = FirebaseDatabase.getInstance().getReference("HoaDon")
                                .orderByChild("trangThai").equalTo("Đã vận chuyển");
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                double tong1 = 0;
                                for (DataSnapshot data:dataSnapshot.getChildren()){
                                    HoaDon s = data.getValue(HoaDon.class);
                                    if (s.getDateHoaDon().equals(dateHoaDon)) {
                                        tong1 = tong1 + Double.parseDouble(s.getTongCong());
                                        NumberFormat numberFormat = new DecimalFormat("#,###");
                                        String tongf = numberFormat.format(tong1);
                                        textView.setText(tongf + " đ");
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    } else {
                        textView.setText("Ngày bạn chọn không có hoá đơn");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        Query query = FirebaseDatabase.getInstance().getReference("HoaDon")
                .orderByChild("trangThai").equalTo("Đã vận chuyển");
        query.addListenerForSingleValueEvent(listener);
    }
    public void layTongHoaDonTheoThang (final String monthHoaDon, final String yearHoaDon , final TextView textView) {
        final ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    HoaDon s = data.getValue(HoaDon.class);
                    String date = s.getDateHoaDon();
                    String [] kiTu = date.split("/");
                    String year = kiTu[2];
                    final String month = kiTu[1];
                    if (year.equals(yearHoaDon)){
                        Query query = FirebaseDatabase.getInstance().getReference("HoaDon")
                                .orderByChild("trangThai").equalTo("Đã vận chuyển");
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (month.equals(monthHoaDon)){
                                    Query query = FirebaseDatabase.getInstance().getReference("HoaDon")
                                            .orderByChild("trangThai").equalTo("Đã vận chuyển");
                                    query.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            double tong1 = 0;
                                            for (DataSnapshot data:dataSnapshot.getChildren()){
                                                HoaDon s = data.getValue(HoaDon.class);
                                                String date = s.getDateHoaDon();
                                                String [] kiTu = date.split("/");
                                                String month = kiTu[1];
                                                if (month.equals(monthHoaDon)){
                                                    tong1 = tong1 + Double.parseDouble(s.getTongCong());
                                                    NumberFormat numberFormat = new DecimalFormat("#,###");
                                                    String tongf = numberFormat.format(tong1);
                                                    textView.setText(tongf+" đ");
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                } else {
                                    textView.setText("Tháng bạn nhập không có hoá đơn");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    } else {
                        textView.setText("Năm bạn nhập không có hoá đơn");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        Query query = FirebaseDatabase.getInstance().getReference("HoaDon")
                .orderByChild("trangThai").equalTo("Đã vận chuyển");
        query.addListenerForSingleValueEvent(listener);
    }
    public void layTongHoaDonTheoNam (final String namHoaDon, final TextView textView) {
        final ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                list.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    HoaDon s = data.getValue(HoaDon.class);
                    String date = s.getDateHoaDon();
                    String [] kiTu = date.split("/");
                    String year = kiTu[2];
                    if (year.equals(namHoaDon)){
                        Query query = FirebaseDatabase.getInstance().getReference("HoaDon")
                                .orderByChild("trangThai").equalTo("Đã vận chuyển");
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                double tong1 = 0;
                                for (DataSnapshot data:dataSnapshot.getChildren()){
                                    HoaDon s = data.getValue(HoaDon.class);
                                    String date = s.getDateHoaDon();
                                    String [] kiTu = date.split("/");
                                    String year = kiTu[2];
                                    if (year.equals(namHoaDon)){
                                        tong1 = tong1 + Double.parseDouble(s.getTongCong());
                                        NumberFormat numberFormat = new DecimalFormat("#,###");
                                        String tongf = numberFormat.format(tong1);
                                        textView.setText(tongf+" đ");
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    } else {
                        textView.setText("Năm của bạn nhập không có hoá đơn");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        Query query = FirebaseDatabase.getInstance().getReference("HoaDon")
                .orderByChild("trangThai").equalTo("Đã vận chuyển");
        query.addListenerForSingleValueEvent(listener);
    }
    public void suaHoaDon(final HoaDon hoaDon) {
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
                                        Toast.makeText(context,"Sửa thành công",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context,"Sửa thất bại",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    } }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


}

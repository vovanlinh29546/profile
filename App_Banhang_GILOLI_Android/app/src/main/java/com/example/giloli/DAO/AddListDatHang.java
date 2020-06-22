package com.example.giloli.DAO;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.giloli.Activity.ChiTietDanhMucSanPham;
import com.example.giloli.Activity.ListGiaoHang;
import com.example.giloli.Model.DatHang;
import com.example.giloli.Model.DiaChiNguoiDung;
import com.example.giloli.Model.HoaDon;
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

public class AddListDatHang {
    DatabaseReference mDatabase;
    Context context;
    public static ArrayList<DatHang> list = new ArrayList<DatHang>();
    public AddListDatHang(Context context){
        this.mDatabase = FirebaseDatabase.getInstance().getReference("ListPT");
        this.context = context;
    }
    public ArrayList<DatHang> layListPt() {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    DatHang datHang = dataSnapshot1.getValue(DatHang.class);
                    list.add(datHang);
                }
                ListGiaoHang.adapterDatHang.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(valueEventListener);
        return list;
    }


}

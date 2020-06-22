package com.example.giloli.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giloli.Adapter.Adapter_GridviewDanhMuc;
import com.example.giloli.DAO.TheLoaiDAO;
import com.example.giloli.Model.Image;
import com.example.giloli.Model.TheLoai;
import com.example.giloli.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class DanhMucFragment extends Fragment {
    GridView gv_danhmuc;
    List<TheLoai> list;
    TheLoaiDAO theLoaiDAO;
    TheLoai theLoai;
    public  static  Adapter_GridviewDanhMuc adapter_gridviewDanhMuc;
    StorageReference storageReference;
    DatabaseReference databaseReference;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danhmuc, container, false);
        gv_danhmuc= view.findViewById(R.id.gv_danhmuc);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        storageReference = FirebaseStorage.getInstance().getReference("TheLoai");
        databaseReference = FirebaseDatabase.getInstance().getReference("TheLoai");
        FirebaseRecyclerOptions<TheLoai> options =
                new FirebaseRecyclerOptions.Builder<TheLoai>()
                .setQuery(databaseReference,TheLoai.class)
                .build();
        theLoaiDAO = new TheLoaiDAO(getContext());
        list = new ArrayList<>();
        theLoaiDAO = new TheLoaiDAO(getActivity());
        list = (ArrayList<TheLoai>) theLoaiDAO.layHetTheLoai();
        theLoai = new TheLoai();
        adapter_gridviewDanhMuc = new Adapter_GridviewDanhMuc((ArrayList<TheLoai>) list, getContext());
        gv_danhmuc.setAdapter( adapter_gridviewDanhMuc );
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    public static void capNhatAdapter(){
        adapter_gridviewDanhMuc.notifyDataSetChanged();
    }
}

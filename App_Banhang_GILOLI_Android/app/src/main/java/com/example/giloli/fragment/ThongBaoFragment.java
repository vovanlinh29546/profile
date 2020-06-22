package com.example.giloli.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giloli.Adapter.thongbaoAdapter;
import com.example.giloli.Model.ThongBao;
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


public class ThongBaoFragment extends Fragment {
    RecyclerView rv_thongbao;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    public String image_url;
    List<ThongBao> list;
    String id;
    ThongBao thongBao;
    public static thongbaoAdapter ThongbaoAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thongbao, container, false);
        rv_thongbao= view.findViewById(R.id.rv_thongbao);
        rv_thongbao.setHasFixedSize(true);
        //Tạo đường kẻ, phân biệt giữa các phần tử
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        rv_thongbao.addItemDecoration(dividerItemDecoration);
        rv_thongbao.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Thông Báo");
        storageReference = FirebaseStorage.getInstance().getReference("ThongBao");
        databaseReference = FirebaseDatabase.getInstance().getReference("ThongBao");
        list = new ArrayList<>();

    }

    @Override
    public void onStart() {
        super.onStart();
        databaseReference = FirebaseDatabase.getInstance().getReference("ThongBao");
        FirebaseRecyclerOptions<ThongBao> options =
                new FirebaseRecyclerOptions.Builder<ThongBao>()
                        .setQuery(databaseReference,ThongBao.class)
                        .build();

        FirebaseRecyclerAdapter<ThongBao, thongbaoAdapter.ImageViewHolder> adapter = new FirebaseRecyclerAdapter<ThongBao, thongbaoAdapter.ImageViewHolder>( options) {
            @Override
            protected void onBindViewHolder(@NonNull final thongbaoAdapter.ImageViewHolder holder, int position, @NonNull ThongBao model) {
                String uid = getRef(position).getKey();
                databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("mathongbao")){
                            String tieude = dataSnapshot.child("tieude").getValue().toString();
                            String noidung = dataSnapshot.child("noidung").getValue().toString();
                            String url_iamge = dataSnapshot.child("urlanhthongbao").getValue().toString();

                            holder.tv_tieude.setText(tieude);
                            holder.tv_noidung.setText(noidung);
                            Picasso.get().load(url_iamge).fit().centerCrop().into(holder.imv_anhthongbao);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
            @NonNull
            @Override
            public thongbaoAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup pareparnt, int viewType) {
                View view = LayoutInflater.from(pareparnt.getContext()).inflate(R.layout.cardview_thongbao,pareparnt, false);
                thongbaoAdapter.ImageViewHolder viewHolder = new thongbaoAdapter.ImageViewHolder(view);
                return viewHolder;
            }
        };
        rv_thongbao.setAdapter( adapter );
        adapter.startListening();
    }

}
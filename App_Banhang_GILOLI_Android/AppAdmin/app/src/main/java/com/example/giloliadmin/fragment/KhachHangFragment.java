package com.example.giloliadmin.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giloliadmin.Adapter.NguoiDungAdaptercv;
import com.example.giloliadmin.DAO.NguoiDungDAO;
import com.example.giloliadmin.Model.NguoiDung;
import com.example.giloliadmin.R;

import java.util.ArrayList;

public class KhachHangFragment extends Fragment {
    RecyclerView lv;
    ArrayList<NguoiDung> list;
    static NguoiDungDAO dao;
    public static NguoiDungAdaptercv adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_khachhang, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Người Dùng");
        lv = view.findViewById(R.id.lv_shownd);
        list = new ArrayList<>();
        dao = new NguoiDungDAO(getContext());
        list = (ArrayList<NguoiDung>)dao.getNguoiDung();
        LinearLayoutManager mlinerlayoutmanager=new LinearLayoutManager(getContext());
        lv.setLayoutManager(mlinerlayoutmanager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), mlinerlayoutmanager.getOrientation());
        lv.addItemDecoration(dividerItemDecoration);
        adapter = new NguoiDungAdaptercv(getContext(), list);
        lv.setAdapter(adapter);
    }
    public static void capnhatLV(){
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onResume() {
        super.onResume();
    }
}

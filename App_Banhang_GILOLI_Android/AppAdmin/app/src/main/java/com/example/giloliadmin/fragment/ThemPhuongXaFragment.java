package com.example.giloliadmin.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.giloliadmin.DAO.AddPhuongXaDao;
import com.example.giloliadmin.DAO.AddQuanDao;
import com.example.giloliadmin.DAO.TheLoaiDAO;
import com.example.giloliadmin.Model.DiaChi_QuanHuyen;
import com.example.giloliadmin.Model.DiaChi_ThiXa;
import com.example.giloliadmin.R;

import java.util.ArrayList;
import java.util.List;

public class ThemPhuongXaFragment extends Fragment {
Spinner spnQuan;
  //  ArrayList<String> list;
    AddQuanDao nddao;
    List<DiaChi_QuanHuyen> list;
    public static ArrayAdapter adapterTenQuan;
    String tenQuan="";
EditText edtthemphuongxa;
Button btnthemphuongxa;
    AddPhuongXaDao pxdao;
    DiaChi_ThiXa px;
    List<DiaChi_ThiXa> listpx;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_themphuongxa, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
spnQuan=view.findViewById(R.id.spnQuan);
edtthemphuongxa=view.findViewById(R.id.edtthemphuongxa);
btnthemphuongxa=view.findViewById(R.id.btnThemPhuongXa);
        AddQuanDao nddao = new AddQuanDao(getContext());
        pxdao = new AddPhuongXaDao(getContext());
        list = new ArrayList<DiaChi_QuanHuyen>();
        list = nddao.getTenQuan();
        adapterTenQuan = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, list);
        spnQuan.setAdapter(adapterTenQuan);
        spnQuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               tenQuan=list.get(i).getTenQuan();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
btnthemphuongxa.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String tenphuongxa;
        if (!edtthemphuongxa.getText().toString().isEmpty()){
          
            DiaChi_ThiXa dcq = new DiaChi_ThiXa();
            dcq.setTenPhuongXa(edtthemphuongxa.getText().toString());
            dcq.setTenQuan(tenQuan);
            pxdao.addPhuongXa(dcq);
            Toast.makeText(getContext(),"Thêm thành công",Toast.LENGTH_SHORT).show();
            edtthemphuongxa.setText("");
            return;
        }
        else {
            Toast.makeText(getContext(),"Thêm Thất bại",Toast.LENGTH_SHORT).show();
            return;
        }
    }
});
    }
}

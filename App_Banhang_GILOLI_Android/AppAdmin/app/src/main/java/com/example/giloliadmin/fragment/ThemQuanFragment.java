package com.example.giloliadmin.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.giloliadmin.DAO.AddQuanDao;
import com.example.giloliadmin.Model.DiaChi_QuanHuyen;
import com.example.giloliadmin.R;

import java.util.ArrayList;
import java.util.List;

public class ThemQuanFragment extends Fragment {
EditText edtthemquanhuyen;
Button btnThemQuan;
    AddQuanDao nddao;
    DiaChi_QuanHuyen nd;
    List<DiaChi_QuanHuyen> list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_themquan, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
edtthemquanhuyen=view.findViewById(R.id.edtthemquanhuyen);
btnThemQuan=view.findViewById(R.id.btnThemQuan);
        nddao = new AddQuanDao(getContext());
btnThemQuan.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String tenquan;
        if (!edtthemquanhuyen.getText().toString().isEmpty()){
            AddQuanDao nddao = new AddQuanDao(getContext());
            list = new ArrayList<DiaChi_QuanHuyen>();
            list = nddao.getQuan();
            tenquan=edtthemquanhuyen.getText().toString();
            for (int j = 0; j <= list.size() - 1; j++) {
                if (list.size() > 0) {
                    if (tenquan.equals(list.get(j).getTenQuan())) {
                        return;
                    }
                }
            }
        DiaChi_QuanHuyen dcq = new DiaChi_QuanHuyen();
        dcq.setTenQuan(edtthemquanhuyen.getText().toString());
            nddao.addTenQuan(dcq);
            Toast.makeText(getContext(),"Thêm thành công",Toast.LENGTH_SHORT).show();
            edtthemquanhuyen.setText("");
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

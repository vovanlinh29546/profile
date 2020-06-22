package com.example.giloliadmin.fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giloliadmin.Adapter.HoaDonAdapter;
import com.example.giloliadmin.DAO.HoaDonCtDAO;
import com.example.giloliadmin.DAO.HoaDonDAO;
import com.example.giloliadmin.Model.HoaDon;
import com.example.giloliadmin.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HoaDonFragment extends Fragment {
    private EditText edtTextNgay;
    private Button button;
    private RecyclerView recyclerView;
    private HoaDonDAO hoaDonDAO;
    private List<HoaDon> list;
    public static HoaDonAdapter hoaDonAdapter;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hoadon, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Hóa Đơn");
        edtTextNgay = view.findViewById(R.id.edtNgayHd);
        button = view.findViewById(R.id.btnHien);
        recyclerView = view.findViewById(R.id.recyclerViewHD);
        edtTextNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        , mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDataSet: yyyy/mm/dd" + year + "/" + month + "/" + day);
                String date = day + "/" + month + "/" + year;
                edtTextNgay.setText(date);
            }
        };
        hoaDonDAO = new HoaDonDAO(getContext());
        list = new ArrayList<HoaDon>();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list = hoaDonDAO.getHoaDon(edtTextNgay.getText().toString());
                hoaDonAdapter = new HoaDonAdapter(getContext(),list);
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(hoaDonAdapter);

            }
        });
    }
}

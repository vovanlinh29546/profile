package com.example.giloliadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giloliadmin.DAO.HoaDonDAO;
import com.example.giloliadmin.Model.HoaDon;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ThongKeActivity extends AppCompatActivity {
    private RelativeLayout relativeLayout;
    private TextView textView;
    private EditText edtNgay, edtThang, edtNam;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Button btn, btnThang, btnNam;
    private HoaDonDAO hoaDonDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        relativeLayout = findViewById(R.id.relativeThongKe);
        textView = findViewById(R.id.txtTongTienThongKe);
        edtNgay = findViewById(R.id.edtTheoNgay);
        edtThang = findViewById(R.id.edtTheoThang);
        edtNam = findViewById(R.id.edtTheoNam);
        btn = findViewById(R.id.btnThongKeInside);
        btnThang = findViewById(R.id.btnThongKeThangInside);
        btnNam = findViewById(R.id.btnThongKeNamInside);
        hoaDonDAO = new HoaDonDAO(ThongKeActivity.this);
        edtNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ThongKeActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
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
                edtNgay.setText(date);
            }
        };
        setBtn();
        setBtnThang();
        setBtnNam();

    }
    private void setBtn(){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtNgay.getText().toString().equals("")) {
                    textView.setText("Vui lòng chọn ngày");
                    relativeLayout.setVisibility(View.VISIBLE);
                    return;
                }
                hoaDonDAO.layTongHoaDonTheoNgay(edtNgay.getText().toString(), textView);
                relativeLayout.setVisibility(View.VISIBLE);
            }
        });
    }
    private void setBtnThang(){
        btnThang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int thang = 0;
                String isnumber = "^[0-9]*$";
                if (!edtThang.getText().toString().isEmpty() && edtThang.getText().toString().matches(isnumber)) {
                    thang = Integer.parseInt(edtThang.getText().toString());
                }
                String month = edtThang.getText().toString().trim();
                if (month.isEmpty()){
                    edtThang.setError("Tháng không được để trống");
                    return;
                }
                if (!month.matches(isnumber)){
                    edtThang.setError("Nhập tháng là số");
                    return;
                } if (thang < 0 || thang >12){
                    edtThang.setError("Tháng không được nhỏ hơn 1 và lớn hơn 12");
                    return;
                } if (edtNam.getText().toString().trim().isEmpty()){
                    edtNam.setError("Năm không được trống");
                    return;
                }

                hoaDonDAO.layTongHoaDonTheoThang(edtThang.getText().toString(),
                        edtNam.getText().toString(),textView);
                relativeLayout.setVisibility(View.VISIBLE);
            }
        });
    }
    private void setBtnNam(){
        btnNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String year = edtNam.getText().toString().trim();
                String isnumber = "^[0-9]*$";
                if (year.isEmpty()){
                    edtNam.setError("Năm không được để trống");
                    return;
                }
                if (!year.matches(isnumber)){
                    edtNam.setError("Năm phải là số");
                    return;
                }
                hoaDonDAO.layTongHoaDonTheoNam(edtNam.getText().toString(),textView);
                relativeLayout.setVisibility(View.VISIBLE);

            }
        });
    }
}

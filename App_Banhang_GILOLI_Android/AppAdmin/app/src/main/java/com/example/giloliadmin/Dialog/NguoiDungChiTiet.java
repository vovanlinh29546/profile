package com.example.giloliadmin.Dialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.giloliadmin.Model.NguoiDung;
import com.example.giloliadmin.R;

import java.util.Calendar;

public class NguoiDungChiTiet extends AppCompatActivity {
    NguoiDung nd;
    private static final String TAG = "chonngaysinh";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_dung_chi_tiet);
        Toolbar toolbar = findViewById(R.id.toolbar_thongtinnguoidung);
        toolbar.setTitle("Thông tin người dùng");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        EditText edthoten=findViewById(R.id.edt_chitiet_HoTen);
        EditText edtemail=findViewById(R.id.edt_chitiet_Email);
        EditText edtsdt=findViewById(R.id.edt_chitiet_Sdt);
        EditText edtmk=findViewById(R.id.edt_chitiet_MatKhau);
        final EditText edtngaysinh=findViewById(R.id.edt_chitiet_NgaySinh);
        edtngaysinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        NguoiDungChiTiet.this, android.R.style.Theme_DeviceDefault_Dialog_Alert
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
                edtngaysinh.setText(date);
            }
        };
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("suaThongTin");
        if (bundle != null) {
            String hoten = bundle.getString("ht", "");
            String email = bundle.getString("em", "");
            String sodt = bundle.getString("sdt", "");
            String matkhau = bundle.getString("mk", "");
            String ngaysinh = bundle.getString("ns", "");
            //dua data len EditText
            edthoten.setText(hoten);
            edtemail.setText(email);
            edtmk.setText(sodt);
            edtsdt.setText(matkhau);
            edtngaysinh.setText(ngaysinh);

            if (!email.isEmpty()) {
                edtemail.setEnabled(false);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                onBackPressed();
                return true;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}

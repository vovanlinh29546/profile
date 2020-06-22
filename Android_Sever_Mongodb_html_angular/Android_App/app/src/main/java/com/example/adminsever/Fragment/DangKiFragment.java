package com.example.adminsever.Fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.adminsever.Books;
import com.example.adminsever.R;

import com.example.adminsever.noUI;
import com.google.android.material.textfield.TextInputLayout;

import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class DangKiFragment extends Fragment {
    EditText edt_registNgaySinh,edt_registHoTen,edt_registSdt,edt_registEmail,edt_registMatKhau;
    Button btnDangKy,btnPickDate;
    TextInputLayout inputhoten,inputsdt,inputemail,inputmatkhau,inputngaysinh;
    private static final String TAG = "chonngaysinh";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private ProgressDialog pDialog;

    private static final String alpha = "abcdefghijklmnopqrstuvwxyz"; // a-z
    private static final String alphaUpperCase = alpha.toUpperCase(); // A-Z
    private static final String digits = "0123456789"; // 0-9
    private static final String ALPHA_NUMERIC = alpha + alphaUpperCase + digits;
    private static Random generator = new Random();
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.1.9:3000");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    private Emitter.Listener onRegister = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String data =  args[0].toString();

            noUI noui = new noUI(getContext());

            if(data == "true"){

                noui.toast("Dang ky thanh cong");

                // Launch login activity
                Intent intent = new Intent(
                        getContext(),
                        Books.class);
                startActivity(intent);
                //finish();
            }else{
                noui.toast("Dang ky that bai");

            }

        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dangki, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Ánh xạ
        edt_registNgaySinh = view.findViewById(R.id.edt_registNgaySinh);
        edt_registHoTen=view.findViewById(R.id.edt_registHoTen);
        edt_registSdt=view.findViewById(R.id.edt_registSdt);
        edt_registEmail=view.findViewById(R.id.edt_registEmail);
        edt_registMatKhau=view.findViewById(R.id.edt_registMatKhau);
        btnDangKy=view.findViewById(R.id.btnDangKy);
        inputhoten=view.findViewById(R.id.input_regis_hoten);
        inputsdt=view.findViewById(R.id.input_regis_sdt);
        inputemail=view.findViewById(R.id.input_regis_email);
        inputmatkhau=view.findViewById(R.id.input_regis_matkhau);
        mSocket.connect();
        mSocket.on("register", onRegister);
        // Chọn ngày sinh
        edt_registNgaySinh.setOnClickListener(new View.OnClickListener() {
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
                edt_registNgaySinh.setText(date);
            }
        };
btnDangKy.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String name = edt_registHoTen.getText().toString().trim();
        String email = edt_registEmail.getText().toString().trim();
        String password = edt_registMatKhau.getText().toString().trim();
        String sdt = edt_registSdt.getText().toString().trim();
        String ns = edt_registNgaySinh.getText().toString().trim();

        if (!name.isEmpty() && !sdt.isEmpty() && !email.isEmpty()&& !password.isEmpty() && !ns.isEmpty()) {
            registerUser( email,password,name,sdt,ns);
        } else {
            Toast.makeText(getContext(),
                    "Please enter your details!", Toast.LENGTH_LONG)
                    .show();
        }
    }
});
        // Progress dialog
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);

    }

    private void registerUser(final String email, final String password,
                              final String name,final String sdt,final String ngaysinh) {


        mSocket.emit("register", email,password,name,sdt,ngaysinh);

        pDialog.setMessage("Registering ...");
        showDialog();
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing()){
            pDialog.dismiss();
            pDialog.cancel();
        }
    }
}

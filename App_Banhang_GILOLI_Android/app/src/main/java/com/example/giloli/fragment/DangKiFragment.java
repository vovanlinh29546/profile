package com.example.giloli.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

import androidx.annotation.Nullable;

import com.example.giloli.DAO.NguoiDungDAO;
import com.example.giloli.MainActivity;
import com.example.giloli.Model.NguoiDung;
import com.example.giloli.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DangKiFragment extends Fragment {
    EditText edt_registNgaySinh,edt_registHoTen,edt_registSdt,edt_registEmail,edt_registMatKhau;
    Button btnDangKy,btnPickDate;
    TextInputLayout inputhoten,inputsdt,inputemail,inputmatkhau,inputngaysinh;
    private static final String TAG = "chonngaysinh";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
//firebase
    FirebaseAuth firebaseAuth;
    FirebaseDatabase mDatabase;
    //ma nguoi dung tang tu dong
NguoiDungDAO nddao;
NguoiDung nd;

    private static final String alpha = "abcdefghijklmnopqrstuvwxyz"; // a-z
    private static final String alphaUpperCase = alpha.toUpperCase(); // A-Z
    private static final String digits = "0123456789"; // 0-9
    private static final String ALPHA_NUMERIC = alpha + alphaUpperCase + digits;
    private static Random generator = new Random();

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
        //firebase
        firebaseAuth=FirebaseAuth.getInstance();
        //ma nguoi dung tang tu dong
        nddao= new NguoiDungDAO(getContext());
      //  final List<NguoiDung>nguoidungs2= nddao.getNguoiDung();

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

                DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                final String hoten = edt_registHoTen.getText().toString();
                final String sdt = edt_registSdt.getText().toString();
                final String email = edt_registEmail.getText().toString();
                final String matkhau = edt_registMatKhau.getText().toString();
                final String ngaysinh = edt_registNgaySinh.getText().toString();

if (validatedk()==false){
    Toast.makeText(getContext(), "Đăng ký không thành công", Toast.LENGTH_SHORT).show();

}

                else {
                    final ProgressDialog progressDialog=ProgressDialog.show(getContext(),"Đăng kí","Đang kiểm tra dữ liệu...",true);
    List<NguoiDung> nguoiDungs2 = new ArrayList<NguoiDung>();
    nguoiDungs2 = nddao.getNguoiDung();
    final int finalNewID = 8;
    (firebaseAuth.createUserWithEmailAndPassword(edt_registEmail.getText().toString(),edt_registMatKhau.getText().toString()))
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if (task.isSuccessful()){
                                      //  nd = new NguoiDung(finalNewID, sdt, email, hoten,matkhau,ngaysinh);
                                   //     nddao.addNguoiDungFirebase(nd);
                                        int maNduoi = 8;
                                        NguoiDung nguoiDung = new NguoiDung();
                                        int ma=Integer.parseInt(randomAlphaNumeric(maNduoi));
                                        nguoiDung.setEmail(email);
                                        nguoiDung.setHoten(hoten);
                                        nguoiDung.setNgaySinh(ngaysinh);
                                        nguoiDung.setSoDienThoai(sdt);
                                        nguoiDung.setMatKhau(matkhau);
<<<<<<< HEAD
                                        nguoiDung.setiDNguoiDung(ma);
=======
                                        nguoiDung.setiDNguoiDung(randomAlphaNumeric(finalNewID));
>>>>>>> fd98af78136306b24c6a89c426328c10431515a2
                                        nddao.addNguoiDungFirebase(nguoiDung);

                                        (firebaseAuth.signInWithEmailAndPassword(email, matkhau))
                                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        progressDialog.dismiss();
                                                        if (task.isSuccessful()) {
                                                            Intent i = new Intent(getContext(), MainActivity.class);
                                                            startActivity(i);
                                                        } else {
                                                            Log.e("ERROR", task.getException().toString());
                                                            Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });


                                        Toast.makeText(getContext(), "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                                    }
                                    else  { Log.e("ERROR",task.getException().toString());
                                        Toast.makeText(getContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });

    }
    private boolean validatedk(){
        // Liên quan tới validate
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String MobilePattern = "[0-9]{10}";
        String regx = "^[\\p{L} .'-]+$";
        if (edt_registHoTen.getText().toString().isEmpty()){
            inputhoten.setError("Vui lòng nhập họ tên");
            return false;
        }
        if (!edt_registHoTen.getText().toString().matches(regx)){
            inputhoten.setError("Họ tên không hợp lệ");
            return false;
        }
        if(edt_registSdt.getText().toString().isEmpty()) {
            inputsdt.setError("Vui lòng số điện thoại");
            return false;
        }
        if(!edt_registSdt.getText().toString().matches(MobilePattern)) {
            inputsdt.setError("Số điện thoại không hợp lệ");
            return false;
        }
        if(edt_registEmail.getText().toString().isEmpty()){
            inputemail.setError("Vui lòng nhập email");
            return false;
        }
        if(!edt_registEmail.getText().toString().matches(emailPattern)){
            inputemail.setError("Email không hợp lệ");
            return false;
        }
        if (edt_registMatKhau.getText().toString().isEmpty()){
            inputmatkhau.setError("Vui lòng nhập mật khẩu");
            return false;
        }
        if( edt_registNgaySinh.getText().toString().isEmpty()) {
            inputmatkhau.setError("Chưa chọn ngày sinh");
            return false;
        }
return true;
    }
    public String randomAlphaNumeric(int numberOfCharactor) {
        StringBuilder randomMa = new StringBuilder();
        for (int i = 0; i < numberOfCharactor; i++) {
            int number = randomNumber(0, ALPHA_NUMERIC.length() - 1);
            char ch = ALPHA_NUMERIC.charAt(number);
            randomMa.append(ch); }
        return randomMa.toString();
    }

    public static int randomNumber(int min, int max) {
        return generator.nextInt((max - min) + 1) + min;
    }


}

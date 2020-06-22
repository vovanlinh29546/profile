package com.example.giloli.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.giloli.DAO.NguoiDungDAO;
import com.example.giloli.Model.NguoiDung;
import com.example.giloli.R;
import com.example.giloli.fragment.CaNhanFragmentDone;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class SuaThongTinNguoiDungActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    TextInputLayout input_edit_hoten;
    EditText edt_editNgaySinh,edt_editHoTen;
    TextView edt_editEmail;
    FirebaseAuth mdatabase;
    String email;
    Button btnLuuThayDoi;
    NguoiDungDAO nddao;
    private static final String TAG = "chonngaysinhedit";
    ImageView imgBackSTTND;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_thong_tin_nguoi_dung);
        mdatabase = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar_suathongtinnguoidung);
        setSupportActionBar(toolbar);
        nddao= new NguoiDungDAO(this);
        anhxa();



        onclick();
        if (mdatabase.getCurrentUser()!=null) {
            email = mdatabase.getCurrentUser().getEmail();
           edt_editEmail.setText(email);
        } else {
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }
        NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(this);
        nguoiDungDAO.layTenvsSDT(edt_editEmail.getText().toString(),edt_editHoTen,edt_editNgaySinh);

        // Chọn ngày sinh
        edt_editNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SuaThongTinNguoiDungActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
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
                edt_editNgaySinh.setText(date);
            }
        };
    }
    private void loadUserProfile(AccessToken accessToken)
    {

        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    if (object == null){
                        object = new JSONObject();
                    }
                    String email1 = object.getString("email");
                    email = email1;
                    edt_editEmail.setText(email);
                    NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(SuaThongTinNguoiDungActivity.this);
                    nguoiDungDAO.layTenvsSDT(edt_editEmail.getText().toString(),edt_editHoTen,edt_editNgaySinh);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields","first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }

    private void onclick() {
        btnLuuThayDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValueEventListener listener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                          //  NguoiDung nguoiDung = dataSnapshot1.getValue(NguoiDung.class);
                            Map<String,Object> map=(Map<String, Object>)dataSnapshot1.getValue();
                            Object id=map.get("iDNguoiDung");
                            Object mk=map.get("matKhau");
                            Object sd=map.get("soDienThoai");
                            String idvalue= String.valueOf(id);
                            String mkvalue= String.valueOf(mk);
                            String sdvalue= String.valueOf(sd);
                            if (!edt_editHoTen.getText().toString().isEmpty()){
                                NguoiDung nguoidung = new NguoiDung(idvalue,sdvalue,email,edt_editHoTen.getText().toString(),mkvalue,edt_editNgaySinh.getText().toString());

                                 nddao.updateNguoiDung(nguoidung);
                                Toast.makeText(SuaThongTinNguoiDungActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();

                                break;
                            }
                            else {
                                input_edit_hoten.setError("Họ tên không được để trống");
                                return;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };
                Query query = FirebaseDatabase.getInstance().getReference("NguoiDung")
                        .orderByChild("email").equalTo(email);
                query.addListenerForSingleValueEvent(listener);

            }
        });


        imgBackSTTND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void anhxa() {
        edt_editHoTen=findViewById(R.id.edt_editHoTen);
        edt_editEmail=findViewById(R.id.edt_editEmail);
        edt_editNgaySinh=findViewById(R.id.edt_editNgaySinh);
        btnLuuThayDoi=findViewById(R.id.btnLuuThayDoi);
        input_edit_hoten=findViewById(R.id.input_edit_hoten);
        imgBackSTTND=findViewById(R.id.imgBackSTTND);
    }

    @Override
    public void onBackPressed() {
        if ( getFragmentManager().getBackStackEntryCount() > 0)
        {
            getFragmentManager().popBackStack();
            return;
        }
        super.onBackPressed();
    }
}

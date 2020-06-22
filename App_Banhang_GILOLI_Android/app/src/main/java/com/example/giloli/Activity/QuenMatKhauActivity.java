package com.example.giloli.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.giloli.R;
import com.example.giloli.fragment.CaNhanFragmentDone;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class QuenMatKhauActivity extends AppCompatActivity {
EditText edt_forgotemail;
TextInputLayout input_forgotMK;
Button btn_forgotemail;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau);
        Toolbar toolbar = findViewById(R.id.toolbar_forgotmk);
        toolbar.setTitle("Quên mật khẩu?");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        firebaseAuth=FirebaseAuth.getInstance();
        anhxa();
        btn_forgotemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog=ProgressDialog.show(QuenMatKhauActivity.this,"Lấy lại mật khẩu....","Đang tải..",true);

                firebaseAuth.sendPasswordResetEmail(edt_forgotemail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()){

                                    Toast.makeText(QuenMatKhauActivity.this, "Kiểm tra lại Email!", Toast.LENGTH_SHORT).show();

                                }
                                else {
                                    input_forgotMK.setError("Email sai hoặc không tồn tại!");
                                }
                            }
                        });
            }
        });
    }

    private void anhxa() {
        edt_forgotemail=findViewById(R.id.edt_forgotEmail);
        input_forgotMK=findViewById(R.id.input_forgot_email);
        btn_forgotemail=findViewById(R.id.btnLaylaimk);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                onBackPressed();
                break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}

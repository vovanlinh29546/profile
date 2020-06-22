package com.example.giloliadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;

public class AdminLogin extends AppCompatActivity {
    EditText edtusername,edtpass;
    TextInputLayout inputtk,inputmk;
    Button btndangnhap,btndangky;
    CheckBox cbluu;
    SharedPreferences luutru;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        anhxa();
        setonclick();
        luutru = getSharedPreferences("loginthuchi", Context.MODE_PRIVATE);
        // nap thong tin len form tu sharedpreference
        Boolean luuthongtin = luutru.getBoolean("saveInformation", false);
        if (luuthongtin) {
            edtusername.setText(luutru.getString("username_info", ""));
            edtpass.setText(luutru.getString("password_info", ""));
            cbluu.setChecked(true);

        }
    }

    private void setonclick() {
btndangnhap.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if ( edtusername.getText().toString().equals("admin")&&edtpass.getText().toString().equals("123456")){
            Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

            Intent intent=new Intent(AdminLogin.this,MainActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(),"Sai mật khẩu hoặc tài khoản",
                    Toast.LENGTH_SHORT).show();
        }
        SharedPreferences.Editor editor = luutru.edit();

        if (cbluu.isChecked()) {
            editor.putString("username_info", edtusername.getText().toString());
            editor.putString("password_info", edtpass.getText().toString());
        }
        editor.putBoolean("saveInformation", cbluu.isChecked());
        editor.commit();
        finish();
    }
});
    }

    private void anhxa() {
        edtusername=findViewById(R.id.edtusername);
        edtpass=findViewById(R.id.edtpass);
        btndangnhap=findViewById(R.id.btndangnhap);
        cbluu=findViewById(R.id.cbluu);
        inputmk=findViewById(R.id.inputmk);

    }
}

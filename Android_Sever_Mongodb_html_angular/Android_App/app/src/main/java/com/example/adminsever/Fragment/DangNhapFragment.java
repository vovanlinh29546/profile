package com.example.adminsever.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.adminsever.Books;

import com.example.adminsever.R;


import com.example.adminsever.noUI;
import com.google.android.material.textfield.TextInputLayout;


import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class DangNhapFragment extends Fragment {
    EditText edtemail, edtmatkhau;
    Button btndangnhap;
    TextInputLayout inputemail, inputmatkhau;
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
    // lang nghe su kien login va xu ly
    private Emitter.Listener onLogin = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String data =  args[0].toString();
            noUI noui = new noUI(getContext());
            if(data == "true"){
                noui.toast("Login Success");
                Intent intent = new Intent(getContext(),
                        Books.class);
                startActivity(intent);
               // finish();
            }else{
                noui.toast("Login fail");
                hideDialog();
            }
            hideDialog();

        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dangnhap, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        //anh xa
        edtemail = view.findViewById(R.id.edt_loginEmail);
        edtmatkhau = view.findViewById(R.id.edt_loginMatKhau);
        btndangnhap = view.findViewById(R.id.btnDangNhap);
        inputemail = view.findViewById(R.id.input_loginemail);
        inputmatkhau = view.findViewById(R.id.input_loginmatkhau);

        mSocket.connect();
        mSocket.on("login", onLogin);
        // Progress dialog
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);
        // Login button Click Event
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email =edtemail.getText().toString().trim();
                String password = edtmatkhau.getText().toString().trim();
                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty()) {
                    // login user
                    checkLogin(email, password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }

            }
        });

    }
    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request

        pDialog.setMessage("Logging in ...");
        showDialog();

        mSocket.emit("login", email, password);

    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

        }











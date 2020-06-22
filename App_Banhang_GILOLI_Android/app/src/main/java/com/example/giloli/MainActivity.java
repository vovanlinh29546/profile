package com.example.giloli;

import androidx.annotation.NonNull;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.giloli.DAO.GioHangDAO;
import com.example.giloli.Model.GioHang;
import com.example.giloli.fragment.CaNhanFragment;
import com.example.giloli.fragment.CaNhanFragmentDone;
import com.example.giloli.fragment.DanhMucFragment;
import com.example.giloli.fragment.ThongBaoFragment;
import com.example.giloli.fragment.TimKiemFragment;
import com.example.giloli.fragment.TrangChuFragment;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FirebaseAuth mFirebase;
    //gio hang
    public static List<GioHang> listgiohangmain=null;
    static GioHangDAO giohangdaomain;
     String emailmain;
    FirebaseAuth mdatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navi_main);
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);
        mdatabase = FirebaseAuth.getInstance();
        if (mdatabase.getCurrentUser() != null) {
            emailmain = mdatabase.getCurrentUser().getEmail();
        }
        else {
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }
        if (listgiohangmain!=null){}
        else {
            String emai = emailmain;
            listgiohangmain=new ArrayList<>();
            giohangdaomain = new GioHangDAO(MainActivity.this);
           listgiohangmain=giohangdaomain.layGioHangTheoEmail(emailmain);

        }

        if (savedInstanceState == null){
            getFragmentManager().beginTransaction().replace(R.id.fragment_container_main,
                    new TrangChuFragment()).addToBackStack(null).commit();
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedfragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.bottom_trangchu:
                            selectedfragment = new TrangChuFragment();
                            getFragmentManager().beginTransaction();
                            break;
                        case R.id.bottom_danhmuc:
                            selectedfragment = new DanhMucFragment();
                            getFragmentManager().beginTransaction();
                            break;
                        case R.id.bottom_timkiem:
                            selectedfragment = new TimKiemFragment();
                            getFragmentManager().beginTransaction();
                            break;
                        case R.id.bottom_thongbao:
                            selectedfragment = new ThongBaoFragment();
                            getFragmentManager().beginTransaction();
                            break;
                        case R.id.bottom_canhan:
                            mFirebase = FirebaseAuth.getInstance();
                            if (mFirebase.getCurrentUser() != null || AccessToken.getCurrentAccessToken() != null) {
                                selectedfragment = new CaNhanFragmentDone();
                                getFragmentManager().beginTransaction();
                            } else {
                                selectedfragment = new CaNhanFragment();
                                getFragmentManager().beginTransaction();
                            }
                            break;
                    }
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container_main,
                            selectedfragment).addToBackStack(null).commit();

                    return true;
                }
                };

    @Override
    protected void onStart() {
        super.onStart();

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
                    emailmain = email1;

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

}




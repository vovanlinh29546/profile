package com.example.giloli.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.giloli.Adapter.GioHangAdaptercv;
import com.example.giloli.DAO.GioHangDAO;
import com.example.giloli.DAO.NguoiDungDAO;
import com.example.giloli.MainActivity;
import com.example.giloli.ManHinhCho_Activity;
import com.example.giloli.Model.GioHang;
import com.example.giloli.R;
import com.example.giloli.fragment.TrangChuFragment;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class GioHangActivity extends AppCompatActivity {
  static int sosptronggiohang=0;
 public static TextView txt_giatientamthoi;
Button btntieptucmuasamgiohang;
ImageView imgBackGH;
static TextView txttitleGH;
 public static   List<GioHang> listgiohangduynhat;
   static GioHangDAO giohangdaoduynhat;
    //dem gio hang
    // List<GioHang> listgiohang;
   // GioHangDAO giohangdao;
    String email=null;
    static    String email01=null;
    FirebaseAuth mdatabase;
    LinearLayout giohangkhongcothongtin;
    RelativeLayout relative_giohangcothongtin;
    RecyclerView rcv_giohang;
    Button btnTienHanhDatHang;
  static LinearLayout giohangkhongcothongtinduynhat;
  static LinearLayout lntienhanhdh;
   static RelativeLayout relative_giohangcothongtinduynhat;
    public static GioHangAdaptercv adaptergiohang;
    public static long tongtien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        mdatabase = FirebaseAuth.getInstance();
        if  (mdatabase.getCurrentUser()!=null){
            email = mdatabase.getCurrentUser().getEmail();
            email01=mdatabase.getCurrentUser().getEmail();
        } else {
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }
        giohangkhongcothongtin=findViewById(R.id.giohangkhongcothongtin);
        relative_giohangcothongtin=findViewById(R.id.scvgiohang);
        btnTienHanhDatHang = findViewById(R.id.btnTienHanhDatHang);
        giohangdaoduynhat = new GioHangDAO(this);
        listgiohangduynhat = new ArrayList<GioHang>();
        listgiohangduynhat=giohangdaoduynhat.layGioHangTheoEmailRCVGioHang(email);
        giohangkhongcothongtinduynhat=findViewById(R.id.giohangkhongcothongtin);
        relative_giohangcothongtinduynhat=findViewById(R.id.scvgiohang);
        lntienhanhdh=findViewById(R.id.lntienhanhdh);
        Toolbar toolbar = findViewById(R.id.toolbar_giohang);
        rcv_giohang=findViewById(R.id.rcv_giohang);
        imgBackGH=findViewById(R.id.imgBackGH);
        txttitleGH=findViewById(R.id.txttitleGH);

        LinearLayoutManager mlinerlayoutmanager=new LinearLayoutManager(this);
        rcv_giohang.setLayoutManager(mlinerlayoutmanager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, mlinerlayoutmanager.getOrientation());
        rcv_giohang.addItemDecoration(dividerItemDecoration);
        adaptergiohang = new GioHangAdaptercv(this, (ArrayList<GioHang>)listgiohangduynhat);
        rcv_giohang.setAdapter(adaptergiohang);
        toolbar.setTitle("Giỏ hàng"+"("+sosptronggiohang+")");
        sosptronggiohang = listgiohangduynhat.size();
        txttitleGH.setText("Giỏ hàng ("+sosptronggiohang+")");
        toolbar.setTitle("Giỏ hàng"+"("+sosptronggiohang+")");
        sosptronggiohang=listgiohangduynhat.size();
        txttitleGH.setText("Giỏ hàng ("+sosptronggiohang+")");
        setSupportActionBar(toolbar);
        btntieptucmuasamgiohang=findViewById(R.id.btntieptucmuasamgiohang);
        btntieptucmuasamgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iq = new Intent(GioHangActivity.this, MainActivity.class);
                startActivity(iq);
            }
        });
       anhienrl();
        imgBackGH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
                  });
        setBtnTienHanhDatHang();
        }



    public static void anhienrl(){
        if (listgiohangduynhat.size()<=0){
            giohangkhongcothongtinduynhat.setVisibility(View.VISIBLE);
            relative_giohangcothongtinduynhat.setVisibility(View.GONE);
            lntienhanhdh.setVisibility(View.GONE);
        }
        else {
            sosptronggiohang=MainActivity.listgiohangmain.size();
            txttitleGH.setText("Giỏ hàng ("+sosptronggiohang+")");
            giohangkhongcothongtinduynhat.setVisibility(View.GONE);
            lntienhanhdh.setVisibility(View.VISIBLE);
            relative_giohangcothongtinduynhat.setVisibility(View.VISIBLE);
        }
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

    @Override
    protected void onRestart() {
        super.onRestart();

    }
    @Override
    protected void onStart() {
        super.onStart();

    }



    @Override
    protected void onResume() {
        super.onResume();
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
    public void setBtnTienHanhDatHang(){
        btnTienHanhDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GioHangActivity.this,DatHangActivity.class);
                startActivity(intent);
            }
        });
    }
}
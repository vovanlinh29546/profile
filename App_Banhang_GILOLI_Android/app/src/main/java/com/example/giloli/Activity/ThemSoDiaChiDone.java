package com.example.giloli.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.giloli.Adapter.DiaChiNguoiDungAdaptercv;
import com.example.giloli.DAO.AddDiaChiNguoiDungDao;
import com.example.giloli.DAO.NguoiDungDAO;
import com.example.giloli.Model.DiaChiNguoiDung;
import com.example.giloli.R;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ThemSoDiaChiDone extends AppCompatActivity {
    RecyclerView lv;
    ArrayList<DiaChiNguoiDung> list;
    ArrayList<DiaChiNguoiDung> listdcnd;
    static AddDiaChiNguoiDungDao dao;
    public static DiaChiNguoiDungAdaptercv adapterdcdone;
    FirebaseAuth mdatabase;
    String email;
    Button btndiachidone;
    LinearLayout linerthemsodiachidone;
    RelativeLayout relathemsodiachidone;
    Button btnthemdiachikhongcothongtin;
    ImageView  imgBackTSDC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_so_dia_chi_done);
        mdatabase = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar_diachi_done);
        btndiachidone=findViewById(R.id.btndiachidone);
        linerthemsodiachidone=findViewById(R.id.linerthemsodiachidone);
        relathemsodiachidone=findViewById(R.id.relathemsodiachidone);
        imgBackTSDC=findViewById(R.id.imgBackTSDC);
        setSupportActionBar(toolbar);

        if (mdatabase.getCurrentUser()!=null){
            email = mdatabase.getCurrentUser().getEmail();
        } else {
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }

        lv = findViewById(R.id.rcv_diachidone);
        list = new ArrayList<>();
        listdcnd = new ArrayList<>();
        dao = new AddDiaChiNguoiDungDao(this);
        list = (ArrayList<DiaChiNguoiDung>)dao.layDiaChiTheoEmailDone(email);
        listdcnd= (ArrayList<DiaChiNguoiDung>) dao.layDiaChiTheoEmail(email);
        LinearLayoutManager mlinerlayoutmanager=new LinearLayoutManager(this);
        lv.setLayoutManager(mlinerlayoutmanager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, mlinerlayoutmanager.getOrientation());
        lv.addItemDecoration(dividerItemDecoration);
        adapterdcdone = new DiaChiNguoiDungAdaptercv(this, list);
        lv.setAdapter(adapterdcdone);
        btndiachidone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iq = new Intent(ThemSoDiaChiDone.this, ThemSoDiaChi.class);
                startActivity(iq);
            }
        });
        btnthemdiachikhongcothongtin=findViewById(R.id.btnthemdiachikhongcothongtin);
        btnthemdiachikhongcothongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iq = new Intent(ThemSoDiaChiDone.this, ThemSoDiaChi.class);
                startActivity(iq);
            }
        });
        if (listdcnd.size()<=0){
            linerthemsodiachidone.setVisibility(View.VISIBLE);
            relathemsodiachidone.setVisibility(View.GONE);
        }
        else {
            linerthemsodiachidone.setVisibility(View.GONE);
            relathemsodiachidone.setVisibility(View.VISIBLE);
            btndiachidone.setVisibility(View.VISIBLE);
        }
        imgBackTSDC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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

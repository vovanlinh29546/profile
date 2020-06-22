package com.example.giloli.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.giloli.Adapter.AdapterHoaDonChiTiet;
import com.example.giloli.DAO.HoaDonCtDAO;
import com.example.giloli.DAO.HoaDonDAO;
import com.example.giloli.Model.HoaDonCtSP;
import com.example.giloli.R;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HienHoaDonChiTietActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView textView;
    private List<HoaDonCtSP> hoaDonCTS;
    private HoaDonCtDAO hoaDonCtDAO;
    public static AdapterHoaDonChiTiet hoaDonCtAdapter;
    ImageView backHienHoaDon;
    private FirebaseAuth mDatabase;
    private String email;
    private HoaDonDAO hoaDonDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hien_hoa_don_chi_tiet);
        recyclerView = findViewById(R.id.recyclerViewCTHD);
        textView = findViewById(R.id.maHoaDonAo);
        backHienHoaDon=findViewById(R.id.backHienHoaDon);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        mDatabase = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        if (intent.getAction().equals("FromAdapter")) {
            Bundle bundle = intent.getBundleExtra("box");
            String maHoaDon = bundle.getString("maHoaDon");
            textView.setText(maHoaDon);
            hoaDonCTS = new ArrayList<HoaDonCtSP>();
            hoaDonCtDAO = new HoaDonCtDAO(this);
            hoaDonCTS = hoaDonCtDAO.getHoaDonCt(textView.getText().toString());
            hoaDonCtAdapter = new AdapterHoaDonChiTiet(this, hoaDonCTS);
        }
        if (mDatabase.getCurrentUser()!=null){
            email = mDatabase.getCurrentUser().getEmail();
        } else {
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }
        if (intent.getAction().equals("FromCND")){
            hoaDonDAO = new HoaDonDAO(this);
            hoaDonCTS = new ArrayList<HoaDonCtSP>();
            hoaDonCTS = hoaDonDAO.getHoaDonCtTheoEmail(email);
            hoaDonCtAdapter = new AdapterHoaDonChiTiet(this,hoaDonCTS);
        }

        recyclerView.setAdapter(hoaDonCtAdapter);
        setBack();
    }

    private void setBack() {
        backHienHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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

}

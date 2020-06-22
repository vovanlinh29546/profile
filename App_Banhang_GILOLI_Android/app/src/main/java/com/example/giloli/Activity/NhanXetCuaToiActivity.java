package com.example.giloli.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giloli.Adapter.NhanXetAdapter;
import com.example.giloli.DAO.NhanXetDao;
import com.example.giloli.Model.NhanXet;
import com.example.giloli.R;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.firebase.auth.FirebaseAuth;

import android.os.Bundle;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NhanXetCuaToiActivity extends AppCompatActivity {
    private NhanXetDao nhanXetDao;
    public static NhanXetAdapter nhanXetAdapter;
    private RecyclerView recyclerView;
    private ArrayList<NhanXet> nhanXets;
    private String email;
    private FirebaseAuth mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_xet_cua_toi);
        recyclerView = findViewById(R.id.recyclerViewNx);
        mDatabase = FirebaseAuth.getInstance();
        nhanXetDao = new NhanXetDao(this);
        nhanXets = new ArrayList<NhanXet>();
        if (mDatabase.getCurrentUser()!= null){
            email = mDatabase.getCurrentUser().getEmail();
        } else {
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }
        nhanXets = nhanXetDao.getNhanXetTheoEmail(email);
        nhanXetAdapter = new NhanXetAdapter(this,nhanXets);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(nhanXetAdapter);
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

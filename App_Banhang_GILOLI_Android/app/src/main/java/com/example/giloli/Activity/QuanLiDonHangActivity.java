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

import com.example.giloli.Adapter.Adapter_QuanLiDonHang;
import com.example.giloli.DAO.HoaDonDAO;
import com.example.giloli.Model.HoaDon;
import com.example.giloli.R;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.firebase.auth.FirebaseAuth;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuanLiDonHangActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private HoaDonDAO hoaDonDAO;
    private List<HoaDon> list;
    private TextView textView;
    public static Adapter_QuanLiDonHang adapter_quanLiDonHang;
    String emailNguoiDung;
    FirebaseAuth mDatabase;
    ImageView imgBackQLDH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_li_don_hang);
        recyclerView = findViewById(R.id.recyclerQLDH);
        imgBackQLDH=findViewById(R.id.imgBackQLDH);
        textView = findViewById(R.id.txtJust);
        hoaDonDAO = new HoaDonDAO(this);
        list = new ArrayList<HoaDon>();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        mDatabase = FirebaseAuth.getInstance();
        if (mDatabase.getCurrentUser()!=null){
            emailNguoiDung = mDatabase.getCurrentUser().getEmail();
        } else {
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }
        receveiInten();
        setback();
    }

    private void setback() {
        imgBackQLDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void receveiInten(){
        Intent intent = getIntent();
        if (intent.getAction().equals("QLDH")){
            list = hoaDonDAO.getHoaDon(emailNguoiDung);
            adapter_quanLiDonHang = new Adapter_QuanLiDonHang(QuanLiDonHangActivity.this,list);
            recyclerView.setAdapter(adapter_quanLiDonHang);
        } else if (intent.getAction().equals("Giloli")){
            textView.setText("Đã duyệt đơn");
            list = hoaDonDAO.getHoaDonTheoTt(textView.getText().toString(),emailNguoiDung);
            adapter_quanLiDonHang = new Adapter_QuanLiDonHang(QuanLiDonHangActivity.this,list);
            recyclerView.setAdapter(adapter_quanLiDonHang);
        } else if (intent.getAction().equals("DCVC")){
            textView.setText("Đang vận chuyển");
            list = hoaDonDAO.getHoaDonTheoTt(textView.getText().toString(),emailNguoiDung);
            adapter_quanLiDonHang = new Adapter_QuanLiDonHang(QuanLiDonHangActivity.this,list);
            recyclerView.setAdapter(adapter_quanLiDonHang);
        } else if (intent.getAction().equals("HDH")){
            textView.setText("Đã huỷ đơn hàng");
            list = hoaDonDAO.getHoaDonTheoTt(textView.getText().toString(),emailNguoiDung);
            adapter_quanLiDonHang = new Adapter_QuanLiDonHang(QuanLiDonHangActivity.this,list);
            recyclerView.setAdapter(adapter_quanLiDonHang);
        } else {
            textView.setText("Đã vận chuyển");
            list = hoaDonDAO.getHoaDonTheoTt(textView.getText().toString(),emailNguoiDung);
            adapter_quanLiDonHang = new Adapter_QuanLiDonHang(QuanLiDonHangActivity.this,list);
            recyclerView.setAdapter(adapter_quanLiDonHang);
        }
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
                    emailNguoiDung = email1;
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

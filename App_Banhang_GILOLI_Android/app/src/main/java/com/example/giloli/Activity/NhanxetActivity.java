package com.example.giloli.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.giloli.DAO.NguoiDungDAO;
import com.example.giloli.DAO.NhanXetDao;
import com.example.giloli.Model.NguoiDung;
import com.example.giloli.Model.NhanXet;
import com.example.giloli.NothingSelectedSpinnerAdapter;
import com.example.giloli.R;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NhanxetActivity extends AppCompatActivity {
EditText spndanhgia,edt_nhanxet;
String[] itemdanhgia={"Rất không hài lòng","Không hài lòng","Bình thường","Hài lòng","Rất hài lòng"};
String dohailong=null;
ArrayAdapter adapterdohailong;
String url,ten,masp;
ImageView img_nhanxet_url;
TextView txt_nhanxet_tensp;
Button btntienhanhNhanXet;
    FirebaseAuth mdatabase;
    String email;
ImageView backnx;
    NhanXetDao nxdao;
   ArrayList<NhanXet> nhanxets2;
    NguoiDungDAO nddao;
    ArrayList<NguoiDung> nguoidung;
    int newID=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhanxet);
        mdatabase = FirebaseAuth.getInstance();
        if (mdatabase.getCurrentUser() != null) {
            email = mdatabase.getCurrentUser().getEmail();

        }
        else {
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }
        nxdao=new NhanXetDao(this);
        nhanxets2=new ArrayList<>();
        nddao=new NguoiDungDAO(this);
        nguoidung=new ArrayList<>();
        nguoidung= (ArrayList<NguoiDung>) nddao.layNguoiDungTheoEmail(email);
        anhxa();
        getvalueCTSP();
        onclick();
    }

    private void onclick() {
        spndanhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setspiner();
            }
        });
        btntienhanhNhanXet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               nhanxets2 = (ArrayList<NhanXet>) nxdao.getAllNhanXet();

                Date now = new Date();
                Date alsoNow = Calendar.getInstance().getTime();
                String nowAsString = new SimpleDateFormat("dd-MM-yyyy").format(alsoNow);
                if (spndanhgia.getText().toString().isEmpty()){
                    Toast.makeText(NhanxetActivity.this, "Bạn chưa chọn độ hài lòng", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edt_nhanxet.getText().toString().isEmpty()){
                    Toast.makeText(NhanxetActivity.this, "Bạn chưa nhận xét", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (nhanxets2.size()>0)
                    newID= Integer.parseInt(nhanxets2.get(nhanxets2.size()-1).getIdnhanxet());
                newID++;
                NhanXet nx = new NhanXet();
                String ten=nguoidung.get(0).getHoten();
                nx.setIdnhanxet(String.valueOf(newID));
                nx.setEmail(email);
                nx.setMasp(masp);
                nx.setNhanxet(edt_nhanxet.getText().toString());
                nx.setNgaynhanxet(nowAsString);
                nx.setDohailong(spndanhgia.getText().toString());
                nx.setHoten(ten);

                nxdao.addNhanXet(nx);
                Toast.makeText(NhanxetActivity.this, "Nhận xét thành công", Toast.LENGTH_SHORT).show();
                spndanhgia.setText("");
                edt_nhanxet.setText("");
            }
        });

        backnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void getvalueCTSP() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("nhanxet");
        if (bundle != null) {
            url = bundle.getString("url","" );
            ten = bundle.getString("ten", "");
            masp = bundle.getString("masp", "");
            Glide.with(this).asBitmap().load(url).into(img_nhanxet_url);
            txt_nhanxet_tensp.setText(ten);

            //dua data len EditText

        }
    }

    private void setspiner() {

        AlertDialog.Builder mBuilder=new AlertDialog.Builder(NhanxetActivity.this);
        View mView=getLayoutInflater().inflate(R.layout.item_dohailong,null);
        mBuilder.setTitle("Độ hài lòng");
        mBuilder.setView(mView);
        final ListView rcv=mView.findViewById(R.id.lv_nhanxet_dohailong);
        adapterdohailong= new ArrayAdapter(NhanxetActivity.this,android.R.layout.simple_list_item_1,itemdanhgia);
        rcv.setAdapter(adapterdohailong);

        final AlertDialog dialog=mBuilder.create();
        rcv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dohailong=itemdanhgia[i];
                Toast.makeText(NhanxetActivity.this, dohailong, Toast.LENGTH_SHORT).show();
                spndanhgia.setText(dohailong);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void anhxa() {
        spndanhgia=findViewById(R.id.spn_nhanxet_danhgia);
        edt_nhanxet=findViewById(R.id.edt_nhanxet);
    img_nhanxet_url=findViewById(R.id.img_nhanxet_url);
        txt_nhanxet_tensp=findViewById(R.id.txt_nhanxet_tensp);
        btntienhanhNhanXet=findViewById(R.id.btntienhanhNhanXet);
        backnx=findViewById(R.id.imgBackNX);
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

package com.example.giloli.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.giloli.Adapter.spinTenPhuongXaAdapter;
import com.example.giloli.Adapter.spinTenQuanAdapter;
import com.example.giloli.DAO.AddDiaChiNguoiDungDao;
import com.example.giloli.DAO.AddPhuongXaDao;
import com.example.giloli.DAO.AddQuanDao;
import com.example.giloli.Model.DiaChiNguoiDung;
import com.example.giloli.Model.DiaChi_QuanHuyen;
import com.example.giloli.Model.DiaChi_ThiXa;
import com.example.giloli.Model.Image;
import com.example.giloli.Model.NguoiDung;
import com.example.giloli.NothingSelectedSpinnerAdapter;
import com.example.giloli.R;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ThemSoDiaChi extends AppCompatActivity {
       EditText spnQuanHuyentst,edt_DiaChiPhuongXatst,edt_DiaChiHoTen,edt_DiaChiSdt,edt_DiaChiDiachi;
       TextInputLayout input_DiaChi_hoten,input_DiaChi_sdt,input_DiaChi_diachi,input_DiaChi_quan,input_DiaChi_px;
       Button btnDiaChimoi;
    List<DiaChi_QuanHuyen> list;
    AddQuanDao qdao;
   public static   spinTenQuanAdapter adapterTenQuan;
    List<DiaChi_ThiXa> listpx;
    AddPhuongXaDao pxdao;
    public static ArrayAdapter adapterTenPhuongXa;
    String tenquan=null;
    String tenpx=null;
    //them dia chi
    List<DiaChiNguoiDung> listdcnd;
    AddDiaChiNguoiDungDao dcnddao;
    FirebaseAuth mdatabase;
    String email;
    int id=0;
    String hoten,sdt,quan,px,dc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_so_dia_chi);
        Toolbar toolbar = findViewById(R.id.toolbar_themsodiachi);
        toolbar.setTitle("Thêm sổ địa chỉ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        mdatabase = FirebaseAuth.getInstance();
        anhxa();
        intentedit();
         qdao = new AddQuanDao(this);
        pxdao = new AddPhuongXaDao(this);
        list = new ArrayList<DiaChi_QuanHuyen>();
        listpx = new ArrayList<DiaChi_ThiXa>();
        dcnddao = new AddDiaChiNguoiDungDao(this);
        listdcnd = new ArrayList<DiaChiNguoiDung>();
        final List<DiaChiNguoiDung> diachis2= dcnddao.getDiaChiNguoiDung();
      spnQuanHuyentst.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              AlertDialog.Builder mBuilder=new AlertDialog.Builder(ThemSoDiaChi.this);
              View mView=getLayoutInflater().inflate(R.layout.dialogtenquan,null);
              mBuilder.setView(mView);
            final ListView rcv=mView.findViewById(R.id.rcv_tenQuan);
            final SearchView sr=mView.findViewById(R.id.searchtenquan);
final ImageButton imgexitquan=mView.findViewById(R.id.imgexitquan);
              list = (ArrayList<DiaChi_QuanHuyen>)qdao.getTenQuan();
              adapterTenQuan = new spinTenQuanAdapter(ThemSoDiaChi.this, (ArrayList<DiaChi_QuanHuyen>) list);
              rcv.setAdapter(adapterTenQuan);
              final AlertDialog dialog=mBuilder.create();
              rcv.setTextFilterEnabled(true);

           sr.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
               @Override
               public boolean onQueryTextSubmit(String s) {
                   return false;
               }

               @Override
               public boolean onQueryTextChange(String s) {
                  // if (s==null) {
                 //      adapterTenQuan.resetData();
                //   }
                   adapterTenQuan.getFilter().filter(s);
                   return false;
               }
           });

rcv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        tenquan=list.get(i).getTenQuan();
        spnQuanHuyentst.setText(tenquan);
        dialog.dismiss();
        LayTenQuan();
    }
});

              imgexitquan.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      dialog.dismiss();
                  }
              });
              dialog.show();

            //  Toast.makeText(ThemSoDiaChi.this,"Đăng xuất thành công",Toast.LENGTH_SHORT).show();
          }
      });

        edt_DiaChiPhuongXatst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tenquan!=null){
                    LayTenQuan();
                }
                else {
                    Toast.makeText(ThemSoDiaChi.this,"Bạn cần chòn tên Quận/Huyện",Toast.LENGTH_SHORT).show();
             return;
                }
            }
        });
        btnDiaChimoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id!=0){
                    if (mdatabase.getCurrentUser()!=null)
                    {
                        email = mdatabase.getCurrentUser().getEmail();
                    } else
                    {
                        loadUserProfile(AccessToken.getCurrentAccessToken());
                    }

                    DiaChiNguoiDung nguoidung = new DiaChiNguoiDung(id,edt_DiaChiHoTen.getText().toString(),
                            edt_DiaChiSdt.getText().toString(),
                            spnQuanHuyentst.getText().toString(),
                            edt_DiaChiPhuongXatst.getText().toString(),
                            edt_DiaChiDiachi.getText().toString(),
                            email
                            );

                    dcnddao.updateDiaChiND(nguoidung);

                }
                else {
                    if (addDiaChi() == false){

                    }
                    else {
                        listdcnd = dcnddao.getDiaChiNguoiDung();
                        if (mdatabase.getCurrentUser()!=null) {
                            email = mdatabase.getCurrentUser().getEmail();
                        }
                        else {
                            loadUserProfile(AccessToken.getCurrentAccessToken());
                        }
                        DiaChiNguoiDung dcq = new DiaChiNguoiDung();
                        int newID=0;
                        if (diachis2.size()>0)
                            newID=diachis2.get(diachis2.size()-1).getiDDiaChiDone();
                        newID++;
                        dcq.setiDDiaChiDone(newID);
                        dcq.setHoten(edt_DiaChiHoTen.getText().toString());
                        dcq.setSoDienThoai(edt_DiaChiSdt.getText().toString());
                        dcq.setTenQuan(spnQuanHuyentst.getText().toString());
                        dcq.setTenPhuong(edt_DiaChiPhuongXatst.getText().toString());
                        dcq.setDiaChi(edt_DiaChiDiachi.getText().toString());
                        dcq.setEmail(email);

                        dcnddao.addDiaChiNguoiDung(dcq);
                        Toast.makeText(ThemSoDiaChi.this,"Thêm thành công",Toast.LENGTH_SHORT).show();
                        edt_DiaChiHoTen.setText("");
                        edt_DiaChiSdt.setText("");
                        spnQuanHuyentst.setText("");
                        edt_DiaChiPhuongXatst.setText("");
                        edt_DiaChiDiachi.setText("");


                    }
                }


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

    private void anhxa() {
        spnQuanHuyentst=findViewById(R.id.edt_DiaChiQuan);
        edt_DiaChiPhuongXatst=findViewById(R.id.edt_DiaChiPhuongXa);
        edt_DiaChiHoTen=findViewById(R.id.edt_DiaChiHoTen);
        edt_DiaChiSdt =findViewById(R.id.edt_DiaChiSdt);
        edt_DiaChiDiachi=findViewById(R.id.edt_DiaChiDiachi);
        btnDiaChimoi=findViewById(R.id.btnDiaChimoi);
    }
    private boolean addDiaChi() {
        if (edt_DiaChiHoTen.getText().toString().isEmpty()) {
            Toast.makeText(ThemSoDiaChi.this, "Bạn cần nhập họ tên", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (spnQuanHuyentst.getText().toString().isEmpty()) {
            Toast.makeText(ThemSoDiaChi.this, "Bạn cần nhập quận/huyện", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edt_DiaChiPhuongXatst.getText().toString().isEmpty()) {
            Toast.makeText(ThemSoDiaChi.this, "Bạn cần nhập số phường/xã", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edt_DiaChiSdt.getText().toString().isEmpty()) {
            Toast.makeText(ThemSoDiaChi.this, "Bạn cần nhập số điện thoại", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edt_DiaChiDiachi.getText().toString().isEmpty()) {
            Toast.makeText(ThemSoDiaChi.this, "Bạn cần nhập địa chỉ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    public void LayTenQuan(){

    AlertDialog.Builder mBuilder=new AlertDialog.Builder(ThemSoDiaChi.this);
    View mView=getLayoutInflater().inflate(R.layout.dialogtenphuongxa,null);
    mBuilder.setTitle("Chọn Phường/Xã");
    mBuilder.setView(mView);
    final ListView rcv=mView.findViewById(R.id.rcv_tenPhuongXa);
    final SearchView srpx=mView.findViewById(R.id.searchtenphuongxa);
    final ImageButton imgexitpx=mView.findViewById(R.id.imgexitpx);
    listpx = (ArrayList<DiaChi_ThiXa>)pxdao.layTenPhuongXaTheoTenQuantest(tenquan);

    adapterTenPhuongXa= new ArrayAdapter(ThemSoDiaChi.this,android.R.layout.simple_list_item_1, (ArrayList<DiaChi_ThiXa>) listpx);
    rcv.setAdapter(adapterTenPhuongXa);
    rcv.setTextFilterEnabled(true);
    srpx.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            adapterTenPhuongXa.getFilter().filter(s);
            return false;
        }
    });
    final AlertDialog dialog=mBuilder.create();
    rcv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
             tenpx=listpx.get(i).getTenPhuongXa();
            edt_DiaChiPhuongXatst.setText(tenpx);
            dialog.dismiss();
        }
    });
    imgexitpx.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog.dismiss();
        }
    });


    dialog.show();


}
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:

                onBackPressed();
                //   fragmentManager.beginTransaction().replace(R.id.content_frame,fragment).addToBackStack("tag").commit();
                return true;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void intentedit(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("suaThongTin");
        if (bundle != null) {
            id = bundle.getInt("id" );
              hoten = bundle.getString("ht", "");
             sdt = bundle.getString("sdt", "");
             quan = bundle.getString("quan", "");
             px = bundle.getString("px", "");
             dc = bundle.getString("dc", "");



            //dua data len EditText

            edt_DiaChiHoTen.setText(hoten);
            edt_DiaChiSdt.setText(sdt);
            spnQuanHuyentst.setText(quan);
            edt_DiaChiPhuongXatst.setText(px);
            edt_DiaChiDiachi.setText(dc);

        }
    }

}

package com.example.giloli.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.giloli.Adapter.NhanXetAdapter;
import com.example.giloli.DAO.NhanXetDao;
import com.example.giloli.Model.NhanXet;
import com.example.giloli.R;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class XemTatCaNhanXet extends AppCompatActivity {
RecyclerView rcv_xtcnx_nx;
TextView xtcnx_tongsoluotbinhchon,xtcnx_tatcanhanxet,xtcnx_rathailong,xtcnx_hailong,xtcnx_binhthuong,xtcnx_khonghailong,xtcnx_ratkhonghailong;
NhanXetDao nxdao;
ArrayList<NhanXet> nhanXetArrayList;
  public static int soLuong = 0;
String masp;
    int tongluotnx;
    int tongluotnxxtcnx;
    public static NhanXetAdapter adapternhanxet;
    int rathailong=0,hailong=0,binhthuong=0,khonghailong=0,ratkhonghailong=0;
    ScrollView scrollView;
    LinearLayout ln_khongconhanxet;
    ImageView imgBackXTCNX;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_tat_ca_nhan_xet);
        anhxa();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("xtcnx");
        if (bundle != null) {
            masp = bundle.getString("maspxtcnx", "");

        }

        nxdao=new NhanXetDao(this);
        nhanXetArrayList=new ArrayList<>();
        nhanXetArrayList= (ArrayList<NhanXet>) nxdao.getNhanXetTheoMaSP(masp);
      //  setDuLieu();
        setrcview();
        setback();
    }

    private void setback() {
        imgBackXTCNX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setDuLieu() {

        tongluotnx=ChiTietSPActivity.nhanXetArrayListctsp.size();
        tongluotnxxtcnx=nhanXetArrayList.size();
        soLuong = tongluotnxxtcnx;
        if (ChiTietSPActivity.nhanXetArrayListctsp==null){
            xtcnx_tongsoluotbinhchon.setText(tongluotnxxtcnx + " bình chọn");
            xtcnx_tatcanhanxet.setText("Tất cả nhận xét " + "(" + tongluotnxxtcnx + ")");
            scrollView.setVisibility(View.GONE);
            ln_khongconhanxet.setVisibility(View.VISIBLE);

            for (int j = 0; j <= nhanXetArrayList.size() - 1; j++) {

                if (nhanXetArrayList.get(j).getDohailong().equals("Rất hài lòng")){
                    rathailong++;
                }
                if (nhanXetArrayList.get(j).getDohailong().equals("Hài lòng")){
                    hailong++;
                }
                if (nhanXetArrayList.get(j).getDohailong().equals("Bình thường")){
                    binhthuong++;
                }
                if (nhanXetArrayList.get(j).getDohailong().equals("Không hài lòng")){
                    khonghailong++;
                }
                if (nhanXetArrayList.get(j).getDohailong().equals("Rất không hài lòng")){
                    ratkhonghailong++;
                }
            }
              xtcnx_rathailong.setText("Rất hài lòng ("+rathailong+")");
            xtcnx_hailong.setText("Hài lòng " +"("+hailong+")");
            xtcnx_binhthuong.setText("Bình thường "+"("+binhthuong+")");
            xtcnx_khonghailong.setText("Không hài lòng "+"("+khonghailong+")");
            xtcnx_ratkhonghailong.setText("Rất không hài lòng "+"("+ratkhonghailong +")");




        }

        else {
            xtcnx_tongsoluotbinhchon.setText(tongluotnx+" bình chọn");
            xtcnx_tatcanhanxet.setText("Tất cả nhận xét "+"("+tongluotnx+")");
            scrollView.setVisibility(View.VISIBLE);
            ln_khongconhanxet.setVisibility(View.GONE);
            for (int j = 0; j <= ChiTietSPActivity.nhanXetArrayListctsp.size() - 1; j++) {

                if (ChiTietSPActivity.nhanXetArrayListctsp.get(j).getDohailong().equals("Rất hài lòng")){
                    rathailong++;
                }
                if (ChiTietSPActivity.nhanXetArrayListctsp.get(j).getDohailong().equals("Hài lòng")){
                    hailong++;
                }
                if (ChiTietSPActivity.nhanXetArrayListctsp.get(j).getDohailong().equals("Bình thường")){
                    binhthuong++;
                }
                if (ChiTietSPActivity.nhanXetArrayListctsp.get(j).getDohailong().equals("Không hài lòng")){
                    khonghailong++;
                }
                if (ChiTietSPActivity.nhanXetArrayListctsp.get(j).getDohailong().equals("Rất không hài lòng")){
                    ratkhonghailong++;
                }
            }

            xtcnx_rathailong.setText("Rất hài lòng ("+rathailong+")");
            xtcnx_hailong.setText("Hài lòng " +"("+hailong+")");
            xtcnx_binhthuong.setText("Bình thường "+"("+binhthuong+")");
            xtcnx_khonghailong.setText("Không hài lòng "+"("+khonghailong+")");
            xtcnx_ratkhonghailong.setText("Rất không hài lòng "+"("+ratkhonghailong +")");




        }



    }

    private void setrcview() {
        final LinearLayoutManager mlinerlayoutmanager=new LinearLayoutManager(this);
        rcv_xtcnx_nx.setLayoutManager(mlinerlayoutmanager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, mlinerlayoutmanager.getOrientation());
        rcv_xtcnx_nx.addItemDecoration(dividerItemDecoration);
        adapternhanxet = new NhanXetAdapter(this, (ArrayList<NhanXet>)nhanXetArrayList);
        rcv_xtcnx_nx.setAdapter(adapternhanxet);

    }




    private void anhxa() {
        rcv_xtcnx_nx=findViewById(R.id.rcv_xtcnx_nx);
        xtcnx_tongsoluotbinhchon=findViewById(R.id.xtcnx_tongsoluotbinhchon);
                xtcnx_tatcanhanxet=findViewById(R.id.xtcnx_tatcanhanxet);
        xtcnx_rathailong=findViewById(R.id.xtcnx_rathailong);
                xtcnx_hailong=findViewById(R.id.xtcnx_hailong);
                xtcnx_binhthuong=findViewById(R.id.xtcnx_binhthuong);
                xtcnx_khonghailong=findViewById(R.id.xtcnx_khonghailong);
                xtcnx_ratkhonghailong=findViewById(R.id.xtcnx_ratkhonghailong);
        scrollView=findViewById(R.id.scv_tatcanhanxet);
        ln_khongconhanxet=findViewById(R.id.ln_khongconhanxet);
        imgBackXTCNX=findViewById(R.id.imgBackXTCNX);

    }

    @Override
    protected void onStart() {
        super.onStart();
        setDuLieu();
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

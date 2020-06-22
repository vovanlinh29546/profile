package com.example.giloli.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.giloli.Adapter.ImageViewAdapter;
import com.example.giloli.Adapter.NhanXetAdapter;
import com.example.giloli.Adapter.NhanXetAdapteritem3;
import com.example.giloli.DAO.GioHangDAO;
import com.example.giloli.DAO.NguoiDungDAO;
import com.example.giloli.DAO.NhanXetDao;
import com.example.giloli.DAO.SanPhamDAO;
import com.example.giloli.DAO.TheLoaiDAO;
import com.example.giloli.MainActivity;
import com.example.giloli.ManHinhCho_Activity;
import com.example.giloli.Model.GioHang;
import com.example.giloli.Model.NhanXet;
import com.example.giloli.Model.SanPham;
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

public class ChiTietSPActivity extends AppCompatActivity {
    List<SanPham> list;
    SanPhamDAO sanPhamDAO;
    private TextView tenSp, giaSp, danhMucSp, cungCap, thuongHieu, trongLuong, huongDan, moTaSp, nhanXet,txt_ctsp_xemtatcanx;
    private SanPham sanPham;
    private List<String> listImg;
    private ImageViewAdapter imageViewAdapter;
    private ViewPager viewPager;
    TheLoaiDAO theLoaiDAO;
    //them vao gio hang
    Button btnThemVaoGioHang,btnnhanxet;
    List<GioHang> listgiohang;
    GioHangDAO giohangdao;
    String email=null;
    FirebaseAuth mdatabase;
    ImageView imgBackCTSP;
    RelativeLayout ctsp_giohang;
    static TextView txtcount_shop_ctsp;
    //xem tat ca nhan xet
    NhanXetDao nxdaoctsp;
    static NhanXetDao nxdaoctspduynhat;
    public static ArrayList<NhanXet> nhanXetArrayListctsp=null;
    RecyclerView rcv_ctsp_nhanxet;
    public static NhanXetAdapteritem3 adapternhanxetctsp;
    ArrayList<NhanXet> nhanXetArrayListct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sp);
        mdatabase = FirebaseAuth.getInstance();
        anhXa();
        Settext();
        themvaogiohang();
        setclick();
        nxdaoctsp=new NhanXetDao(this);
        nxdaoctspduynhat=new NhanXetDao(this);
        nhanXetArrayListctsp=new ArrayList<>();
        nhanXetArrayListctsp= (ArrayList<NhanXet>) nxdaoctsp.getNhanXetTheoMaSPstatic(sanPham.getMaSanPham());
        nhanXetArrayListct=new ArrayList<>();
        nhanXetArrayListct= (ArrayList<NhanXet>) nxdaoctsp.getNhanXetTheoMaSP3SPCTSP(sanPham.getMaSanPham());
        setrcv();
    }

    private void setrcv() {
        final LinearLayoutManager mlinerlayoutmanager=new LinearLayoutManager(this);
        rcv_ctsp_nhanxet.setLayoutManager(mlinerlayoutmanager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, mlinerlayoutmanager.getOrientation());
        rcv_ctsp_nhanxet.addItemDecoration(dividerItemDecoration);
        adapternhanxetctsp = new NhanXetAdapteritem3(this, (ArrayList<NhanXet>)nhanXetArrayListct);
        rcv_ctsp_nhanxet.setAdapter(adapternhanxetctsp);
    }

    private void setclick() {
        btnnhanxet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> urlanhnx = new ArrayList<>();
                urlanhnx = sanPham.getLinkUrl();
                Intent intent = new Intent(ChiTietSPActivity.this, NhanxetActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("url",urlanhnx.get(0));
                bundle.putString("ten",tenSp.getText().toString());
                bundle.putString("masp",sanPham.getMaSanPham());
                intent.putExtra("nhanxet",bundle);

                startActivity(intent);
            }
        });
    }

    public static void demgiohangctsp() {
        int count=(MainActivity.listgiohangmain.size());
        if (count<=0){
            txtcount_shop_ctsp.setVisibility(View.GONE);
        }
        else {
            txtcount_shop_ctsp.setText(count+"");
            txtcount_shop_ctsp.setVisibility(View.VISIBLE);
        }
    }

    private void themvaogiohang() {
        listgiohang = new ArrayList<GioHang>();
        giohangdao = new GioHangDAO(this);
        final List<GioHang> gioHangs2= giohangdao.layGioHang();

        if (mdatabase.getCurrentUser()!=null){
            email = mdatabase.getCurrentUser().getEmail();
        } else {
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }



        btnThemVaoGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if  (email==null){
                    Toast.makeText(ChiTietSPActivity.this,"Bạn cần phải đăng nhập",Toast.LENGTH_SHORT).show();
return;
                }
                else {
                GioHang dcq = new GioHang();
                final List<GioHang> gioHangs3= giohangdao.layGioHangTheoEmail(email);
                int newID=0;
                if (gioHangs2.size()>0)
                    newID=gioHangs2.get(gioHangs2.size()-1).getIdGioiHang();
                newID++;
                List<String> urlanhgiohang = new ArrayList<>();
                urlanhgiohang = sanPham.getLinkUrl();
                for (int j = 0; j <= gioHangs3.size() - 1; j++) {
                    if (gioHangs3.size() > 0) {
                        if (gioHangs3.get(j).getTenHang().equals(sanPham.getMaSanPham())) {
                            int slht = Integer.parseInt(gioHangs3.get(j).getSoLuong());
                            int slmn= Integer.parseInt(gioHangs3.get(j).getSoLuong())+1;
                            long giaht=Long.parseLong(gioHangs3.get(j).getGiaTien());
                            long giamn=(giaht*slmn)/slht;

                            GioHang gh = new GioHang(gioHangs3.get(j).getIdGioiHang()
                                    ,email
                                    ,gioHangs3.get(j).getTenHang(),String.valueOf(slmn)
                                    ,gioHangs3.get(j).getUrlhang(),String.valueOf(giamn)
                                    ,gioHangs3.get(j).getMaSp());

                            giohangdao.updateGioHang(gh);
                            return;
                        }
                    }
                }

                dcq.setIdGioiHang(newID);
                dcq.setEmail(email);
                dcq.setTenHang(sanPham.getTenSanPham());
                dcq.setSoLuong("1");
                dcq.setUrlhang(urlanhgiohang.get(0));
                dcq.setGiaTien(sanPham.getGiaThanh());
                dcq.setMaSp(sanPham.getMaSanPham());

                giohangdao.addGioHang(dcq);
                Toast.makeText(ChiTietSPActivity.this,"Thêm thành công",Toast.LENGTH_SHORT).show();}
            }
        });

        imgBackCTSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ctsp_giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iq = new Intent(ChiTietSPActivity.this, GioHangActivity.class);
                startActivity(iq);
            }
        });
        txt_ctsp_xemtatcanx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietSPActivity.this, XemTatCaNhanXet.class);

                Bundle bundle = new Bundle();
                bundle.putString("maspxtcnx",sanPham.getMaSanPham());
                intent.putExtra("xtcnx",bundle);

                startActivity(intent);
            }
        });
    }

    private void anhXa(){
        tenSp = findViewById(R.id.txtTenCTSP);
        giaSp = findViewById(R.id.txtGiaCTSP);
        danhMucSp = findViewById(R.id.txtDM);
        cungCap = findViewById(R.id.txtCC);
        thuongHieu = findViewById(R.id.txtTH);
        trongLuong = findViewById(R.id.txtTL);
        huongDan = findViewById(R.id.txtHD);
        moTaSp = findViewById(R.id.txtMT);
        viewPager = findViewById(R.id.viewPagerCTSP);
        btnThemVaoGioHang=findViewById(R.id.btnThemVaoGioHang);
        imgBackCTSP=findViewById(R.id.imgBackCTSP);
        ctsp_giohang=findViewById(R.id.ctsp_giohang);
        txtcount_shop_ctsp=findViewById(R.id.txtcount_shop_ctsp);
        btnnhanxet=findViewById(R.id.btnNhanXet);
        txt_ctsp_xemtatcanx=findViewById(R.id.txt_ctsp_xemtatcanx);
        rcv_ctsp_nhanxet=findViewById(R.id.rcv_ctsp_nhanxet);
    }
    private void Settext(){
        Intent intent = getIntent();
        int position = intent.getIntExtra("STT",0);
        String ml = intent.getStringExtra("MaLoai");
        sanPhamDAO = new SanPhamDAO(this);
        theLoaiDAO = new TheLoaiDAO(this);
        list = new ArrayList<>();
        list = sanPhamDAO.layHetSanPhamTheoDm1(ml);
        sanPham = new SanPham();
        sanPham = list.get(position);
        listImg = new ArrayList<>();
        listImg = sanPham.getLinkUrl();
        imageViewAdapter = new ImageViewAdapter(ChiTietSPActivity.this,listImg);
        viewPager.setAdapter(imageViewAdapter);
        theLoaiDAO.layDanhMuc(sanPham.getMaLoai(),danhMucSp);
        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedNumber = formatter.format(Double.parseDouble(sanPham.getGiaThanh()));
        tenSp.setText(sanPham.getTenSanPham());
        giaSp.setText(formattedNumber+" đ");
        cungCap.setText(sanPham.getNhaCungCap());
        thuongHieu.setText(sanPham.getThuongHieu());
        trongLuong.setText(sanPham.getTrongLuong());
        huongDan.setText(sanPham.getHuongDan());
        moTaSp.setText(sanPham.getMoTa());
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

    @Override
    protected void onStart() {
        super.onStart();
        demgiohangctsp();
    }
}

package com.example.giloli.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giloli.DAO.AddDiaChiNguoiDungDao;
import com.example.giloli.DAO.AddListDatHang;
import com.example.giloli.DAO.GioHangDAO;
import com.example.giloli.DAO.HoaDonCtDAO;
import com.example.giloli.DAO.HoaDonDAO;
import com.example.giloli.MainActivity;
import com.example.giloli.Model.DatHang;
import com.example.giloli.Model.DiaChiNguoiDung;
import com.example.giloli.Model.GioHang;
import com.example.giloli.Model.HoaDon;
import com.example.giloli.Model.HoaDonCT;
import com.example.giloli.R;
import com.example.giloli.fragment.TrangChuFragment;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DatHangActivity extends AppCompatActivity {
    RelativeLayout relativeDatHang, relativePt, relativePttt;
    private int resutl;
    private int position;
    AddDiaChiNguoiDungDao diaChiNguoiDungDao;
    List<DiaChiNguoiDung> list = new ArrayList<DiaChiNguoiDung>();
    FirebaseAuth mDatabase;
    private String email;
    TextView txtNguoiNhan, txtSdtNguoiNhan,txtDiaChiDatHang,txtQuanDatHang,txtPhuongDatHang;
    TextView txtPt, txtGia, txtSoNgay;
    TextView txtTamThoi, txtTongTienVanChuyen, txtTongCong;
    double tien1;
    Button btnDatHang;
    private HoaDon hoaDon;
    private TextView txtTienHangAo, txtTienvcAo, txtTongCongAo;
    private static final String alpha = "abcdefghijklmnopqrstuvwxyz"; // a-z
    private static final String alphaUpperCase = alpha.toUpperCase(); // A-Z
    private static final String digits = "0123456789"; // 0-9
    private static final String ALPHA_NUMERIC = alpha + alphaUpperCase + digits;
    private static Random generator = new Random();
    ImageView imgBackDatHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_hang);
        mDatabase = FirebaseAuth.getInstance();
        anhXa();
        setRelativeDatHang();
        diaChiNguoiDungDao = new AddDiaChiNguoiDungDao(this);
        setRelativePt();
        setRelativePttt();
        setback();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("hopTien");
        if (bundle!=null){
            tien1 = bundle.getDouble("giaTien");
        }
        setBtnDatHang();
        if (mDatabase.getCurrentUser()!=null){
            email = mDatabase.getCurrentUser().getEmail();
        } else {
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }
        list = diaChiNguoiDungDao.layDiaChiTheoEmail(email);
        if (list.size() > 0) {
            DiaChiNguoiDung diaChiNguoiDung = list.get(0);
            txtNguoiNhan.setText(diaChiNguoiDung.getHoten());
            txtSdtNguoiNhan.setText(diaChiNguoiDung.getSoDienThoai());
            txtDiaChiDatHang.setText(diaChiNguoiDung.getDiaChi());
            txtPhuongDatHang.setText(diaChiNguoiDung.getTenPhuong() + ", ");
            txtQuanDatHang.setText(diaChiNguoiDung.getTenQuan() + ", ");
        }
        GioHangDAO gioHangDAO = new GioHangDAO(DatHangActivity.this);
        List<GioHang> gioHangs = new ArrayList<GioHang>();
        gioHangs = gioHangDAO.layGioHangTheoEmailRCVGioHanggh(email);
        double  tongtien = 0;
        for (int i = 0; i < gioHangs.size(); i++){
            GioHang gioHang = gioHangs.get(i);
            double tienGioHang = Double.parseDouble(gioHang.getGiaTien());
            tongtien = tongtien + tienGioHang;
        }
        NumberFormat numberFormat = new DecimalFormat("#,###");
        txtTienHangAo.setText(String.valueOf(tongtien));
        String tienAo = numberFormat.format(tongtien);
        txtTamThoi.setText(tienAo);
        String tienVcAo = numberFormat.format(Double.parseDouble("30000"));
        txtTienvcAo.setText("30000");
        txtTongTienVanChuyen.setText(tienVcAo);
        double tientam = Double.parseDouble(txtTienHangAo.getText().toString());
        double tientongvanc = Double.parseDouble(txtTienvcAo.getText().toString());
        double tong = tientam + tientongvanc;
        txtTongCongAo.setText(String.valueOf(tong));
        String tongAo = numberFormat.format(tong);
        txtTongCong.setText(tongAo);
    }

    private void setback() {
        imgBackDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void anhXa(){
        relativeDatHang = findViewById(R.id.relativeDatHang);
        txtNguoiNhan = findViewById(R.id.txtNguoiNhan);
        txtDiaChiDatHang = findViewById(R.id.txtDiaChiDatHang);
        txtQuanDatHang = findViewById(R.id.txtQuanDatHang);
        txtPhuongDatHang = findViewById(R.id.txtPhuongDatHang);
        txtSdtNguoiNhan = findViewById(R.id.txtSdtNguoiNhan);
        relativePt = findViewById(R.id.relativePt);
        txtPt = findViewById(R.id.txtGiaoHangTietKiem);
        txtGia = findViewById(R.id.txtGiaTienPt);
        txtSoNgay = findViewById(R.id.txtSoNgayGiao);
        relativePttt = findViewById(R.id.RelativePttt);
        txtTamThoi = findViewById(R.id.txtTongTienHang);
        txtTongTienVanChuyen = findViewById(R.id.txtTongTienVanChuyen1);
        txtTongCong = findViewById(R.id.txtTongThanhToan);
        btnDatHang = findViewById(R.id.btnDatHang);
        txtTienHangAo = findViewById(R.id.txtTienHangAo);
        txtTienvcAo = findViewById(R.id.txtTienVanChuyenAo);
        txtTongCongAo = findViewById(R.id.txtTongCongAo);
        imgBackDatHang=findViewById(R.id.imgBackDatHang);
    }

    private void setRelativePttt(){
        relativePttt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DatHangActivity.this,"Xin lỗi bạn, hiện tại ứng dụng chỉ có phương thức thanh toán khi nhận hàng",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setRelativeDatHang(){
        relativeDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DatHangActivity.this, ThemSoDiaChiDone.class);
                startActivityForResult(intent,1);
            }
        });
    }
    private void setRelativePt(){
        relativePt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DatHangActivity.this,ListGiaoHang.class);
                startActivityForResult(intent,2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == Activity.RESULT_OK){
                resutl = data.getIntExtra("resutl",0);
                if (mDatabase.getCurrentUser()!=null){
                    email = mDatabase.getCurrentUser().getEmail();
                } else {
                    loadUserProfile(AccessToken.getCurrentAccessToken());
                }
                list = diaChiNguoiDungDao.layDiaChiTheoEmail(email);
                DiaChiNguoiDung diaChiNguoiDung = list.get(resutl);
                txtNguoiNhan.setText(diaChiNguoiDung.getHoten());
                txtSdtNguoiNhan.setText(diaChiNguoiDung.getSoDienThoai());
                txtDiaChiDatHang.setText(diaChiNguoiDung.getDiaChi());
                txtPhuongDatHang.setText(diaChiNguoiDung.getTenPhuong()+", ");
                txtQuanDatHang.setText(diaChiNguoiDung.getTenQuan()+", ");

            }
            if (resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(DatHangActivity.this,"Bạn không chọn địa chỉ",
                        Toast.LENGTH_SHORT).show();
            }

        }
        if (requestCode == 2){
            if (resultCode == Activity.RESULT_OK) {
                position = data.getIntExtra("position", 0);
                DatHang datHang = AddListDatHang.list.get(position);
                txtPt.setText(datHang.getTenKieu());
                NumberFormat format = new DecimalFormat("#,###");
                String tienVanChuyenAo = format.format(Double.parseDouble(datHang.getGia()));
                txtGia.setText(tienVanChuyenAo);
                txtTongTienVanChuyen.setText(tienVanChuyenAo);
                txtSoNgay.setText(datHang.getSoNgayGiao());
                txtTienvcAo.setText(datHang.getGia());
                double tientam = Double.parseDouble(txtTienHangAo.getText().toString());
                double tientongvanc = Double.parseDouble(txtTienvcAo.getText().toString());
                double tong = tientam + tientongvanc;
                txtTongCongAo.setText(String.valueOf(tong));
                String tongCongAo = format.format(tong);
                txtTongCong.setText(tongCongAo);
            }
            if (resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(DatHangActivity.this,"Bạn không chọn phương thức",
                        Toast.LENGTH_SHORT).show();
            }

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
    private void setBtnDatHang(){
        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDatabase.getCurrentUser()!= null){
                    email = mDatabase.getCurrentUser().getEmail();
                } else {
                    loadUserProfile(AccessToken.getCurrentAccessToken());
                }
                hoaDon = new HoaDon();
                int maHoaDon = 8;
                String maHoaDonChinh = randomAlphaNumeric(maHoaDon);
                HoaDonDAO hoaDonDAO = new HoaDonDAO(DatHangActivity.this);
                hoaDon.setMaHoaDon(maHoaDonChinh);
                hoaDon.setEmailNguoiDung(email);
                Date dateHoaDon = Calendar.getInstance().getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dateHoaDonF = simpleDateFormat.format(dateHoaDon);
                hoaDon.setDateHoaDon(dateHoaDonF);
                hoaDon.setTenNguoiNhan(txtNguoiNhan.getText().toString());
                hoaDon.setSdtNguoiNhan(txtSdtNguoiNhan.getText().toString());
                String diaChi = txtDiaChiDatHang.getText().toString()+", "+txtPhuongDatHang.getText().toString()+txtQuanDatHang.getText().toString()+"TP. Hồ Chí Minh";
                hoaDon.setDiaChiNguoiNhan(diaChi);
                hoaDon.setDonViVanChuyen(txtPt.getText().toString());
                hoaDon.setPhuongThuc("Thanh toán khi nhận hàng");
                hoaDon.setTrangThai("Chờ duyệt đơn");
                GioHangDAO gioHangDAO = new GioHangDAO(DatHangActivity.this);
                List<GioHang> gioHangs = new ArrayList<GioHang>();
                gioHangs = gioHangDAO.layGioHangTheoEmailRCVGioHanggh(email);
                double  tongtien = 0;
                for (int i = 0; i < gioHangs.size(); i++){
                    GioHang gioHang = gioHangs.get(i);
                    gioHangDAO.deleteGioHang(gioHang);
                    HoaDonCtDAO hoaDonCtDAO = new HoaDonCtDAO(DatHangActivity.this);
                    int maHoaDonCt = 8;
                    tongtien = Double.parseDouble(tongtien + gioHang.getGiaTien());
                    HoaDonCT hoaDonCT = new HoaDonCT();
                    hoaDonCT.setMaHoaDonCt(randomAlphaNumeric(maHoaDonCt));
                    hoaDonCT.setMaHoaDon(maHoaDonChinh);
                    hoaDonCT.setMaSP(gioHang.getMaSp());
                    double giaTien = Double.parseDouble(gioHang.getGiaTien());
                    double sl = Double.parseDouble(gioHang.getSoLuong());
                    double tienCt = giaTien/sl;
                    hoaDonCT.setGiaThanhSp(String.valueOf(tienCt));
                    hoaDonCT.setSoLuong(Integer.parseInt(gioHang.getSoLuong()));
                    hoaDonCtDAO.themHoaDonCt(hoaDonCT);
                }
                hoaDon.setTongCong(txtTongCongAo.getText().toString());
                hoaDonDAO.themHoaDon(hoaDon);
                startActivity(new Intent(DatHangActivity.this, MainActivity.class));
                finish();
            }
        });
    }
    public String randomAlphaNumeric(int numberOfCharactor) {
        StringBuilder randomMa = new StringBuilder();
        for (int i = 0; i < numberOfCharactor; i++) {
            int number = randomNumber(0, ALPHA_NUMERIC.length() - 1);
            char ch = ALPHA_NUMERIC.charAt(number);
            randomMa.append(ch); }
        return randomMa.toString();
    }

    public static int randomNumber(int min, int max) {
        return generator.nextInt((max - min) + 1) + min;
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

package com.example.giloliadmin.Edit_Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.giloliadmin.Adapter.sanphamAdapter;
import com.example.giloliadmin.Adapter.theloaiAdapter;
import com.example.giloliadmin.DAO.SanPhamDAO;
import com.example.giloliadmin.DAO.TheLoaiDAO;
import com.example.giloliadmin.Model.SanPham;
import com.example.giloliadmin.Model.TheLoai;
import com.example.giloliadmin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class suaSanPham_activity extends AppCompatActivity {
    ArrayList<String> listdanhsachsanpham = new ArrayList<>();
    String maLoaiSp;
    ImageView imvsuasanpham;
    EditText edtmasp, edttensp, edtsoluong, edtgia, edtmota, edttrongluong, edtnhacungcap, edtthuonghieu, edthuongdan;
    Spinner spnsuasanpham;
    Button btsuasanpham;
    TextView tvSuaComplete;
    ProgressBar progressanhsp;
    final int PICK_IMAGE_REQUEST = 1;
    ArrayList<Uri> ImageList = new ArrayList<>();
    Uri imageUri;
    private int upload_count = 0;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    public String image_url;
    TheLoaiDAO theLoaiDAO;
    SanPhamDAO sanPhamDAO;
    int countClipdata;
    int currentImageSelect;
    ArrayList<String> listtheloai;
    List<SanPham> listsanpham;
    String id;
    public static theloaiAdapter theloaiAdapter;
    public static ArrayAdapter adapter_sua;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_suasanpham);
        // Ánh xạ
        imvsuasanpham = findViewById(R.id.img_suaSanPham);
        edtmasp = findViewById(R.id.tv_suaMaSanPham); edtmasp.setEnabled(false);
        edttensp = findViewById(R.id.edt_suaTenSanPham);
        edtsoluong = findViewById(R.id.edt_suaSLSanPham);
        edtgia = findViewById(R.id.edt_suaGiaSanPham);
        edtmota = findViewById(R.id.edt_suaMoTaSanPham);
        edttrongluong = findViewById(R.id.edt_suaKLSanPham);
        edtnhacungcap = findViewById(R.id.edt_suaCCSanPham);
        edtthuonghieu = findViewById(R.id.edt_suaTHSanPham);
        edthuongdan = findViewById(R.id.edt_suaHDSanPham);
        btsuasanpham = findViewById(R.id.bt_suasanpham);
        progressanhsp = findViewById(R.id.progress_anhsp);
        spnsuasanpham = findViewById(R.id.spn_suaMaLoai);
        tvSuaComplete = findViewById( R.id.tvSuaComplete );
        storageReference = FirebaseStorage.getInstance().getReference("TheLoai");
        databaseReference = FirebaseDatabase.getInstance().getReference("TheLoai");
        // lấy list thể loại
        listtheloai = new ArrayList<>();
        theLoaiDAO = new TheLoaiDAO(this);
        listtheloai = theLoaiDAO.layMaLoai1();
        adapter_sua = new ArrayAdapter(suaSanPham_activity.this , android.R.layout.simple_list_item_1, listtheloai);
        spnsuasanpham.setAdapter(adapter_sua);
        spnsuasanpham.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = spnsuasanpham.getSelectedItem().toString();
                String[] kiTu = ((String) item).split(" -");
                maLoaiSp = kiTu[0];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        // Lấy list sản phẩm
        listsanpham = new ArrayList<>();
        sanPhamDAO = new SanPhamDAO(this);
        listsanpham = sanPhamDAO.layhetSanPham("");

        // =========================================================
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("SuaSanPham");
        if (bundle!=null){
            String url = bundle.getString("url_imv","");
            String masp = bundle.getString("edtmasanpham", "");
            String tensp = bundle.getString("edttensanpham", "");
            int soluongsp = bundle.getInt("edtsoluong", 1);
            String giasp = bundle.getString("edtgia", "");
            String motasp = bundle.getString("edtmota", "");
            String trongluongsp = bundle.getString("edttrongluong","");
            String nhacungcapsp = bundle.getString("edtnhacungcap","");
            String thuonghieusp = bundle.getString("edtthuonghieu","");
            String huongdansp = bundle.getString("edthuongdan","");
            // SetText lên Edittext
            edtmasp.setText(masp);
            edttensp.setText(tensp);
            edtmota.setText(motasp);
            edtsoluong.setText(String.valueOf(soluongsp));
            edtgia.setText(giasp);
            edttrongluong.setText(trongluongsp);
            edtnhacungcap.setText(nhacungcapsp);
            edtthuonghieu.setText(thuonghieusp);
            edthuongdan.setText(huongdansp);
            Glide.with(getApplication()).load(url)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressanhsp.setVisibility(View.GONE);
                            return false;
                        }
                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressanhsp.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(imvsuasanpham);
        }
        // Chọn ảnh mới
        imvsuasanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/+");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                startActivityForResult(intent,PICK_IMAGE_REQUEST);
            }
        });
        //===========================================================================================
        // Xử lí khi click nút chỉnh chỉnh sửa
        btsuasanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate() == false) {
                    Toast.makeText(suaSanPham_activity.this, "Chỉnh sửa không thành công", Toast.LENGTH_SHORT).show();
                } else {
                    if (imageUri != null) {
                        for (upload_count = 0; upload_count < ImageList.size(); upload_count++) {
                            final Uri IndividualImage = ImageList.get( upload_count );
                            final StorageReference ImageName = storageReference.child( "Image" + IndividualImage.getLastPathSegment() );
                            ImageName.putFile( IndividualImage ).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    ImageName.getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String url = String.valueOf( uri );
                                            listdanhsachsanpham.add(url);
                                            if (listdanhsachsanpham.size() == ImageList.size()) {
                                                SanPham sanPham = new SanPham();
                                                String suamasp = edtmasp.getText().toString();
                                                String suatensp = edttensp.getText().toString();
                                                int suasoluong = Integer.parseInt( edtsoluong.getText().toString() );
                                                String suagia = edtgia.getText().toString();
                                                String suamota = edtmota.getText().toString();
                                                String suatrongluong = edttrongluong.getText().toString();
                                                String suanhacungcap = edtnhacungcap.getText().toString();
                                                String suathuonghieu = edtthuonghieu.getText().toString();
                                                String suahuongdan = edthuongdan.getText().toString();
                                                sanPham.setLinkUrl(listdanhsachsanpham);
                                                sanPham.setMaLoai(maLoaiSp);
                                                sanPham.setMaSanPham( suamasp );
                                                sanPham.setTenSanPham( suatensp );
                                                sanPham.setSoLuong( suasoluong );
                                                sanPham.setGiaThanh( suagia );
                                                sanPham.setMoTa( suamota );
                                                sanPham.setTrongLuong( suatrongluong );
                                                sanPham.setNhaCungCap( suanhacungcap );
                                                sanPham.setThuongHieu( suathuonghieu );
                                                sanPham.setHuongDan( suahuongdan );
                                                sanPhamDAO.capNhatSanPham( sanPham );
                                                finish();
                                            }
                                        }
                                    } );
                                }
                            } );
                        }} }}});}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST){
            if (resultCode == RESULT_OK){
                if (data.getClipData() != null){
                    countClipdata = data.getClipData().getItemCount();
                    currentImageSelect = 0;
                    while (currentImageSelect < countClipdata){
                        imageUri = data.getClipData().getItemAt(currentImageSelect).getUri();
                        ImageList.add(imageUri);
                        currentImageSelect++;
                    }
                    tvSuaComplete.setVisibility(View.VISIBLE);
                    tvSuaComplete.setText("Bạn đã chọn " + ImageList.size() + " hình");
                    imvsuasanpham.setVisibility(View.GONE);
                } else {
                    Toast.makeText(suaSanPham_activity.this,"Vui lòng chọn nhiều hình",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    public static void capNhatAdapterSpinnerSua(){
        adapter_sua.notifyDataSetChanged(); }
    private boolean validate(){
        String mSp = edtmasp.getText().toString().trim();
        String tSp = edttensp.getText().toString().trim();
        String sl = edtsoluong.getText().toString().trim();
        String gia = edtgia.getText().toString().trim();
        String mota = edtmota.getText().toString().trim();
        String th = edtthuonghieu.getText().toString().trim();
        String ncc = edtnhacungcap.getText().toString().trim();
        String tl = edttrongluong.getText().toString().trim();
        String hd = edthuongdan.getText().toString().trim();
        String number = "^[0-9]*$";
        if (mSp.isEmpty()){
            edtmasp.setError("Mã sản phẩm không được trống");
            return false;
        }
        if (tSp.isEmpty()){
            edttensp.setError("Tên sản phẩm không được trống");
            return false;
        }
        if (sl.isEmpty()){
            edtsoluong.setError("Số lượng sản phẩm không được trống");
            return false;
        }
        if (gia.isEmpty()){
            edtgia.setError("Giá sản phẩm không được trống");
            return false;
        }
        if (th.isEmpty()){
            edtthuonghieu.setError("Thương hiệu không được trống");
            return false;
        }
        if (mota.isEmpty()){
            edtmota.setError("Mô tả sản phẩm không được trống");
            return false;
        }
        if (ncc.isEmpty()){
            edtnhacungcap.setError("Nhà cung cấp sản phẩm không được trống");
            return false;
        }
        if (tl.isEmpty()){
            edttrongluong.setError("Trọng lượng sản phẩm không được trống");
            return false;
        }
        if (hd.isEmpty()){
            edthuongdan.setError("Hướng dẫn sản phẩm không được trống");
            return false;
        }
        return true;
    }
    public static void capnhatAdapterTheLoai(){
        theloaiAdapter.notifyDataSetChanged();
    }

}

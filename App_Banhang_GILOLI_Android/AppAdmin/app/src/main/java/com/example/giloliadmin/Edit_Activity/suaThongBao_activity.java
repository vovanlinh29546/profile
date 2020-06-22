package com.example.giloliadmin.Edit_Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.giloliadmin.Adapter.thongbaoAdapter;
import com.example.giloliadmin.DAO.ThongBaoDAO;
import com.example.giloliadmin.Model.ThongBao;
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

public class suaThongBao_activity extends AppCompatActivity {
    ArrayList<ThongBao> danhsachthongbao;
    ImageView imvsuathongbao;
    EditText edtmathongbao, edttieude, edtnoidung;
    Button btsuathongbao;
    ProgressBar progressanhthongbao;
    final int PICK_IMAGE_REQUEST = 1;
    Uri imageUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    public String image_url;
    ThongBaoDAO thongBaoDAO;
    List<ThongBao> list;
    String id;
    public static thongbaoAdapter ThongbaoAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_suathongbao);
        // Ánh xạ
        imvsuathongbao = findViewById(R.id.img_SuaThongBao);
        edtmathongbao = findViewById( R.id.tv_SuaMaThongBao );
        edttieude = findViewById(R.id.edt_SuaTieuDeThongBao);
        edtnoidung = findViewById(R.id.edt_SuaNoiDungThongBao);
        btsuathongbao = findViewById(R.id.bt_suathongbao);
        progressanhthongbao = findViewById(R.id.progress_suaanhthongbao);
        storageReference = FirebaseStorage.getInstance().getReference("ThongBao");
        databaseReference = FirebaseDatabase.getInstance().getReference("ThongBao");
        list = new ArrayList<>();
        thongBaoDAO = new ThongBaoDAO(this);
        list = thongBaoDAO.layHetThongBao();
        ThongbaoAdapter = new thongbaoAdapter((ArrayList<ThongBao>) list, this);
        // =========================================================
        edtmathongbao.setEnabled(false);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("Suathongbao");
        if (bundle!=null){
            String ma;
            String url;
            String tieude;
            String noidung;
            ma = bundle.getString( "edtma", "" );
            url = bundle.getString("imv_anhthongbao","");
            tieude = bundle.getString("edttieude", "");
            noidung = bundle.getString("edtnoidung", "");
            edtmathongbao.setText(ma);
            edttieude.setText(tieude);
            edtnoidung.setText(noidung);
            Glide.with(getApplication()).load(url)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressanhthongbao.setVisibility(View.GONE);
                            return false;
                        }
                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressanhthongbao.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(imvsuathongbao);
        }
        // Chọn ảnh mới
        imvsuathongbao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/+");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Chọn hình ảnh"), PICK_IMAGE_REQUEST);
            }
        });
        //===========================================================================================
        // Xử lí khi click nút chỉnh chỉnh sửa
        btsuathongbao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate() == false) {
                    Toast.makeText( suaThongBao_activity.this, "Chỉnh sửa không thành công", Toast.LENGTH_SHORT).show();
                } else {
                    if (imageUri != null) {
                        StorageReference pickRef = storageReference.child(imageUri.getLastPathSegment());
                        pickRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Lấy URL của ảnh trên FireBase Storage
                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!uriTask.isSuccessful()) ;
                                Uri downloadUrl = uriTask.getResult();
                                image_url = downloadUrl.toString();
                                // Thêm đường dẫn vào database
                                ThongBao thongBao = new ThongBao();
                                String suamathongbao = edtmathongbao.getText().toString();
                                String suatieude = edttieude.getText().toString();
                                String suanoidung = edtnoidung.getText().toString();
                                id = databaseReference.push().getKey();
                                databaseReference.child(id).setValue(thongBao);
                                thongBao.setMathongbao(suamathongbao);
                                thongBao.setTieude(suatieude);
                                thongBao.setNoidung(suanoidung);
                                thongBao.setUrlanhthongbao(image_url);
                                thongBaoDAO.updateThongBao(thongBao);
                                Toast.makeText( suaThongBao_activity.this, "Chỉnh sửa thành công", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
                    }}
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK){
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
                imvsuathongbao.setImageBitmap(bitmap);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    private boolean validate(){
        String tieude = edttieude.getText().toString().trim();
        String noidung = edtnoidung.getText().toString().trim();
        if (tieude.length() == 0){
            edttieude.setError("Tiêu đề không được trống");
            return false;
        } if (noidung.length() == 0){
            edtnoidung.setError("Nội dung không được trống");
            return false;
        }
        return true;
    }
}

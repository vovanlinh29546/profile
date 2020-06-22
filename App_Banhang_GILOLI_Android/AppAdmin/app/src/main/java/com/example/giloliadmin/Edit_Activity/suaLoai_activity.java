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
import com.example.giloliadmin.Adapter.theloaiAdapter;
import com.example.giloliadmin.DAO.TheLoaiDAO;
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
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class suaLoai_activity  extends AppCompatActivity {
    ArrayList<TheLoai> danhsachtheloai;
    ImageView imvsuatheloai;
    EditText edtmaloai, edttenloai;
    Button btsualoai;
    ProgressBar progressanh;
    final int PICK_IMAGE_REQUEST = 1;
    Uri imageUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    public String image_url;
    TheLoaiDAO theLoaiDAO;
    List<TheLoai> list;
    String id;
    public static theloaiAdapter theloaiAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sualoai);
        // Ánh xạ
        imvsuatheloai = findViewById(R.id.img_SuaTheLoai);
        edtmaloai = findViewById(R.id.tv_SuaMaTheLoai);
        edttenloai = findViewById(R.id.edt_SuaTenTheLoai);
        btsualoai = findViewById(R.id.bt_sualoai);
        progressanh = findViewById(R.id.progress_anh);
        storageReference = FirebaseStorage.getInstance().getReference("TheLoai");
        databaseReference = FirebaseDatabase.getInstance().getReference("TheLoai");
        list = new ArrayList<>();
        theLoaiDAO = new TheLoaiDAO(this);
        list = theLoaiDAO.layHetTheLoai();
        theloaiAdapter = new theloaiAdapter((ArrayList<TheLoai>) list, this);
        // =========================================================
        edtmaloai.setEnabled(false);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("SuaLoai");
        if (bundle!=null){
            String url;
            String maLoai;
            String tenLoai;
            url = bundle.getString("url_imv","");
            maLoai = bundle.getString("edtma", "");
            tenLoai = bundle.getString("edtten", "hh");
            edtmaloai.setText(maLoai);
            edttenloai.setText(tenLoai);
            Glide.with(getApplication()).load(url)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressanh.setVisibility(View.GONE);
                            return false;
                        }
                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressanh.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(imvsuatheloai);
        }
        // Chọn ảnh mới
        imvsuatheloai.setOnClickListener(new View.OnClickListener() {
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
        btsualoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate() == false) {
                    Toast.makeText(suaLoai_activity.this, "Chỉnh sửa không thành công", Toast.LENGTH_SHORT).show();
                }else if(imageUri == null){
                    Toast.makeText(suaLoai_activity.this, "Chưa chọn ảnh!", Toast.LENGTH_SHORT).show();
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
                                TheLoai theLoai = new TheLoai();
                                String suamaloai = edtmaloai.getText().toString();
                                String suatenloai = edttenloai.getText().toString();
                                theLoai.setMaLoai(suamaloai);
                                theLoai.setTenLoai(suatenloai);
                                theLoai.setuRLAnh(image_url);
                                theLoaiDAO.updateTheLoai(theLoai);
                                Toast.makeText(suaLoai_activity.this, "Chỉnh sửa thành công", Toast.LENGTH_SHORT).show();
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
                imvsuatheloai.setImageBitmap(bitmap);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    public static void capNhatAdapter(){
        theloaiAdapter.notifyDataSetChanged();
    }
    private boolean validate(){
        String maLoai = edtmaloai.getText().toString().trim();
        String tenLoai = edttenloai.getText().toString().trim();
        if (maLoai.length() == 0){
            edtmaloai.setError("Mã loại không được trống");
            return false;
        } if (tenLoai.length() == 0){
            edttenloai.setError("Tên loại không được trống");
            return false;
        }
        return true;
    }
}

package com.example.giloliadmin.fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.giloliadmin.DAO.TheLoaiDAO;
import com.example.giloliadmin.Model.TheLoai;
import com.example.giloliadmin.Adapter.theloaiAdapter;
import com.example.giloliadmin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.app.Activity.RESULT_OK;
public class PhanLoaiFragment extends Fragment {
    FloatingActionButton floatingActionButton;
    ImageView imgTheLoai;
    EditText edtMaLoai, edtTenLoai;
    final int PICK_IMAGE_REQUEST = 1;
    Uri imageUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    public String image_url;
    TheLoaiDAO theLoaiDAO;
    List<TheLoai> list;
    TheLoai theLoai;
    public static theloaiAdapter theloaiAdapter;
    private static final String alpha = "abcdefghijklmnopqrstuvwxyz"; // a-z
    private static final String alphaUpperCase = alpha.toUpperCase(); // A-Z
    private static final String digits = "0123456789"; // 0-9
    private static final String ALPHA_NUMERIC = alpha + alphaUpperCase + digits;
    private static Random generator = new Random();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_phanloai, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Phân loại");
        theLoaiDAO = new TheLoaiDAO(getContext());
        storageReference = FirebaseStorage.getInstance().getReference("TheLoai");
        databaseReference = FirebaseDatabase.getInstance().getReference("TheLoai");
        floatingActionButton = view.findViewById(R.id.fb_phanloai);
        list = new ArrayList<>();
        theLoaiDAO = new TheLoaiDAO(getActivity());
        list = theLoaiDAO.layHetTheLoai();
        theLoai = new TheLoai();
        theloaiAdapter = new theloaiAdapter((ArrayList<TheLoai>) list, getContext());
        // Thêm recyclerView vào
        final RecyclerView recyclerView = view.findViewById(R.id.rv_phanloai);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //Tạo đường kẻ, phân biệt giữa các phần tử
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(theloaiAdapter);
        setFloatingActionButton();

    }
    private void setFloatingActionButton(){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = PhanLoaiFragment.this.getLayoutInflater();
                view = inflater.inflate(R.layout.dialogthemloai, null);
                builder.setView(view);
                builder.setPositiveButton("Thêm", null);
                builder.setNegativeButton("Hủy", null);
                edtMaLoai = view.findViewById(R.id.edtMaTheLoai);
                edtTenLoai = view.findViewById(R.id.edtTenTheLoai);
                imgTheLoai = view.findViewById(R.id.imgTheLoai);
                int numberOfcharacter = 8; // Tạo mã tự động 12 kí tự
                edtMaLoai.setText(randomAlphaNumeric(numberOfcharacter));
                edtMaLoai.setEnabled( false );
                imageUri = null;
                imgTheLoai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setType("image/+");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Chọn hình ảnh"), PICK_IMAGE_REQUEST);
                    }
                });

                // Dialog Thêm ----------------------------------
                final AlertDialog mAlertDialog = builder.create();
                mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        // Button hủy trong Dialog ------------------
                        Button negativeBT = mAlertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                        negativeBT.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mAlertDialog.dismiss();
                            }
                        });

                        // Button thêm trong Dialog ----------------------
                        Button positiveBT = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveBT.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (validate() == false) {
                                    Toast.makeText(getContext(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
                                }else if(imageUri == null){
                                    Toast.makeText(getContext(), "Chưa chọn ảnh!", Toast.LENGTH_SHORT).show();
                                } else {
                                    for (int j = 0; j <= list.size() - 1; j++) {
                                        if (list.size() > 0) {
                                            if (edtMaLoai.getText().toString().equals(list.get(j).getMaLoai())) {
                                                Toast.makeText(getContext(), "Mã trùng, thêm thất bại", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                        }
                                    }
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
                                                theLoai.setuRLAnh(image_url);
                                                theLoai.setMaLoai(edtMaLoai.getText().toString());
                                                theLoai.setTenLoai(edtTenLoai.getText().toString());
                                                theLoaiDAO.themTheLoai(theLoai );
                                                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                                mAlertDialog.dismiss();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) { }
                                        });
                                    } }
                            }
                    });
                    }
                });
                mAlertDialog.show();
            }});}
            // =====================================================================================

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK){
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),imageUri);
                imgTheLoai.setImageBitmap(bitmap);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private boolean validate(){
        String maLoai = edtMaLoai.getText().toString().trim();
        String tenLoai = edtTenLoai.getText().toString().trim();
        if (maLoai.length() == 0){
            edtMaLoai.setError("Mã loại không được trống");
            return false;
        }if (tenLoai.length() == 0){
            edtTenLoai.setError("Tên loại không được trống");
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    public static void capNhatAdapterTheLoai(){
        theloaiAdapter.notifyDataSetChanged();
    }

    // Liên quan đến random MÃ
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
}

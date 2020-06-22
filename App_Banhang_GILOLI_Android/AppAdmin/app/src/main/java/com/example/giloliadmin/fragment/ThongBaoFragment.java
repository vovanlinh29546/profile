package com.example.giloliadmin.fragment;

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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giloliadmin.Adapter.theloaiAdapter;
import com.example.giloliadmin.Adapter.thongbaoAdapter;
import com.example.giloliadmin.DAO.ThongBaoDAO;
import com.example.giloliadmin.Model.ThongBao;
import com.example.giloliadmin.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.app.Activity.RESULT_OK;

public class ThongBaoFragment extends Fragment {
    FloatingActionButton floatingActionButton;
    ImageView imgthongbao;
    EditText edttieude, edtnoidung, edtmathongbao;
    final int PICK_IMAGE_REQUEST = 1;
    Uri imageUri;
    ThongBaoDAO thongBaoDAO;
    RecyclerView rv_thongbao;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    public String image_url;
    List<ThongBao> list;
    String id;
    ThongBao thongBao;
    public static thongbaoAdapter ThongbaoAdapter;
    // Dùng Random
    private static final String alpha = "abcdefghijklmnopqrstuvwxyz"; // a-z
    private static final String alphaUpperCase = alpha.toUpperCase(); // A-Z
    private static final String digits = "0123456789"; // 0-9
    private static final String ALPHA_NUMERIC = alpha + alphaUpperCase + digits;
    private static Random generator = new Random();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thongbao, container, false);
        rv_thongbao= view.findViewById(R.id.rv_thongbao);
        rv_thongbao.setHasFixedSize(true);
        //Tạo đường kẻ, phân biệt giữa các phần tử
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        rv_thongbao.addItemDecoration(dividerItemDecoration);
        rv_thongbao.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Thông Báo");
        thongBaoDAO = new ThongBaoDAO( getContext() );
        storageReference = FirebaseStorage.getInstance().getReference("ThongBao");
        databaseReference = FirebaseDatabase.getInstance().getReference("ThongBao");
        floatingActionButton = view.findViewById(R.id.fb_thongbao);
        list = new ArrayList<>();
        thongBaoDAO = new ThongBaoDAO(getActivity());
        list = thongBaoDAO.layHetThongBao();
        thongBao = new ThongBao();
        ThongbaoAdapter = new thongbaoAdapter( (ArrayList<ThongBao>) list, getContext() );
        // Thêm recyclerView vào
        final RecyclerView recyclerView = view.findViewById(R.id.rv_thongbao);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //Tạo đường kẻ, phân biệt giữa các phần tử
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(ThongbaoAdapter);
        setFloatingActionButton();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK){
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),imageUri);
                imgthongbao.setImageBitmap(bitmap);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void setFloatingActionButton(){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = ThongBaoFragment.this.getLayoutInflater();
                view = inflater.inflate(R.layout.dialogthemthongbao, null);
                builder.setView(view);
                builder.setPositiveButton("Thêm", null);
                builder.setNegativeButton("Hủy", null);
                edttieude = view.findViewById(R.id.edtTieuDe);
                edtmathongbao = view.findViewById( R.id.edt_mathongbao );
                edtnoidung = view.findViewById(R.id.edtNoiDung);
                imgthongbao = view.findViewById(R.id.imgThongBao);
                int numberOfcharacter = 8; // Tạo mã tự động 12 kí tự
                edtmathongbao.setText(randomAlphaNumeric(numberOfcharacter));
                imageUri = null;
                edtmathongbao.setEnabled( false );
                imgthongbao.setOnClickListener(new View.OnClickListener() {
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
                                            if (edtmathongbao.getText().toString().equals(list.get(j).getMathongbao())) {
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
                                                ThongBao thongBao = new ThongBao();
                                                thongBao.setUrlanhthongbao(image_url);
                                                thongBao.setMathongbao(edtmathongbao.getText().toString());
                                                thongBao.setTieude(edttieude.getText().toString());
                                                thongBao.setNoidung(edtnoidung.getText().toString());
                                                id = databaseReference.push().getKey();
                                                databaseReference.child(id).setValue(thongBao);
                                                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) { }
                                        });
                                    mAlertDialog.dismiss();
                                }
                            }}
                        });
                    }
                });
                mAlertDialog.show();
            }});}


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
    public static void capNhatAdapterThongBao(){
        ThongbaoAdapter.notifyDataSetChanged();
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

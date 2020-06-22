package com.example.giloliadmin.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giloliadmin.Adapter.sanphamAdapter;
import com.example.giloliadmin.DAO.SanPhamDAO;
import com.example.giloliadmin.DAO.TheLoaiDAO;
import com.example.giloliadmin.Model.SanPham;
import com.example.giloliadmin.Model.TheLoai;
import com.example.giloliadmin.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.app.Activity.RESULT_OK;

public class SanPhamFragment extends Fragment {
    EditText maSp, tenSp, soLuongSp, giaSp,moTaSp, cungCapSp, thuongHieuSp, huongDanSp, trongLuongSp;
    String maLoaiSp="";
    Spinner spinner, spn_sanphamtheoma;
    ImageView imgSanPham;
    FloatingActionButton floatingActionButton;
    final int PICK_IMAGE_REQUESTT = 1;
    public String image_urlSP;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    ArrayList<String> list;
    List<String> list1 = new ArrayList<>(); // List hinh anh
    ArrayList<SanPham> listSp;
    ArrayList<SanPham> listSptheoma;
    SanPhamDAO sanPhamDAO;
    TheLoaiDAO theLoaiDAO;
    ArrayList<Uri> ImageList = new ArrayList<>();
    private Uri ImageUri;
    public static sanphamAdapter sanphamAdapter;
    public static sanphamAdapter sanphamAdapter1;
    public static ArrayAdapter adapter;
    private int upload_count = 0;
    private ProgressDialog process;
    int countClipdata;
    int currentImageSelect;
    TextView textView;
    private static final String alpha = "abcdefghijklmnopqrstuvwxyz"; // a-z
    private static final String alphaUpperCase = alpha.toUpperCase(); // A-Z
    private static final String digits = "0123456789"; // 0-9
    private static final String ALPHA_NUMERIC = alpha + alphaUpperCase + digits;
    private static Random generator = new Random();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sanpham, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        floatingActionButton = view.findViewById(R.id.fb_sanpham);
        setFloatingActionButton();
        getActivity().setTitle("Sản phẩm");
        storageReference = FirebaseStorage.getInstance().getReference("SanPham");
        databaseReference = FirebaseDatabase.getInstance().getReference("SanPham");
        listSp = new ArrayList<>();
        sanPhamDAO = new SanPhamDAO(getActivity());
        listSp = (ArrayList<SanPham>) sanPhamDAO.layhetSanPham("");
        sanphamAdapter = new sanphamAdapter((ArrayList<SanPham>) listSp, getContext());

        // Thêm recyclerView vào
        final RecyclerView recyclerView = view.findViewById(R.id.rv_sanpham);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //Tạo đường kẻ, phân biệt giữa các phần tử
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(sanphamAdapter);

        //======================== Spinner hien san pham  theo ma the loai
        list = new ArrayList<String>();
        listSptheoma = new ArrayList<>();
        theLoaiDAO = new TheLoaiDAO(getContext());
        list = theLoaiDAO.layMaLoai();
        adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, list);
        spn_sanphamtheoma = view.findViewById( R.id.spn_sanphamtheoma );
        spn_sanphamtheoma.setAdapter(adapter);


        spn_sanphamtheoma.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = spn_sanphamtheoma.getSelectedItem().toString();
                String [] kiTu = ((String) item).split(" -");
                maLoaiSp = kiTu[0];
                //std.setMalop(getitem1);
                listSptheoma = (ArrayList<SanPham>) sanPhamDAO.laysanphamtheoma(maLoaiSp);
                sanphamAdapter1 = new sanphamAdapter((ArrayList<SanPham>) listSptheoma, getContext());
                recyclerView.setAdapter(sanphamAdapter1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        } );

    }
    private void setFloatingActionButton(){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = SanPhamFragment.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialogthemsanpham, null);
                builder.setView(dialogView);
                builder.setPositiveButton("Thêm", null);
                builder.setNegativeButton("Hủy", null);

                maSp = dialogView.findViewById(R.id.edtMaSanPham);
                int numberOfcharacter = 8; // Tạo mã tự động 12 kí tự
                maSp.setText(randomAlphaNumeric(numberOfcharacter));
                maSp.setEnabled( false );
                tenSp = dialogView.findViewById(R.id.edtTenSanPham);
                soLuongSp = dialogView.findViewById(R.id.edtSLSanPham);
                giaSp = dialogView.findViewById(R.id.edtGiaSanPham);
                moTaSp = dialogView.findViewById(R.id.edtMoTaSanPham);
                trongLuongSp = dialogView.findViewById(R.id.edtKLSanPham);
                cungCapSp = dialogView.findViewById(R.id.edtCCSanPham);
                thuongHieuSp = dialogView.findViewById(R.id.edtTHSanPham);
                huongDanSp = dialogView.findViewById(R.id.edtHDSanPham);
                spinner = dialogView.findViewById(R.id.spnMaLoai);
                imgSanPham = dialogView.findViewById(R.id.imgSanPham);
                textView =  dialogView.findViewById(R.id.tvComplete);
                process = new ProgressDialog(getContext());
                process.setMessage("Hình ảnh đang được upload......");


                // Liên quan đến SPinner
                list = new ArrayList<String>();
                theLoaiDAO = new TheLoaiDAO(getContext());
                list = theLoaiDAO.layMaLoai();
                adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, list);
                spinner.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Object item = spinner.getSelectedItem().toString();
                        String [] kiTu = ((String) item).split(" -");
                        maLoaiSp = kiTu[0];
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
                // =============================================================================
                imgSanPham.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/+");
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                        startActivityForResult(intent,PICK_IMAGE_REQUESTT);
                    }
                });

                // Dialog Thêm ==========================================================================
                final AlertDialog mAlertDialog = builder.create();
                mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        // Button huy
                        Button negativeBT = mAlertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                        negativeBT.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mAlertDialog.dismiss();
                                ImageList.clear();
                                list1.clear();
                            }
                        });

                        // Button thêm trong Dialog
                        Button positiveBT = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveBT.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (validate() == false) {
                                    Toast.makeText(getContext(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    for (int j = 0; j <= listSp.size() - 1; j++) {
                                        if (listSp.size() > 0) {
                                            if (maSp.getText().toString().equals(listSp.get(j).getMaSanPham())) {
                                                Toast.makeText(getContext(), "Mã loại trùng, thêm thất bại", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                        }
                                    }
                                    if (ImageUri != null) {
                                        storageReference = FirebaseStorage.getInstance().getReference().child("SanPham");
                                        for (upload_count = 0; upload_count < ImageList.size(); upload_count++){
                                            final Uri IndividualImage = ImageList.get(upload_count);
                                            final StorageReference ImageName = storageReference.child("Image" + IndividualImage.getLastPathSegment());
                                            ImageName.putFile(IndividualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            String url = String.valueOf(uri);
                                                            list1.add(url);
                                                            if (list1.size()==ImageList.size()){
                                                                SanPham sanPham = new SanPham();
                                                                sanPham.setLinkUrl(list1);
                                                                sanPham.setMaLoai(maLoaiSp);
                                                                sanPham.setMaSanPham(maSp.getText().toString());
                                                                sanPham.setTenSanPham(tenSp.getText().toString());
                                                                sanPham.setGiaThanh(giaSp.getText().toString());
                                                                sanPham.setSoLuong(Integer.parseInt(soLuongSp.getText().toString()));
                                                                sanPham.setMoTa(moTaSp.getText().toString());
                                                                sanPham.setTrongLuong(trongLuongSp.getText().toString());
                                                                sanPham.setThuongHieu(thuongHieuSp.getText().toString());
                                                                sanPham.setNhaCungCap(cungCapSp.getText().toString());
                                                                sanPham.setHuongDan(huongDanSp.getText().toString());
                                                                sanPhamDAO.themSanPham(sanPham);
                                                                ImageList.clear();
                                                                list1.clear();
                                                            }

                                                        }
                                                    });
                                                }
                                            });
                                        }

                                    }
                                    // Đóng Dialog khi thêm thành công
                                    mAlertDialog.dismiss();
                                }
                            }
                        });

                    }
                });
                mAlertDialog.show();
            }});}
// ===============================================================================================

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUESTT){
            if (resultCode == RESULT_OK){
                if (data.getClipData() != null){
                    countClipdata = data.getClipData().getItemCount();
                    currentImageSelect = 0;
                    while (currentImageSelect < countClipdata){
                        ImageUri = data.getClipData().getItemAt(currentImageSelect).getUri();
                        ImageList.add(ImageUri);
                        currentImageSelect++;
                    }
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("Bạn đã chọn " + ImageList.size() + " hình");
                    imgSanPham.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getContext(),"Vui lòng chọn nhiều hình",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private boolean validate(){
        String mSp = maSp.getText().toString().trim();
        String tSp = tenSp.getText().toString().trim();
        String sl = soLuongSp.getText().toString().trim();
        String gia = giaSp.getText().toString().trim();
        String mota = moTaSp.getText().toString().trim();
        String th = thuongHieuSp.getText().toString().trim();
        String ncc = cungCapSp.getText().toString().trim();
        String tl = trongLuongSp.getText().toString().trim();
        String hd = huongDanSp.getText().toString().trim();
        if (mSp.isEmpty()){
            maSp.setError("Mã sản phẩm không được trống");
            return false;
        }
        if (tSp.isEmpty()){
            tenSp.setError("Tên sản phẩm không được trống");
            return false;
        }
        if (sl.isEmpty()){
            soLuongSp.setError("Số lượng sản phẩm không được trống");
            return false;
        }
        if (gia.isEmpty()){
            giaSp.setError("Giá sản phẩm không được trống");
            return false;
        }
        if (th.isEmpty()){
            thuongHieuSp.setError("Thương hiệu không được trống");
            return false;
        }
        if (mota.isEmpty()){
            moTaSp.setError("Mô tả sản phẩm không được trống");
            return false;
        }
        if (ncc.isEmpty()){
            cungCapSp.setError("Nhà cung cấp sản phẩm không được trống");
            return false;
        }
        if (tl.isEmpty()){
            trongLuongSp.setError("Trọng lượng sản phẩm không được trống");
            return false;
        }
        if (hd.isEmpty()){
            huongDanSp.setError("Hướng dẫn sản phẩm không được trống");
            return false;
        }
        return true;
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    public static void capNhatAdapter(){
        sanphamAdapter.notifyDataSetChanged();
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


package com.example.giloli.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giloli.Activity.GoogleMapsActivity;
import com.example.giloli.Activity.GioHangActivity;
import com.example.giloli.Activity.QuanLiDonHangActivity;
import com.example.giloli.Adapter.Adapter_GridviewSanPham;
import com.example.giloli.Adapter.RecyclerAdapter;
import com.example.giloli.DAO.GioHangDAO;
import com.example.giloli.DAO.SanPhamDAO;
import com.example.giloli.DAO.TheLoaiDAO;
import com.example.giloli.ExpandalableHeightGridView;
import com.example.giloli.MainActivity;
import com.example.giloli.ManHinhCho_Activity;
import com.example.giloli.Model.GioHang;
import com.example.giloli.Model.SanPham;
import com.example.giloli.Model.TheLoai;
import com.example.giloli.R;
import com.example.giloli.Service.CountItemShop;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class TrangChuFragment extends Fragment {
    private int hot_number = 0;
    private TextView ui_hot = null;
    ViewFlipper viewFlipper;
    TextView txtxemthem;
    ImageView imageView, imageView1, imageView2;
    GridView gridView;
    Toolbar toolbar;
    RecyclerView recyclerView;
    public static RecyclerAdapter recyclerAdapter;
    public static Adapter_GridviewSanPham adapter_gridviewSanPham;
    ExpandalableHeightGridView gridView1;
    RelativeLayout trangchu_giohang;
    //dem gio hang
    List<GioHang> listgiohang;
    GioHangDAO giohangdao;
    String email;
    FirebaseAuth mdatabase;

    public static TextView txtcount_shop = null;

    RelativeLayout relativeDonHang;
    private static TrangChuFragment ins;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trangchu, container, false);

        viewFlipper = (ViewFlipper) rootView.findViewById(R.id.ViewFlipper);

        int images[] = {R.drawable.panner1, R.drawable.panner2, R.drawable.panner3, R.drawable.panner4
                , R.drawable.panner5};
        for (int image : images) {
            setViewFlipper(image);
        }
        imageView = rootView.findViewById(R.id.imgDanhMuc);
        imageView1 = rootView.findViewById(R.id.imgDonHang);
        imageView2 = rootView.findViewById(R.id.imgVitri);
        recyclerView = rootView.findViewById(R.id.recyclerViewDanhMuc);
        relativeDonHang = rootView.findViewById(R.id.relativeDonHang);
        // List sản phẩm
        gridView1 = (ExpandalableHeightGridView) rootView.findViewById(R.id.gridviewSanPham1);

        List<SanPham> sanPhams = new ArrayList<>();
        gridView1 = (ExpandalableHeightGridView) rootView.findViewById(R.id.gridviewSanPham1);
        SanPhamDAO sanPhamDAO = new SanPhamDAO(getContext());
        sanPhams = sanPhamDAO.layhetSanPham();
        adapter_gridviewSanPham = new Adapter_GridviewSanPham(getContext(), sanPhams);
        gridView1.setAdapter(adapter_gridviewSanPham);
        gridView1.setExpanded(true);



        // List Thể Loại
        LinearLayoutManager linearLayoutManage = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManage);
        List<TheLoai> loais = new ArrayList<>();
        TheLoaiDAO theLoaiDAO = new TheLoaiDAO(getContext());
        loais = theLoaiDAO.layHetTheLoai1();
        recyclerAdapter = new RecyclerAdapter(loais, getContext());
        recyclerView.setAdapter(recyclerAdapter);
        // Xem thêm danh mục ==============================================================================
        txtxemthem = rootView.findViewById(R.id.txtXemThem);
        setRelativeDonHang();
        txtxemthem.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment mFragment = new DanhMucFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container_main, mFragment).commit();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment mFragment = new DanhMucFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container_main, mFragment).commit();
            }
        });
        // ------------------------------------------------------------------------------------------
        // Phần google maps =================================================================================
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GoogleMapsActivity.class);
                startActivity(intent);
            }
        });

        return rootView;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtcount_shop = view.findViewById(R.id.txtcount_shop);
        mdatabase = FirebaseAuth.getInstance();
        if (mdatabase.getCurrentUser() != null) {
            email = mdatabase.getCurrentUser().getEmail();
        } else {
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }
        //   demgiohang();
        toolbar = view.findViewById(R.id.toolBarTrangChu);
        trangchu_giohang = view.findViewById(R.id.trangchu_giohang);
        trangchu_giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iq = new Intent(getContext(), GioHangActivity.class);
                startActivity(iq);
            }
        });
    }




    @Override
    public void onStart() {
        super.onStart();
        List<SanPham> sanPhams = new ArrayList<>();
        SanPhamDAO sanPhamDAO = new SanPhamDAO(getContext());
        sanPhams = sanPhamDAO.layhetSanPham();
        adapter_gridviewSanPham = new Adapter_GridviewSanPham(getContext(), sanPhams);
        gridView1.setAdapter(adapter_gridviewSanPham);
        gridView1.setExpanded(true);
        demgiohang();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public static void capNhatAdapter() {
        recyclerAdapter.notifyDataSetChanged();
    }
        private void setViewFlipper ( int image){
            ImageView imageView = new ImageView(getContext());
            imageView.setBackgroundResource(image);
            viewFlipper.addView(imageView);
            viewFlipper.setFlipInterval(2000);
            viewFlipper.setAutoStart(true);
            viewFlipper.setInAnimation(getContext(), android.R.anim.fade_in);
            viewFlipper.setOutAnimation(getContext(), android.R.anim.fade_out);

        }


        private void loadUserProfile (AccessToken accessToken)
        {

            GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try {
                        if (object == null) {
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
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }

    private void setRelativeDonHang(){
        relativeDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), QuanLiDonHangActivity.class);
                intent.setAction("QLDH");
                startActivity(intent);
            }
        });
    }

    public static void demgiohang() {

        int count = 0;
        if (ManHinhCho_Activity.listgiohangmh == null) {
            count = MainActivity.listgiohangmain.size();
        } else {
            count = ManHinhCho_Activity.listgiohangmh.size();
        }
        if (count <= 0) {
            txtcount_shop.setVisibility(View.GONE);
        } else {
            txtcount_shop.setText(count + "");
            txtcount_shop.setVisibility(View.VISIBLE);
        }
    }
}




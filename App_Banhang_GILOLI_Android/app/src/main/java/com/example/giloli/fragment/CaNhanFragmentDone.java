package com.example.giloli.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.giloli.Activity.DiaChiKhongCoThongTin;
import com.example.giloli.Activity.HienHoaDonChiTietActivity;
import com.example.giloli.Activity.NhanXetCuaToiActivity;
import com.example.giloli.Activity.QuanLiDonHangActivity;
import com.example.giloli.Activity.SuaThongTinNguoiDungActivity;
import com.example.giloli.Activity.ThemSoDiaChiDone;
import com.example.giloli.DAO.AddDiaChiNguoiDungDao;
import com.example.giloli.DAO.NguoiDungDAO;
import com.example.giloli.MainActivity;
import com.example.giloli.Model.DiaChiNguoiDung;
import com.example.giloli.R;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CaNhanFragmentDone extends Fragment {
    TextView tvTenDone;
    TextView tvEmailDone;
    Button btnDangXuat;
    FirebaseAuth mdatabase;
    CircleImageView imgCircleUser;
    String email;
    String image_url;
    CircleImageView circleImageView;
    ImageView imgnext;
    RelativeLayout sodiachi;
    GoogleSignInClient mGoogleSignInClient;
    List<DiaChiNguoiDung> listdcnd;
    AddDiaChiNguoiDungDao dcnddao;
    private RelativeLayout relativeQLDH, relativeGiloli, relativeDHDCVC, relativeDHTC, relativeHuyDon;
    private RelativeLayout relativeNXCT, relativeSTTTT, relativeSPDM;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_canhan_done,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = view.findViewById(R.id.toolbar_canhan_done);
        toolbar.setTitle("Cá nhân");
        circleImageView = view.findViewById(R.id.imgCircleUser);
        tvTenDone = view.findViewById(R.id.txtTenDone);
        tvEmailDone = view.findViewById(R.id.txtEmailDone);
        btnDangXuat = view.findViewById(R.id.btnDangXuat);
        relativeQLDH = view.findViewById(R.id.relativeQLDH);
        relativeGiloli = view.findViewById(R.id.relativeGiloli);
        relativeDHDCVC = view.findViewById(R.id.relativeDCVC);
        relativeDHTC = view.findViewById(R.id.relativeDHTC);
        imgCircleUser=view.findViewById(R.id.imgCircleUser);
        relativeSPDM = view.findViewById(R.id.relativeSPDM);
        relativeSTTTT = view.findViewById(R.id.relativeSTTTT);
        relativeNXCT = view.findViewById(R.id.relativeNXCT);
        imgnext=view.findViewById(R.id.imgnext);
        relativeHuyDon = view.findViewById(R.id.relativeHuyDon);
        mdatabase = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
        if (mdatabase.getCurrentUser()!=null) {
            email = mdatabase.getCurrentUser().getEmail();
            tvEmailDone.setText(email);
            Uri personPhoto = mdatabase.getCurrentUser().getPhotoUrl();
            Picasso.get().load(personPhoto).into(imgCircleUser);
        } else {
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }

        NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(getContext());
        nguoiDungDAO.layTen(tvEmailDone.getText().toString(),tvTenDone);
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Đăng xuất thành công",Toast.LENGTH_SHORT).show();
                if (mdatabase.getCurrentUser()!= null) {
                   // FirebaseAuth.getInstance().signOut();
signOutgg();

                } else if (AccessToken.getCurrentAccessToken() != null){
                    LoginManager.getInstance().logOut();
                }
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });
        imgnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), SuaThongTinNguoiDungActivity.class);
                startActivity(i);
            }
        });
        sodiachi=view.findViewById(R.id.sodiachi);
        dcnddao = new AddDiaChiNguoiDungDao(getContext());
        listdcnd = new ArrayList<DiaChiNguoiDung>();
        listdcnd = (ArrayList<DiaChiNguoiDung>)dcnddao.layDiaChiTheoEmail(email);
        setRelative();
        sodiachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent iq = new Intent(getContext(), ThemSoDiaChiDone.class);
                    startActivity(iq);


            }
        });

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
                    String id = object.getString("id");
                    image_url = "https://graph.facebook.com/"+id+"/picture?type=normal";
                    tvEmailDone.setText(email1);
                    email = email1;
                    NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(getContext());
                    nguoiDungDAO.layTen(tvEmailDone.getText().toString(),tvTenDone);
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();
                    Glide.with(getContext()).load(image_url).into(circleImageView);

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
    private void signOutgg() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                     //   Intent i = new Intent(getContext(), MainActivity.class);
                      //  startActivity(i);
                        FirebaseAuth.getInstance().signOut();
                    }
                });
    }
    private void setRelative(){
        relativeQLDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), QuanLiDonHangActivity.class);
                intent.setAction("QLDH");
                startActivity(intent);
            }
        });
        relativeGiloli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),QuanLiDonHangActivity.class);
                intent.setAction("Giloli");
                startActivity(intent);
            }
        });
        relativeDHDCVC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),QuanLiDonHangActivity.class);
                intent.setAction("DCVC");
                startActivity(intent);
            }
        });
        relativeDHTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),QuanLiDonHangActivity.class);
                intent.setAction("DHTC");
                startActivity(intent);
            }
        });
        relativeHuyDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),QuanLiDonHangActivity.class);
                intent.setAction("HDH");
                startActivity(intent);
            }
        });
        relativeNXCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), NhanXetCuaToiActivity.class));
            }
        });
        relativeSTTTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Xin lỗi bạn, hiện tại chức năng này chưa sử dụng được, mong bạn thông cảm",
                        Toast.LENGTH_SHORT).show();
            }
        });
        relativeSPDM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), HienHoaDonChiTietActivity.class);
                intent.setAction("FromCND");
                startActivity(intent);
            }
        });
    }

}

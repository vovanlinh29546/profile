package com.example.giloli.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageButton;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;


import com.bumptech.glide.request.RequestOptions;

import com.example.giloli.Activity.QuenMatKhauActivity;
import com.example.giloli.DAO.NguoiDungDAO;
import com.example.giloli.MainActivity;
import com.example.giloli.Model.NguoiDung;
import com.example.giloli.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;

import com.facebook.login.LoginManager;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DangNhapFragment extends Fragment {
    EditText edtemail, edtmatkhau;
    Button btndangnhap;
    TextInputLayout inputemail, inputmatkhau;
    //firebaselogin
    FirebaseAuth firebaseAuth;
    //facebook
    CallbackManager callbackManager;
    LoginButton loginButton;
//google
SignInButton btngoogle;
    static final int RC_SIGN_IN=123;
    GoogleSignInClient mGoogleSignInClient;
    private  String TAG="mainActivity";
    NguoiDungDAO nddao;
    NguoiDung nd;
    List<NguoiDung>list;
    TextView txtQuenmk;
    private static final String alpha = "abcdefghijklmnopqrstuvwxyz"; // a-z
    private static final String alphaUpperCase = alpha.toUpperCase(); // A-Z
    private static final String digits = "0123456789"; // 0-9
    private static final String ALPHA_NUMERIC = alpha + alphaUpperCase + digits;
    private static Random generator = new Random();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dangnhap, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        callbackManager = CallbackManager.Factory.create();
        super.onViewCreated(view, savedInstanceState);
        //anh xa
        edtemail = view.findViewById(R.id.edt_loginEmail);
        edtmatkhau = view.findViewById(R.id.edt_loginMatKhau);
        btndangnhap = view.findViewById(R.id.btnDangNhap);
        inputemail = view.findViewById(R.id.input_loginemail);
        inputmatkhau = view.findViewById(R.id.input_loginmatkhau);
        loginButton = view.findViewById(R.id.loginbutton);
         txtQuenmk=view.findViewById(R.id.txtQuenMk);
        setLoginButton();
        txtQuenmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ia = new Intent(getContext(), QuenMatKhauActivity.class);
                startActivity(ia);
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
// Callback registratio

//google
        btngoogle=view.findViewById(R.id.signGoogle);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
        btngoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });



        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtemail.getText().toString().isEmpty() ) {
                    inputemail.setError("Xin nhập vào email");

                    return;
                }
                else if ( edtmatkhau.getText().toString().isEmpty()){
                    inputmatkhau.setError("Xin nhập vào mật khẩu");
                    return;
                }
            else {
                    final ProgressDialog progressDialog = ProgressDialog.show(getContext(), "Please wait....", "Processing..", true);

                    (firebaseAuth.signInWithEmailAndPassword(edtemail.getText().toString(), edtmatkhau.getText().toString()))
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getContext(), MainActivity.class);
                                        startActivity(i);
                                    } else {
                                        Log.e("ERROR", task.getException().toString());
                                        Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }


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
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(getContext());
                    list = new ArrayList<NguoiDung>();
                    list = nguoiDungDAO.getNguoiDung();
                    for (int j = 0; j <= list.size() - 1; j++) {
                        if (list.size() > 0) {
                            if (email.equals(list.get(j).getEmail())) {
                                return;
                            }

                        }
                    }
                    NguoiDung nguoiDung = new NguoiDung();
                    nguoiDung.setEmail(email);
                    nguoiDung.setHoten(first_name + " " + last_name);
                    List<NguoiDung> nguoidungs2= new ArrayList<NguoiDung>();
                    nguoidungs2 = nguoiDungDAO.getNguoiDung();
                    int newID= 8;
                    nguoiDung.setiDNguoiDung(randomAlphaNumeric(newID));
                    nguoiDungDAO.addNguoiDung(nguoiDung);
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();
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



//google

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);
    }



    private void setLoginButton() {
        loginButton.setFragment(this);
        loginButton.setReadPermissions(Arrays.asList("email","public_profile"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                Toast.makeText(getContext(),"Đăng nhập thành công",
                        Toast.LENGTH_SHORT);
                startActivity(new Intent(getContext(), MainActivity.class));
                loadUserProfile(AccessToken.getCurrentAccessToken());
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {

            }

        });
    }


            private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
                Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
               final ProgressDialog progressDialoggg = ProgressDialog.show(getContext(), "Please wait....", "Processing..", true);

                AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
                firebaseAuth.signInWithCredential(credential)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialoggg.dismiss();
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithCredential:success");
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    updateUI(user);
                                    Intent i = new Intent(getContext(), MainActivity.class);
                                    startActivity(i);
                                    Toast.makeText(getContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                                    Toast.makeText(getContext(), "faile", Toast.LENGTH_SHORT).show();
                                         updateUI(null);
                                }

                                // ...
                            }
                        });
            }

            private void updateUI(FirebaseUser currentUser) {
                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
                String personName;
                String personEmail;

String ngaysinh="";
                String sdt="";
                String matkhau="";

                if (acct != null) {

                    NguoiDungDAO nddao = new NguoiDungDAO(getContext());
                    list = new ArrayList<NguoiDung>();
                    list = nddao.getNguoiDung();
                    personName = acct.getDisplayName();
                    personEmail = acct.getEmail();
                    for (int j = 0; j <= list.size() - 1; j++) {
                        if (list.size() > 0) {
                            if (personEmail.equals(list.get(j).getEmail())) {
                                return;
                            }
                        }
                    }
                    NguoiDung nguoiDung = new NguoiDung();
                    nguoiDung.setEmail(personEmail);
                    nguoiDung.setHoten(personName);
                    nguoiDung.setNgaySinh(ngaysinh);
                    nguoiDung.setSoDienThoai(sdt);
                    nguoiDung.setMatKhau(matkhau);
                    List<NguoiDung> nguoiDungs2 = new ArrayList<NguoiDung>();
                    nguoiDungs2 = nddao.getNguoiDung();
                    int newID = 8;
                    nguoiDung.setiDNguoiDung(randomAlphaNumeric(newID));
                    nddao.addNguoiDungFirebase(nguoiDung);
                return;
                }
                else {
                    Toast.makeText(getContext(), "Bạn cần đăng nhập", Toast.LENGTH_SHORT).show();
                }


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
        }











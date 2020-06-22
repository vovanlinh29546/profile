package com.example.giloli;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.giloli.DAO.GioHangDAO;
import com.example.giloli.Model.GioHang;
import com.example.giloli.fragment.TrangChuFragment;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ManHinhCho_Activity extends AppCompatActivity {
    ProgressBar progressBar;
    private int progressStatus = 0;
    private Animation animation;
    private Handler handler = new Handler();
    ImageView logo_splashscreen;
    //gio hang
    public static List<GioHang> listgiohangmh=null;
    static GioHangDAO giohangdaomh;
     String emailmh;
    FirebaseAuth mdatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_manhinhcho);
        mdatabase = FirebaseAuth.getInstance();
        if (mdatabase.getCurrentUser() != null) {
            emailmh = mdatabase.getCurrentUser().getEmail();
        }
        else {
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }
        if (listgiohangmh!=null){}
        else {
            listgiohangmh=new ArrayList<>();
            giohangdaomh = new GioHangDAO(ManHinhCho_Activity.this);
            listgiohangmh=giohangdaomh.layGioHangTheoEmail(emailmh);

        }


        progressBar = findViewById(R.id.progressbar);
        logo_splashscreen = findViewById(R.id.logo_splashcreen);
        animation = AnimationUtils.loadAnimation(ManHinhCho_Activity.this, R.anim.alpha_animation);
        logo_splashscreen.startAnimation(animation);
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;

                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            if (progressStatus == 100) {
                                progressBar.setProgress(progressStatus);

                                Intent intent = new Intent(ManHinhCho_Activity.this, MainActivity.class);
                                startActivity(intent);
                                // Bấm back ở MainActivity không trở về màn hình chờ
                                finish();
                            }
                        }
                    });
                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

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
                    emailmh = email1;

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
}

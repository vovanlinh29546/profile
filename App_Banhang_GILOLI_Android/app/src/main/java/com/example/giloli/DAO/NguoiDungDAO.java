package com.example.giloli.DAO;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.giloli.Model.NguoiDung;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NguoiDungDAO {
   private DatabaseReference mDatabase;
 //  FirebaseDatabase mfirebasedatabase;
    FirebaseAuth mAuth;
   Context context;
   String maNguoiDung;
    public static List<NguoiDung> nguoidungs=new ArrayList<>();

   public NguoiDungDAO(Context context){
       this.mDatabase = FirebaseDatabase.getInstance().getReference("NguoiDung");
       this.mAuth = FirebaseAuth.getInstance();
       this.context = context;
   }
   public void layTen(String email, final TextView tv){
       ValueEventListener listener = new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                   NguoiDung nguoiDung = dataSnapshot1.getValue(NguoiDung.class);
                   tv.setText(nguoiDung.getHoten());
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       };
       Query query = FirebaseDatabase.getInstance().getReference("NguoiDung")
               .orderByChild("email").equalTo(email);
       query.addListenerForSingleValueEvent(listener);
   }
    public void layTenvsSDT(String email, final EditText tv, final EditText sdt){
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    NguoiDung nguoiDung = dataSnapshot1.getValue(NguoiDung.class);
                    tv.setText(nguoiDung.getHoten());
                    sdt.setText(nguoiDung.getNgaySinh());
                   // mandung= String.valueOf(nguoiDung.getiDNguoiDung());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        Query query = FirebaseDatabase.getInstance().getReference("NguoiDung")
                .orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(listener);
    }

    public ArrayList<String> laySDTNguoiDung(String email){
        final ArrayList<String> nguoidung=new ArrayList<>();
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    NguoiDung nguoiDung = dataSnapshot1.getValue(NguoiDung.class);
                    // mandung= String.valueOf(nguoiDung.getiDNguoiDung());
                    String mand=String.valueOf(nguoiDung.getSoDienThoai());
                    nguoidung.add(mand);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        Query query = FirebaseDatabase.getInstance().getReference("NguoiDung")
                .orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(listener);
        return nguoidung;
    }
    public ArrayList<String> layMKNguoiDung(String email){
        final ArrayList<String> nguoidung=new ArrayList<>();
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
nguoidung.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    NguoiDung nguoiDung = dataSnapshot1.getValue(NguoiDung.class);
                    // mandung= String.valueOf(nguoiDung.getiDNguoiDung());
                    String mand=String.valueOf(nguoiDung.getMatKhau());
                    nguoidung.add(mand);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        Query query = FirebaseDatabase.getInstance().getReference("NguoiDung")
                .orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(listener);
        return nguoidung;
    }

    public List<NguoiDung>getNguoiDung() {

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                nguoidungs.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    NguoiDung s = data.getValue(NguoiDung.class);
                    nguoidungs.add(s);
//
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(listener);
        return nguoidungs;
    }
   public void addNguoiDung(NguoiDung nguoidung){
       maNguoiDung = mDatabase.push().getKey();
       mDatabase.child(maNguoiDung).setValue(nguoidung).addOnSuccessListener(new OnSuccessListener<Void>() {
           @Override
           public void onSuccess(Void aVoid) {
             //  Toast.makeText(context,"Thêm thành công",Toast.LENGTH_SHORT).show();
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
              // Toast.makeText(context,"Thêm thất bại",Toast.LENGTH_SHORT).show();
           }
       });
   }
    public void addNguoiDungFirebase(NguoiDung nguoidung){

        String userUID = mAuth.getCurrentUser().getUid();
        if (userUID!=null){
            DatabaseReference mUser = FirebaseDatabase.getInstance().getReference().child("NguoiDung");
            // mUser.child(userUID).child("Email").setValue(email);
            // mUser.child(userUID).child("Email").setValue(email);
            mUser.child(userUID).setValue(nguoidung).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    //  Toast.makeText(context,"Thêm thành công",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Toast.makeText(context,"Thêm thất bại",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    public void updateNguoiDung(final NguoiDung nguoidung) {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("iDNguoiDung").getValue(String.class).equals(nguoidung.getiDNguoiDung())) {
                        maNguoiDung = data.getKey();
                        mDatabase.child(maNguoiDung).setValue(nguoidung)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context, "Cập nhật thành công",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Cập nhật thất bại",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public List<NguoiDung> layNguoiDungTheoEmail(String email) {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                nguoidungs.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    NguoiDung s = data.getValue(NguoiDung.class);
                    nguoidungs.add(s);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        Query query = FirebaseDatabase.getInstance().getReference("NguoiDung")
                .orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(listener);
        return nguoidungs;
    }
}

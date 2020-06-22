package com.example.adminsever.Adapter;

import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.adminsever.Books;
import com.example.adminsever.Model.Students;
import com.example.adminsever.R;


import java.util.ArrayList;

public class StudentAdapter extends ArrayAdapter {
    TextView tvId,tvName,tvEmail;
    ImageButton btnXoa;
    Context context;
    ArrayList<Students> students;

    public StudentAdapter(@NonNull Context context, ArrayList<Students> students) {
        super(context, 0,students);
        this.context = context;
        this.students = students;
    }


    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View v = convertView;

        if (v == null){
            v= LayoutInflater.from(context).inflate(R.layout.item_list,parent,false);
        }

        final Students sv = students.get(position);
        if (sv !=null) {
            //anh xa
            tvId = (TextView) v.findViewById(R.id.tvId);
            tvName = (TextView) v.findViewById(R.id.tvName);
            tvEmail = (TextView) v.findViewById(R.id.tvEmail);
            btnXoa = (ImageButton)v.findViewById(R.id.btnXoa);
            //set data len layout custom

            tvId.setText(sv.id);
            tvName.setText(sv.name);
            tvEmail.setText(sv.email);

        }
        // click vao item

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Ban vua click dong "+position, Toast.LENGTH_SHORT).show();
                ((Books)context).txtId.setText(sv.id);
                ((Books)context).txtName.setText(sv.name);
                ((Books)context).txtEmail.setText(sv.email);
                ((Books)context)._id = sv._id;

            }
        });


        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Truy cap den bien nao do trong MainActivity ((MainActivity)context)

                //Toast.makeText(context, "da xoa "+sv._id, Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder=new AlertDialog.Builder(context,android.R.style.Theme_DeviceDefault_Light_Dialog);
                builder.setTitle("Xóa");
                builder.setMessage("Xác nhận xóa sinh viên" + "\"" + sv.name + "\""  );
                builder.setIcon(R.drawable.ic_delete_alert);
                builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int w) {

                        ((Books)context).xoaStudent(sv._id);

                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int w) {
                    }
                });
                builder.show();
            }
        });




        return v;
    }
}


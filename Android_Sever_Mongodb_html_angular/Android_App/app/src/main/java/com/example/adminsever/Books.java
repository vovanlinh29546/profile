package com.example.adminsever;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adminsever.Adapter.StudentAdapter;
import com.example.adminsever.DAO.StudentDAO;
import com.example.adminsever.Model.Students;

import java.util.ArrayList;

public class Books extends AppCompatActivity {
    Button btnThem, btnSua;
    public EditText txtId, txtName, txtEmail;
    public String _id;
    ListView lv;
    Students sv;
    StudentDAO svDao;
    ArrayList<Students> students;
    public StudentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        lv=findViewById(R.id.lv1);
        txtId=findViewById(R.id.edtId);
        txtName=findViewById(R.id.edtTen);
        txtEmail=findViewById(R.id.edtEmail);

        btnThem=findViewById(R.id.btnThem);
        btnSua=findViewById(R.id.btnSua);

        svDao = new StudentDAO(this);

        capnhatLV();


        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sv = new Students();
                sv.id=txtId.getText().toString();
                sv.name=txtName.getText().toString();
                sv.email=txtEmail.getText().toString();

                if(!sv.id.isEmpty() && !sv.name.isEmpty()){
                    //them student
                    svDao.insert(sv);



                }else{
                    Toast.makeText(Books.this, "Vui long nhap du thong tin", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sv = new Students();
                sv._id = _id;
                sv.id=txtId.getText().toString();
                sv.name=txtName.getText().toString();
                sv.email=txtEmail.getText().toString();

                svDao.update(sv);

                txtId.setText("");
                txtName.setText("");
                txtEmail.setText("");

            }
        });

    }
    public void xoaStudent(String id){

        svDao.delete(id);

        txtId.setText("");
        txtName.setText("");
        txtEmail.setText("");



    }

    public void capnhatLV(){

        // getAll Student
        students = (ArrayList<Students>) svDao.getAll();

        // gan adapter

        adapter = new StudentAdapter(this, students);

        lv.setAdapter(adapter);
    }
}

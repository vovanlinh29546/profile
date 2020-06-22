package com.example.giloli.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.giloli.Activity.ChiTietDanhMucSanPham;
import com.example.giloli.Model.TheLoai;
import com.example.giloli.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<TheLoai> loais = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewdanhmuc,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TheLoai loai = loais.get(position);
        holder.textView.setText(loai.getTenLoai());
        Glide.with(context).asBitmap().load(loai.getuRLAnh()).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChiTietDanhMucSanPham.class);
                intent.putExtra("IDDMTT",loai.getMaLoai());
                intent.setAction("FromTT");
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return loais.size();
    }

    public RecyclerAdapter(List<TheLoai> loais, Context context){
        this.loais = loais;
        this.context = context;
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView imageView;
        TextView textView;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.CircleImageView);
            textView = itemView.findViewById(R.id.txtTenLoai);
            cardView = itemView.findViewById(R.id.cardviewDMTT);
        }
    }
}

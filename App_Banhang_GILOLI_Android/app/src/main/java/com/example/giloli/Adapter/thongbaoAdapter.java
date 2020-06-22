package com.example.giloli.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.giloli.Model.ThongBao;
import com.example.giloli.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class thongbaoAdapter extends RecyclerView.Adapter<thongbaoAdapter.ImageViewHolder> {

    Context mContext;
    ArrayList<ThongBao> mUploads;
    public thongbaoAdapter(Context context, ArrayList<ThongBao> listthongbao){
        mContext = context;
        mUploads = listthongbao;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_thongbao, parent, false);
        return  new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        ThongBao thongbaocurrent = mUploads.get(position);
        holder.tv_tieude.setText(thongbaocurrent.getTieude());
        holder.tv_noidung.setText(thongbaocurrent.getNoidung());
        Picasso.get().load(thongbaocurrent.getUrlanhthongbao()).into(holder.imv_anhthongbao);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_tieude, tv_noidung;
        public  ImageView imv_anhthongbao;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tieude = itemView.findViewById(R.id.tv_tieude);
            tv_noidung = itemView.findViewById(R.id.tv_noidung);
            imv_anhthongbao = itemView.findViewById(R.id.imv_thongbao);
        }}
}

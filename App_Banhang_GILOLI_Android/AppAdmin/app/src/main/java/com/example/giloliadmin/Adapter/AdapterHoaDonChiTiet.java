package com.example.giloliadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;;
import com.example.giloliadmin.Model.SanPham;
import com.example.giloliadmin.R;
import java.util.ArrayList;
import java.util.List;
public class AdapterHoaDonChiTiet extends RecyclerView.Adapter<AdapterHoaDonChiTiet.ViewHolder> {
    private Context context;
    private List<SanPham> listSp;
    public AdapterHoaDonChiTiet(Context context, List<SanPham> hoaDonCtSPS){
        this.context = context;
        this.listSp = hoaDonCtSPS;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.cardview_hoadonchitiet, parent, false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SanPham sanPham = listSp.get(position);
        if (sanPham!=null){
            List<String> list = new ArrayList<String>();
            list = sanPham.getLinkUrl();
            String url = list.get(0);
            Glide.with(context).load(url).into(holder.imageView);
            holder.txtTenSanPham.setText(sanPham.getTenSanPham());
            holder.txtTongSoLuong.setText("100");
        }
    }

    @Override
    public int getItemCount() {
        return listSp.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTenSanPham, txtTongSoLuong;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenSanPham = itemView.findViewById(R.id.txtTenSanPham);
            txtTongSoLuong = itemView.findViewById(R.id.txtTongSoLuong1);
            imageView = itemView.findViewById(R.id.imgSanPham);
        }}
}

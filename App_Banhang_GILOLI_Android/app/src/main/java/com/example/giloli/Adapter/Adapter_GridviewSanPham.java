package com.example.giloli.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.giloli.Activity.ChiTietSPActivity;
import com.example.giloli.Model.SanPham;
import com.example.giloli.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Adapter_GridviewSanPham extends BaseAdapter {
    private Context context;
    private List<SanPham> list;
    public Adapter_GridviewSanPham(Context context, List<SanPham> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view==null){
            LayoutInflater layoutInflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // Ép cả 1 dòng của item_list thành 1 convertView(dạng đóng gói)
            view = layoutInflater.inflate(R.layout.cardview_sanpham,null);
        }
        TextView txtSp = view.findViewById(R.id.txtTenSp);
        TextView txtGT = view.findViewById(R.id.txtGiaSp);
        ImageView imgSP = view.findViewById(R.id.ImageViewSP);
        CardView cardView = view.findViewById(R.id.cardviewSP);
        final ProgressBar progressBar = view.findViewById(R.id.progressbarSP);
        NumberFormat formatter = new DecimalFormat("#,###");
        final SanPham sanPham = list.get(i);
        String formattedNumber = formatter.format(Double.parseDouble(sanPham.getGiaThanh()));
        txtSp.setText(sanPham.getTenSanPham());
        txtGT.setText(formattedNumber+ " đ");
        List<String> list = new ArrayList<>();
        list = sanPham.getLinkUrl();
        String url = null;
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChiTietSPActivity.class);
                intent.putExtra("STT",i);
                intent.putExtra("MaLoai",sanPham.getMaLoai());
                context.startActivity(intent);
            }
        });
        if (list.size()>0){
            url = list.get(0);
        }
        Glide.with(context).load(url).centerCrop().listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(imgSP);
        return view;
    }
}

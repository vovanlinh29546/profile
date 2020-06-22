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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.giloli.Activity.ChiTietDanhMucSanPham;
import com.example.giloli.Model.TheLoai;
import com.example.giloli.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_GridviewDanhMuc extends BaseAdapter{
    public Adapter_GridviewDanhMuc(ArrayList<TheLoai> dulieu, Context context){
        this.context = context;
        this.dulieu = dulieu;

    }
    Context context;
    ArrayList<TheLoai> dulieu;

    @Override
    public int getCount() {
        return dulieu.size();
    }

    @Override
    public Object getItem(int i) {
        return dulieu.get( i );
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertview, ViewGroup viewGroup) {
        if(convertview == null){
            // Context phân biệt hoa thường
            LayoutInflater layoutInflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // Ép cả 1 dòng của item_list thành 1 convertView(dạng đóng gói)
            convertview = layoutInflater.inflate(R.layout.gridview_1row,null);
        }
        CardView cardView = convertview.findViewById(R.id.cardviewDM);
        TextView tvname = convertview.findViewById(R.id.tv_gridview_1row);
        ImageView imv_anh = convertview.findViewById(R.id.imv_gridview_1row);
        final ProgressBar progressBarDANHMUC = convertview.findViewById( R.id.progressdanhmuc );
        final TheLoai theLoai = dulieu.get(i);
        tvname.setText(theLoai.getTenLoai());
        String url = theLoai.getuRLAnh();
        Glide.with(context).load(url).centerCrop().listener( new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                progressBarDANHMUC.setVisibility( View.GONE );
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                progressBarDANHMUC.setVisibility( View.GONE );
                return false;
            }
        } ).into(imv_anh);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChiTietDanhMucSanPham.class);
                intent.putExtra("IDDM",theLoai.getMaLoai());
                intent.setAction("IDDMGV");
                context.startActivity(intent);
            }
        });
        return convertview;
    }
}

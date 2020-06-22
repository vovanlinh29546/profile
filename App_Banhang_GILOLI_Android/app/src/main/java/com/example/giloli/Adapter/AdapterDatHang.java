package com.example.giloli.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giloli.Model.DatHang;
import com.example.giloli.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class AdapterDatHang extends RecyclerView.Adapter<AdapterDatHang.ViewHolder> {
    Context context;
    ArrayList<DatHang> list;
    public AdapterDatHang(Context context, ArrayList<DatHang> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_list_hang,parent,false);
        return new AdapterDatHang.ViewHolder(v,(Activity)context);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final DatHang datHang = list.get(position);
        if (datHang!=null){
            holder.tenPt.setText(datHang.getTenKieu());
            NumberFormat numberFormat = new DecimalFormat("#,###");
            String tienAo = numberFormat.format(Double.parseDouble(datHang.getGia()));
            holder.giaPt.setText(tienAo);
            holder.ngayPt.setText(datHang.getSoNgayGiao());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("position",position);
                    holder.activity.setResult(Activity.RESULT_OK,intent);
                    holder.activity.finish();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tenPt, giaPt, ngayPt;
        private CardView cardView;
        private Activity activity;
        public ViewHolder(View itemview, Activity mActivity){
            super(itemview);
            tenPt = itemview.findViewById(R.id.txtPtGiaoHang);
            giaPt = itemview.findViewById(R.id.txtGiaTien);
            ngayPt = itemview.findViewById(R.id.txtSoNgayGiao);
            cardView = itemview.findViewById(R.id.cardviewPT);
            this.activity = mActivity;

        }
    }
}

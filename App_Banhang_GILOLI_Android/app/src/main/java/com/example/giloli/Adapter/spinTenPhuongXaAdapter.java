package com.example.giloli.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giloli.Activity.ThemSoDiaChi;
import com.example.giloli.DAO.AddPhuongXaDao;
import com.example.giloli.DAO.AddQuanDao;
import com.example.giloli.Model.DiaChi_QuanHuyen;
import com.example.giloli.Model.DiaChi_ThiXa;
import com.example.giloli.R;

import java.util.ArrayList;
import java.util.List;



public class spinTenPhuongXaAdapter extends BaseAdapter implements Filterable {
    Context context;
    List<DiaChi_ThiXa> arrayList;
    List<DiaChi_ThiXa> arrSortList;
    AddPhuongXaDao qdao;
    private Filter PXFilter;
    public LayoutInflater inflater;
    public spinTenPhuongXaAdapter(Context context, List<DiaChi_ThiXa> arrayList) {
        super();
        this.context = context;
        this.arrayList = arrayList;
        this.arrSortList = arrayList;
        this.inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        qdao = new AddPhuongXaDao(context);
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public static class ViewHolder {
        TextView tenpx;
    }
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if(convertView==null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.sp_tenphuongxa, null);

            holder.tenpx= (TextView) convertView.findViewById(R.id.txttenphuongxa);
            convertView.setTag(holder);
        }
        else
            holder=(ViewHolder)convertView.getTag();
        DiaChi_ThiXa _entry = (DiaChi_ThiXa) arrayList.get(i);
        holder.tenpx.setText( _entry.getTenPhuongXa());
        return convertView;
    }
    public void changeDataset(List<DiaChi_ThiXa> items){
        this.arrayList = items;
        notifyDataSetChanged();
    }
    public void resetData() {
        arrayList = arrSortList; }
    public Filter getFilter() {
        if (PXFilter == null)
            PXFilter = new CustomFilter();
        return PXFilter; }
    private class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
// We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                results.values = arrSortList;
                results.count = arrSortList.size();
            }
            else {
                List<DiaChi_ThiXa> lsSach = new ArrayList<DiaChi_ThiXa>();
                for (DiaChi_ThiXa p : arrayList) {
                    if
                    (p.getTenPhuongXa().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        lsSach.add(p);
                }
                results.values = lsSach;
                results.count = lsSach.size();
            }
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            if (results.count == 0) {
                //   notifyDataSetInvalidated();
            }
            else {
                arrayList = (List<DiaChi_ThiXa>) results.values;
                notifyDataSetChanged();
            } }
    }
}

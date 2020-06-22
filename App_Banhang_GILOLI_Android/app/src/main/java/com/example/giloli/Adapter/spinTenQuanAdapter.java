package com.example.giloli.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giloli.Activity.ThemSoDiaChi;
import com.example.giloli.DAO.AddQuanDao;
import com.example.giloli.Model.DiaChi_QuanHuyen;
import com.example.giloli.R;

import java.util.ArrayList;
import java.util.List;


public class spinTenQuanAdapter extends BaseAdapter implements Filterable{
    Context context;
    List<DiaChi_QuanHuyen> arrayList;
    List<DiaChi_QuanHuyen> arrSortList;
    AddQuanDao qdao;
    private Filter sachFilter;
    public LayoutInflater inflater;
    public spinTenQuanAdapter(Context context, List<DiaChi_QuanHuyen> arrayList) {
        super();
        this.context = context;
        this.arrayList = arrayList;
        this.arrSortList = arrayList;
        this.inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        qdao = new AddQuanDao(context);
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
        TextView tenquan;
    }
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if(convertView==null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.sp_tenquan, null);

            holder.tenquan= (TextView) convertView.findViewById(R.id.txttenQuan);
            convertView.setTag(holder);
        }
        else
            holder=(ViewHolder)convertView.getTag();
        DiaChi_QuanHuyen _entry = (DiaChi_QuanHuyen) arrayList.get(i);
        holder.tenquan.setText( _entry.getTenQuan());
        return convertView;
    }

    public void changeDataset(List<DiaChi_QuanHuyen> items){
        this.arrayList = items;
        notifyDataSetChanged();
    }
    public void resetData() {
        arrayList = arrSortList; }
    public Filter getFilter() {
        if (sachFilter == null)
            sachFilter = new CustomFilter();
        return sachFilter; }
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
                List<DiaChi_QuanHuyen> lsSach = new ArrayList<DiaChi_QuanHuyen>();
                for (DiaChi_QuanHuyen p : arrayList) {
                    if
                    (p.getTenQuan().toUpperCase().startsWith(constraint.toString().toUpperCase()))
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
                arrayList = (List<DiaChi_QuanHuyen>) results.values;
                notifyDataSetChanged();
            } }
    }
}


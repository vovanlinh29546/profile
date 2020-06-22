package com.example.giloli.Model;

import androidx.recyclerview.widget.DiffUtil;

import java.util.ArrayList;

public class GioHangDifff extends DiffUtil.Callback {
    private ArrayList<GioHang> mOldList;
    private ArrayList<GioHang> mNewList;

    public GioHangDifff(ArrayList<GioHang> oldList, ArrayList<GioHang> newList) {
        this.mOldList = oldList;
        this.mNewList = newList;
    }
    @Override
    public int getOldListSize() {
        return mOldList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        // add a unique ID property on Contact and expose a getId() method
        return mOldList.get(oldItemPosition).getIdGioiHang() == mNewList.get(newItemPosition).getIdGioiHang();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        GioHang oldContact = mOldList.get(oldItemPosition);
        GioHang newContact = mNewList.get(newItemPosition);

        if (oldContact.getSoLuong() == newContact.getSoLuong()) {
            return true;
        }
        return false;
    }
}

package com.karaoke.karaokebook.data.model;

import android.os.Looper;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ListLiveData<T> extends LiveData<List<T>> {
    private ArrayList<T> dataList;

    public ListLiveData() {
        dataList = new ArrayList<>();
    }

    public void add(T data) {
        dataList.add(data);
        updateValue();
    }

    public void addAll(List<T> list) {
        dataList.addAll(list);
        updateValue();
    }

    public void clear(Boolean notify) {
        dataList.clear();
        if (notify) updateValue();
    }

    public void sort(Comparator<T> method) {
        dataList.sort(method);
        updateValue();
    }

    private void updateValue() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            setValue(dataList);
        } else {
            postValue(dataList);
        }
    }
}

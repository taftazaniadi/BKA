package com.buka.amanah.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
 * Created by OwlDev23 on 11,August,2021
 */


abstract public class BaseAdapterRecycler<T>  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<T> mData = new ArrayList<>();

    public void addItems(T item){
        mData.add(item);
        notifyDataSetChanged();
    }

    public void addItems(T item, int index){
        mData.add(index, item);
        notifyItemChanged(index);
    }

    public void updateItem(T item, int index){
        mData.set(index, item);
        notifyItemChanged(index);
    }

   public T getItem(int index){
        return mData.get(index);
    }

    public List<T> getAllData(){
        return mData;
    }

    public void addAllItem(Collection<T> items){
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public void clearItems(){
        mData.clear();
        notifyDataSetChanged();
    }

    public int getItemsCount(){
        return mData.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false), viewType);
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutId(position, mData.get(position));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((Binder<T>) holder).bind(mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    // set

    protected abstract int getLayoutId(int position, T obj);

    protected abstract RecyclerView.ViewHolder getHolder(View view, int viewType);

    interface Binder<T> {
        public void bind(T data, int pos);
    }
}

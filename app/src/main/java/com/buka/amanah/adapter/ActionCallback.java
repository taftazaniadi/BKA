package com.buka.amanah.adapter;

public interface ActionCallback<T> {
    void onSee(T data);
    void onDel(T data);
    void onEdit(T data);
}

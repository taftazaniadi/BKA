package com.buka.amanah.utils.network;

//
// Created by OwlDev23
//

public interface ResponseCallback<T>{
    public void onSuccess(T response);
    public void onError(String error, Integer code);
    public void onLoading(Boolean isLoading);
}

package com.example.myshop.callback;

/**
 * Created by zouqile on 2016-03-14.
 */
public interface BmobCallback<T> {
    void result(T t,boolean result, String msg);
}

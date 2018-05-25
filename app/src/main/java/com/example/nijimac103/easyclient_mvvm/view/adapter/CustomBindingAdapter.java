package com.example.nijimac103.easyclient_mvvm.view.adapter;

import android.databinding.BindingAdapter;
import android.view.View;

public class CustomBindingAdapter {
    //xmlに定義する際のBindingAdapter
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}

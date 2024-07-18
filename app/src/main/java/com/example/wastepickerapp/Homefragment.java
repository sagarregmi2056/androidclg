package com.example.wastepickerapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;


public class Homefragment extends Fragment {
    private WebView webViewMap;
    private static final String TAG = "Homefragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_homefragment, container, false);

        webViewMap = view.findViewById(R.id.webview_map);

        // Enable JavaScript (if required for the embed code)
        WebSettings webSettings = webViewMap.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String url = "file:///android_asset/google_map.html";
        Log.d(TAG, "Loading URL: " + url);
        // Load Google Maps embed code
        webViewMap.loadUrl(url);
        return view;
    }
}
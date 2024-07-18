package com.example.wastepickerapp;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {
    private WebView webViewMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        webViewMap = findViewById(R.id.webview_map);

        // Enable JavaScript (if required for the embed code)
        WebSettings webSettings = webViewMap.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Load Google Maps embed code
        String mapEmbedCode = "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d226170.82288789697!2d84.24108220319303!3d27.657974341005264!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3994fb37e078d531%3A0x973f22922ea702f7!2sBharatpur%2044200!5e0!3m2!1sen!2snp!4v1720599862054!5m2!1sen!2snp\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>";
        webViewMap.loadDataWithBaseURL(null, mapEmbedCode, "text/html", "UTF-8", null);
    }
}
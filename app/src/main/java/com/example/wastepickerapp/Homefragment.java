package com.example.wastepickerapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wastepickerapp.api.Api;
import com.example.wastepickerapp.api.RetrofitClient;
import com.example.wastepickerapp.models.HomeResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Homefragment extends Fragment {
    private WebView webViewMap;
    private TextView usernameTextView;
    private TextView scheduleDateTextView;
    private TextView arrivalTimeTextView;

    private static final String TAG = "Homefragment";
    private static final String SHARED_PREF_NAME = "wastepicker_pref";
    private static final String KEY_TOKEN = "key_token";
    private SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_homefragment, container, false);
        usernameTextView = view.findViewById(R.id.usernameTextView);
        scheduleDateTextView = view.findViewById(R.id.ScheduleDateTextView);
        arrivalTimeTextView = view.findViewById(R.id.arrivalTimeTextView);

        webViewMap = view.findViewById(R.id.webview_map);
        sharedPreferences = requireContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        // Enable JavaScript (if required for the embed code)
        WebSettings webSettings = webViewMap.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String url = "file:///android_asset/google_map.html";
        Log.d(TAG, "Loading URL: " + url);
        // Load Google Maps embed code
        webViewMap.loadUrl(url);



        ImageView notificationImage = view.findViewById(R.id.notificationImage);
        notificationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment notificationFragment = new NotificationFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, notificationFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        fetchHomeData(view);

        return view;
    }

    private void fetchHomeData(View view) {
        Api api = RetrofitClient.getInstance().getApi();
        String  token = sharedPreferences.getString(KEY_TOKEN, "");
        Call<HomeResponse> call = api.getHome("Bearer " + token);
        call.enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    HomeResponse homeResponse = response.body();
                    if (homeResponse.data != null) {
                        HomeResponse.User user = homeResponse.data.user;
                        Log.d(TAG, "API Response: " + homeResponse.toString()); // Log the entire response
                        HomeResponse.Schedule schedule = homeResponse.data.schedules;

                        String username = user != null ? user.username : "N/A";
                        String scheduleDate = schedule != null ? schedule.date : "N/A";
                        String arrivalTime = schedule != null ? schedule.arrival_time : "N/A";

                        // Convert the date string to a readable format if needed
                        String readableDate = convertDateString(scheduleDate);

                        // Update the TextViews
                        usernameTextView.setText(username);
                        scheduleDateTextView.setText(readableDate);
                        arrivalTimeTextView.setText(arrivalTime);
                    } else {
                        // Handle the case where data is null
                        Log.e(TAG, "Data is null");
                    }
                } else {
                    // Handle unsuccessful response
                    Log.e(TAG, "Response not successful");
                }
            }


            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                // Handle failure
                Log.e(TAG, "API call failed", t);
            }
        });
    }

    private String convertDateString(String dateString) {
        try {
            // Define the input and output date formats
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault());

            // Parse the input date string
            Date date = inputFormat.parse(dateString);

            // Format the date into a readable format
            if (date != null) {
                return outputFormat.format(date);
            }
        } catch (ParseException e) {
            // Handle parsing exception
            Log.e(TAG, "Date parsing failed", e);
        }

        // Implement your date conversion logic here
        return dateString;
    }
}
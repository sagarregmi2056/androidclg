package com.example.wastepickerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wastepickerapp.api.RetrofitClient;
import com.example.wastepickerapp.models.DefaultResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Profilefragment extends Fragment {
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "wastepicker_pref";
    private static final String KEY_TOKEN = "key_token";
    private TextView textLogout;
    private ImageView logoutPic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profilefragment, container, false);
        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        textLogout = view.findViewById(R.id.textLogout);
        logoutPic = view.findViewById(R.id.logoutPic);

        View.OnClickListener logoutClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = sharedPreferences.getString(KEY_TOKEN, null);
                if (token != null) {
                    Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().logout("Bearer " + token);
                    call.enqueue(new Callback<DefaultResponse>() {
                        @Override
                        public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                                // Clear SharedPreferences
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.remove(KEY_TOKEN);
                                editor.apply();

                                // Redirect to login activity
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getActivity(), "Logout failed. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<DefaultResponse> call, Throwable t) {
                            Toast.makeText(getActivity(), "Network error. Please check your connection.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        };

        textLogout.setOnClickListener(logoutClickListener);
        logoutPic.setOnClickListener(logoutClickListener);
        return view;
    }
}
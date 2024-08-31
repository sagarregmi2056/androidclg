package com.example.wastepickerapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wastepickerapp.api.RetrofitClient;
import com.example.wastepickerapp.models.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    private Button  login_BTN;
    private TextView register_BTN;
    private EditText email_ET, password_ET;
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "wastepicker_pref";
    private static final String KEY_TOKEN = "key_token";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        login_BTN = findViewById(R.id.login_BTN);
        register_BTN = findViewById(R.id.register_BTN);
        email_ET = findViewById(R.id.email_ET);
        password_ET= findViewById(R.id.password_ET);

          register_BTN.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Intent intent = new Intent(LoginActivity.this,SignupActivity.class);

                  startActivity(intent);

              }
          });

          login_BTN.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  String email = email_ET.getText().toString().trim();
                  String password = password_ET.getText().toString().trim();
                  if (email.isEmpty()) {
                      email_ET.setError("email is required");
                      email_ET.requestFocus();
                      return;


                  }
                  if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                      email_ET.setError("Please enter a valid email");
                      email_ET.requestFocus();
                      return;
                  }
                  if (password.isEmpty()) {
                      password_ET.setError("email is required");
                      password_ET.requestFocus();
                      return;


                  }
                  if (password.length()<6) {
                      password_ET.setError("enter a password greater than 6");
                      password_ET.requestFocus();
                      return;
                  }
                  Call<LoginResponse> call = RetrofitClient.getInstance().getApi().Login(email, password);
                  call.enqueue(new Callback<LoginResponse>() {
                      @Override
                      public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                          if (response.isSuccessful()) {
                              LoginResponse loginResponse = response.body();
                              if (loginResponse != null) {
                                  String status = loginResponse.getStatus();
                                  String message = loginResponse.getMessage();
                                  String token = loginResponse.getToken();

                                  if ("Success".equals(status)) {
                                      SharedPreferences.Editor editor = sharedPreferences.edit();
                                      editor.putString(KEY_TOKEN, token);
                                      editor.apply();
                                      Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                      // Save token and proceed to the main activity
                                      // e.g., SharedPreferences or other storage
                                      Intent intent = new Intent(LoginActivity.this, AddressFormActivity.class);
                                      startActivity(intent);
                                      finish(); // Close the LoginActivity
                                  } else {
                                      // Handle any other messages such as "Invalid credentials"
                                      Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                  }
                              } else {
                                  Toast.makeText(LoginActivity.this, "Unexpected response from server.", Toast.LENGTH_SHORT).show();
                              }
                          } else {
                              if (response.code() == 401) { // Unauthorized error code
                                  Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                              } else {
                                  Toast.makeText(LoginActivity.this, "Server error. Please try again later.", Toast.LENGTH_SHORT).show();
                              }
                          }
                      }

                      @Override
                      public void onFailure(Call<LoginResponse> call, Throwable throwable) {
                          Log.e("API Error", "Network Error: ", throwable);
                          Toast.makeText(LoginActivity.this, "Network error. Please check your connection.", Toast.LENGTH_SHORT).show();
                      }
                  });
              }
          });







    }
}
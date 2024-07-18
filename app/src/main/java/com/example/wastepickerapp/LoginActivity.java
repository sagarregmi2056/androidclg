package com.example.wastepickerapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {
    private Button register_BTN, login_BTN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        login_BTN = findViewById(R.id.login_BTN);
        register_BTN = findViewById(R.id.register_BTN);

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
                  Intent intent = new Intent(LoginActivity.this ,MainActivity.class);
                  startActivity(intent);
              }
          });







    }
}
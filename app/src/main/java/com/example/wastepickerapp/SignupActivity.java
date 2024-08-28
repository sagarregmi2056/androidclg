package com.example.wastepickerapp;

import android.content.Intent;
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
import com.example.wastepickerapp.models.DefaultResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private Button register_BTN ;
    private TextView login_BTN;
 private EditText editTextUsername,editTextEmail,editTextPassword,editTextConfirmPw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        login_BTN = findViewById(R.id.login_BTN);
        register_BTN = findViewById(R.id.register_BTN);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPw = findViewById(R.id.editTextConfirmPw);

login_BTN.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
        startActivity(intent);
    }
});

register_BTN.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String email = editTextEmail.getText().toString().trim();
        String username = editTextUsername.getText().toString().trim();

        String password = editTextPassword.getText().toString().trim();
        String confirm_password = editTextConfirmPw.getText().toString().trim();
        if (email.isEmpty()) {
            editTextEmail.setError("email is required");
            editTextEmail.requestFocus();
            return;


        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("email is required");
            editTextPassword.requestFocus();
            return;


        }
        if (password.length()<6) {
            editTextPassword.setError("enter a password greater than 6");
            editTextPassword.requestFocus();
            return;
        }

        if (confirm_password.isEmpty()) {
            editTextConfirmPw.setError("Confirm Password is required");
            editTextConfirmPw.requestFocus();
            return;
        }
        if (!password.equals(confirm_password)) {
            editTextConfirmPw.setError("Passwords do not match");
            editTextConfirmPw.requestFocus();
            return;
        }
        Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().Register(username,email,password,confirm_password);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {


                    // Convert response body to a string
                    if (response.code() == 201) {
                        DefaultResponse dr = response.body();
                        Toast.makeText(SignupActivity.this, dr.getMsg(), Toast.LENGTH_LONG).show();
                        // Redirect to LoginActivity
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else if(response.code() == 422) {

                        Toast.makeText(SignupActivity.this,"user already exist" , Toast.LENGTH_SHORT).show();
                    }

                }



            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable throwable) {
                Log.e("API Error", "Network Error: ", throwable);
                Toast.makeText(SignupActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
});
    }
}
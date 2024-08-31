package com.example.wastepickerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.wastepickerapp.api.Api;
import com.example.wastepickerapp.api.RetrofitClient;
import com.example.wastepickerapp.models.DefaultResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressFormActivity extends AppCompatActivity {
    private Button Submit_BTN;
    private Spinner spinnerProvince, spinnerDistrict, spinnerMunicipality;
    private EditText provinceEditText, districtEditText, municipalityEditText, wardEditText, streetEditText;
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "wastepicker_pref";
    private static final String KEY_TOKEN = "key_token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_form);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        Submit_BTN = findViewById(R.id.Submit_BTN);

        spinnerProvince = findViewById(R.id.spinner_province);
        spinnerDistrict = findViewById(R.id.spinner_district);
        spinnerMunicipality = findViewById(R.id.spinner_municipality);
        provinceEditText = findViewById(R.id.province_ET);
        districtEditText = findViewById(R.id.District_ET);
        municipalityEditText = findViewById(R.id.Metro_ET);
        wardEditText = findViewById(R.id.Ward_ET);
        streetEditText = findViewById(R.id.Street_ET);

        // Set up province spinner
        ArrayAdapter<CharSequence> provinceAdapter = ArrayAdapter.createFromResource(this,
                R.array.provinces_array, android.R.layout.simple_spinner_item);
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvince.setAdapter(provinceAdapter);

        spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedProvince = parent.getItemAtPosition(position).toString();
                updateDistrictSpinner(selectedProvince);
                updateMunicipalitySpinner(selectedProvince);
                provinceEditText.setText(selectedProvince);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action needed
            }
        });

        // Set up district spinner with default items
        ArrayAdapter<CharSequence> districtAdapter = ArrayAdapter.createFromResource(this,
                R.array.districts_default, android.R.layout.simple_spinner_item);
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistrict.setAdapter(districtAdapter);

        spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedDistrict = parent.getItemAtPosition(position).toString();
                districtEditText.setText(selectedDistrict);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action needed
            }
        });

        // Set up metropolitan city spinner with default items
        ArrayAdapter<CharSequence> municipalityAdapter = ArrayAdapter.createFromResource(this,
                R.array.metropolitan_cities_default, android.R.layout.simple_spinner_item);
        municipalityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMunicipality.setAdapter(municipalityAdapter);

        spinnerMunicipality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCity = parent.getItemAtPosition(position).toString();
                municipalityEditText.setText(selectedCity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action needed
            }
        });

        Submit_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String province = provinceEditText.getText().toString();
                String district = districtEditText.getText().toString();
                String municipality = municipalityEditText.getText().toString();
                String ward = wardEditText.getText().toString();
                String street = streetEditText.getText().toString();
                String token = sharedPreferences.getString(KEY_TOKEN, "");

                // Check if token is present
                if (token.isEmpty()) {
                    Toast.makeText(AddressFormActivity.this, "User not logged in", Toast.LENGTH_SHORT).show();
                    return;
                }

                submitAddress(province, district, municipality, ward, street);

            }
        });
    }

    private void updateDistrictSpinner(String province) {
        int districtArrayId;
        switch (province) {
            case "Province 1":
                districtArrayId = R.array.districts_province_1;
                break;
            case "Province 2":
                districtArrayId = R.array.districts_province_2;
                break;
            case "Bagmati Province":
                districtArrayId = R.array.districts_bagmati;
                break;
            case "Gandaki Province":
                districtArrayId = R.array.districts_gandaki;
                break;
            case "Lumbini Province":
                districtArrayId = R.array.districts_lumbini;
                break;
            case "Karnali Province":
                districtArrayId = R.array.districts_karnali;
                break;
            case "Sudurpashchim Province":
                districtArrayId = R.array.districts_sudurpashchim;
                break;
            default:
                districtArrayId = R.array.districts_default; // Default case
                break;
        }

        ArrayAdapter<CharSequence> districtAdapter = ArrayAdapter.createFromResource(this,
                districtArrayId, android.R.layout.simple_spinner_item);
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistrict.setAdapter(districtAdapter);
    }

    private void updateMunicipalitySpinner(String province) {
        int municipalityArrayId;
        switch (province) {
            case "Province 1":
                municipalityArrayId = R.array.metropolitan_cities_province_1;
                break;
            case "Province 2":
                municipalityArrayId = R.array.metropolitan_cities_province_2;
                break;
            case "Bagmati Province":
                municipalityArrayId = R.array.metropolitan_cities_bagmati;
                break;
            case "Gandaki Province":
                municipalityArrayId = R.array.metropolitan_cities_gandaki;
                break;
            case "Lumbini Province":
                municipalityArrayId = R.array.metropolitan_cities_lumbini;
                break;
            case "Karnali Province":
                municipalityArrayId = R.array.metropolitan_cities_karnali;
                break;
            case "Sudurpashchim Province":
                municipalityArrayId = R.array.metropolitan_cities_sudurpashchim;
                break;
            default:
                municipalityArrayId = R.array.metropolitan_cities_default; // Default case
                break;
        }

        ArrayAdapter<CharSequence> municipalityAdapter = ArrayAdapter.createFromResource(this,
                municipalityArrayId, android.R.layout.simple_spinner_item);
        municipalityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMunicipality.setAdapter(municipalityAdapter);
    }



    private void submitAddress(String province, String district, String municipality, String ward, String street) {
        Api api = RetrofitClient.getInstance().getApi();
        String token = sharedPreferences.getString(KEY_TOKEN, "");

        // Make the API call
        Call<DefaultResponse> call = api.submitUserLocation("Bearer " + token,province, district, municipality, ward, street);

       call.enqueue(new Callback<DefaultResponse>() {
           @Override
           public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
               if (response.isSuccessful()&& response.body() != null) {
                   DefaultResponse defaultResponse = response.body();
                   Toast.makeText(AddressFormActivity.this, defaultResponse.getMsg(), Toast.LENGTH_SHORT).show();

                   // Optionally, navigate to the next screen or perform other actions
                   Intent intent = new Intent(AddressFormActivity.this, MainActivity.class);
                   startActivity(intent);
           }else {
                   // Log the response body for debugging
                   if (response.errorBody() != null) {
                       try {
                           String errorBody = response.errorBody().string();
                           Log.e("API Response Error Body", errorBody);
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                   }
                   Toast.makeText(AddressFormActivity.this, "Failed to submit address", Toast.LENGTH_SHORT).show();
                   Log.e("API Response", "Error: " + response.code() + " " + response.message());
               }}

           @Override
           public void onFailure(Call<DefaultResponse> call, Throwable throwable) {
               Toast.makeText(AddressFormActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
               Log.e("API Failure", "Error: " + throwable.getMessage());
           }
       });
    }
}







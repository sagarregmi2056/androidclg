package com.example.wastepickerapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView ;
    private FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavView);

        frameLayout = findViewById(R.id.frameLayout);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
              int itemId = menuItem.getItemId();

              if(itemId == R.id.navHome){
                  loadFragment(new Homefragment(),false);



              } else if (itemId == R.id.navSchedule) {
                  loadFragment(new Schedulefragment(),false);
                  
              }else if(itemId == R.id.navReport){
                  loadFragment(new Reportfragment(),false);
                  
                  
                } else if (itemId== R.id.navDiscover) {
                  loadFragment(new Discoveryfragment(),false);
                  
              } else  {
                  loadFragment( new Profilefragment(),false);

                  
              }


                return true;
            }
        });

        loadFragment(new Homefragment(),true);

    }
    private  void loadFragment(Fragment fragment,boolean isAppInitialized){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(isAppInitialized){
            fragmentTransaction.add(R.id.frameLayout,fragment);
        }else {
            fragmentTransaction.replace(R.id.frameLayout, fragment);
        }


        fragmentTransaction.commit();
    }
}

package com.example.yourbeauty.UnregisteredUser;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.yourbeauty.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
{
    private AppBarConfiguration mAppBarConfiguration;

    private static String userCity = "";

    public static String getUserCity()
    {
        return userCity;
    }

    public static void setUserCity(String userCity) {
        MainActivity.userCity = userCity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userCity = getIntent().getStringExtra("CITY_NAME");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder
                (R.id.nav_home)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onSupportNavigateUp()
    {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed()
    {
        if(getSupportFragmentManager().getBackStackEntryCount() == 0)
        {
            if (doubleBackToExitPressedOnce)
            {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, R.string.click_back, Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable()
            {

                @Override
                public void run()
                {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
        else
            super.onBackPressed();
    }
}
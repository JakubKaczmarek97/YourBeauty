package com.example.yourbeauty.LoggedUser;

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

public class UserActivity extends AppCompatActivity
{
    private static String userId = "";
    private static String userCity = "";

    private AppBarConfiguration mAppBarConfiguration;

    public static String getUserId()
    {
        return userId;
    }

    public static String getUserCity()
    {
        return userCity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        userId = getIntent().getStringExtra("USER_ID");
        userCity = getIntent().getStringExtra("CITY_NAME");

        Toolbar toolbar = findViewById(R.id.toolbar_user);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout_user);
        NavigationView navigationView = findViewById(R.id.nav_view_user);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder
                (R.id.nav_home, R.id.nav_logout, R.id.nav_my_profile, R.id.nav_my_orders)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_user);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }
    @Override
    public boolean onSupportNavigateUp()
    {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_user);
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

    @Override
    protected void onDestroy()
    {
        userId = "";
        super.onDestroy();
    }
}
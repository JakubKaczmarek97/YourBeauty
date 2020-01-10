package com.example.yourbeauty;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yourbeauty.LoggedUser.UserActivity;
import com.example.yourbeauty.UnregisteredUser.MainActivity;

public class SplashActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView imageView = findViewById(R.id.splash_image);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.transition_animation);
        imageView.startAnimation(animation);

        String noLogout = SharedPrefs.loadData(this,"who_is_logged");

        System.out.println("SPLASH: " + noLogout);

        final Intent intent;

        if(noLogout.equals("Nobody"))
        {
            intent = new Intent(this, MainActivity.class);
        }
        else
        {
            intent = new Intent(this, UserActivity.class);

            String city_name = SharedPrefs.loadData(this,"city_name" + noLogout);
            intent.putExtra("USER_ID", noLogout);
            intent.putExtra("USER_CITY", city_name);
            UserActivity.setUserId(noLogout);
            UserActivity.setUserCity(city_name);
        }


        Thread thread = new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(3000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    startActivity(intent);
                    finish();
                }
            }
        };
        thread.start();
    }
}
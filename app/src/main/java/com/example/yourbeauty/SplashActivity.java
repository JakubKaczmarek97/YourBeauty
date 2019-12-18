package com.example.yourbeauty;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yourbeauty.UnregisteredUser.MainActivity;

public class SplashActivity extends AppCompatActivity
{
    private TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageView = findViewById(R.id.splash_image);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.transition_animation);
        imageView.startAnimation(animation);

        final Intent intent = new Intent(this, MainActivity.class);

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

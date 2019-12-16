package com.example.yourbeauty.LoggedUser;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yourbeauty.R;

public class UserActivity extends AppCompatActivity
{
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        userId = getIntent().getStringExtra("USER_ID");


        System.out.println("UserActivity: " + userId);
    }
}

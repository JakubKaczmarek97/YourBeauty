package com.example.yourbeauty.LoggedUser;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yourbeauty.R;
import com.example.yourbeauty.ui.SignIn.SignInFragment;

public class UserActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        SignInFragment signInFragment = new SignInFragment();

        System.out.println(signInFragment.getUserId());
    }
}

package com.example.yourbeauty.LoggedUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.yourbeauty.SharedPrefs;
import com.example.yourbeauty.UnregisteredUser.MainActivity;

import java.util.Objects;

public class LogoutFragment extends Fragment
{
    public View onCreateView
            (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        UserActivity.setUserId("");
        UserActivity.setUserCity("");

        SharedPrefs.saveData(getActivity(),"who_is_logged","Nobody");

        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        Objects.requireNonNull(getActivity()).finish();

        return null;
    }
}
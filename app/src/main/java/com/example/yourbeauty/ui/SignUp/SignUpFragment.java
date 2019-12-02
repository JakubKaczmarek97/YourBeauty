package com.example.yourbeauty.ui.SignUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.yourbeauty.R;
import com.example.yourbeauty.SignUpActivity;

public class SignUpFragment extends Fragment
{

    public View onCreateView
            (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        Intent intent = new Intent(getActivity(), SignUpActivity.class);
        startActivity(intent);

        return inflater.inflate(R.layout.fragment_signup, container, false);
    }
}
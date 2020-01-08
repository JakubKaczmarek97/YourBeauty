package com.example.yourbeauty.LoggedUser;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.yourbeauty.R;

import java.util.Objects;

public class ChangeCityFragment extends Fragment
{
    private static final String SHARED_PREFS = "sharedPrefs";
    private String CITY_NAME = "city_name";

    private String userCity;
    private String userID;

    private TextView textView;
    private EditText editText;
    private Button saveButton;

    public View onCreateView
            (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_change_city, container, false);

        userID = UserActivity.getUserId();
        CITY_NAME += userID;

        textView = view.findViewById(R.id.textView);
        editText = view.findViewById(R.id.editText);
        saveButton = view.findViewById(R.id.save_button);

        loadData();

        return view;
    }

    private void saveData()
    {
        SharedPreferences sharedPreferences =
                Objects.requireNonNull(getActivity()).getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(!editText.getText().toString().isEmpty())
        {
            editor.putString(CITY_NAME, editText.getText().toString());
            Toast.makeText(getActivity(),"Data saved", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getActivity(),"City name can't be empty! ", Toast.LENGTH_SHORT).show();
        }

        editor.apply();
    }

    private void loadData()
    {
        SharedPreferences sharedPreferences =
                Objects.requireNonNull(getActivity()).getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        userCity = sharedPreferences.getString(CITY_NAME, "");

        String setText = "Your City: " + userCity;
        textView.setText(setText);

        saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                saveData();
                loadData();
            }
        });

    }
}
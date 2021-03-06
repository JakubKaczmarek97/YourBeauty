package com.example.yourbeauty.LoggedUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.yourbeauty.R;
import com.example.yourbeauty.SharedPrefs;

import java.util.Objects;

public class UserCityFragment extends Fragment
{
    private String userID;

    private EditText editText;
    private Button saveButton;

    private String CITY_NAME = "city_name";
    private String IS_SAVED = "is_saved";
    private String IS_LOGGED_IN = "is_logged_in";
    private final static String WHO_IS_LOGGED = "who_is_logged";

    public View onCreateView
            (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user_city, container, false);

        userID = Objects.requireNonNull(getArguments()).getString("USER_ID");
        String noLogoutChecked = Objects.requireNonNull(getArguments()).getString("NO_LOGOUT");

        if(Objects.requireNonNull(noLogoutChecked).equals("true"))
        {
            SharedPrefs.saveData(getActivity(),WHO_IS_LOGGED, userID);
        }
        else
        {
            SharedPrefs.saveData(getActivity(),WHO_IS_LOGGED, "Nobody");
        }

        IS_SAVED += userID;
        CITY_NAME += userID;
        IS_LOGGED_IN += userID;

        editText = view.findViewById(R.id.editText);
        saveButton = view.findViewById(R.id.save_button);

        loadData();

        return view;
    }

    private void saveData()
    {
        if(!editText.getText().toString().isEmpty())
        {
            String userCity = editText.getText().toString();

            String firstUpper = userCity.substring(0,1).toUpperCase();
            String toLower = userCity.substring(1).toLowerCase();

            userCity = firstUpper + toLower;

            SharedPrefs.saveData(getActivity(),CITY_NAME, userCity);
            SharedPrefs.saveData(getActivity(),IS_SAVED,"true");
            SharedPrefs.saveData(getActivity(),IS_LOGGED_IN, "true");

            UserActivity.setUserCity(userCity);

            Toast.makeText(getActivity(),"Data saved", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getActivity(),"City name can't be empty! ", Toast.LENGTH_SHORT).show();
        }

    }

    private void loadData()
    {
        String text = SharedPrefs.loadData(getActivity(), IS_SAVED);
        String userCity = SharedPrefs.loadData(getActivity(),CITY_NAME);
        String isLoggedIn = SharedPrefs.loadData(getActivity(),IS_LOGGED_IN);

        if(!Objects.requireNonNull(text).equals("true"))
        {
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
        else
        {
            Intent intent = new Intent(getActivity(), UserActivity.class);
            intent.putExtra("IS_LOGGED_IN", isLoggedIn);

            UserActivity.setUserId(userID);
            UserActivity.setUserCity(userCity);

            startActivity(intent);
            Objects.requireNonNull(getActivity()).finish();
        }
    }
}
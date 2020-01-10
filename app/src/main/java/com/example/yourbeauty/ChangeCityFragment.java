package com.example.yourbeauty;

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

import com.example.yourbeauty.LoggedUser.UserActivity;
import com.example.yourbeauty.UnregisteredUser.MainActivity;

public class ChangeCityFragment extends Fragment
{
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
        if(!editText.getText().toString().isEmpty())
        {
            SharedPrefs.saveData(getActivity(),CITY_NAME, editText.getText().toString());
            UserActivity.setUserCity(editText.getText().toString());
            MainActivity.setUserCity(editText.getText().toString());

            Toast.makeText(getActivity(),"Data saved", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getActivity(),"City name can't be empty! ", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadData()
    {
        userCity = SharedPrefs.loadData(getActivity(),CITY_NAME);

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
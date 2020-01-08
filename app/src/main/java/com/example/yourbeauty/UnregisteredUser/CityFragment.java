package com.example.yourbeauty.UnregisteredUser;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.yourbeauty.R;
import com.example.yourbeauty.ui.Menu.BarberFragment;
import com.example.yourbeauty.ui.Menu.BeautyFragment;
import com.example.yourbeauty.ui.Menu.DietFragment;
import com.example.yourbeauty.ui.Menu.HairFragment;
import com.example.yourbeauty.ui.Menu.MedicineFragment;
import com.example.yourbeauty.ui.Menu.NailsFragment;
import com.example.yourbeauty.ui.home.HomeFragment;

import java.util.Objects;

public class CityFragment extends Fragment
{
    private EditText editText;
    private Button saveButton;
    private String change = " ";

    private static final String SHARED_PREFS = "sharedPrefs";
    private String CITY_NAME = "city_name";
    private String IS_SAVED = "is_saved";

    public View onCreateView
            (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user_city, container, false);

        if(!change.equals("changed"))
        {
            change = Objects.requireNonNull(getArguments()).getString("CHANGE");
        }

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
            editor.putString(IS_SAVED, "true");

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
        String text = sharedPreferences.getString(IS_SAVED, "");
        String userCity = sharedPreferences.getString(CITY_NAME, "");

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
            changeFragment();
        }
    }

    private void changeFragment()
    {
        switch(change)
        {
            default:
                HomeFragment homeFragment = new HomeFragment();

                FragmentTransaction transaction =
                        Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
                transaction.replace(R.id.fragment_home, homeFragment);
                transaction.addToBackStack(null).commit();
                change = "changed";
                break;


            case "Hair":
                HairFragment hairFragment = new HairFragment();

                transaction =
                        Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
                transaction.replace(R.id.fragment_home, hairFragment);
                transaction.addToBackStack(null).commit();
                change = "changed";
                break;

            case "Barber":
                BarberFragment barberFragment = new BarberFragment();

                transaction =
                        Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
                transaction.replace(R.id.fragment_home, barberFragment);
                transaction.addToBackStack(null).commit();
                change = "changed";
                break;

            case "Beauty":
                BeautyFragment beautyFragment = new BeautyFragment();

                transaction =
                        Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
                transaction.replace(R.id.fragment_home, beautyFragment);
                transaction.addToBackStack(null).commit();
                change = "changed";
                break;

            case "Medic":
                MedicineFragment medicineFragment = new MedicineFragment();

                transaction =
                        Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
                transaction.replace(R.id.fragment_home, medicineFragment);
                transaction.addToBackStack(null).commit();
                change = "changed";
                break;

            case "Nails":
                NailsFragment nailsFragment = new NailsFragment();

                transaction =
                        Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
                transaction.replace(R.id.fragment_home, nailsFragment);
                transaction.addToBackStack(null).commit();
                change = "changed";
                break;

            case "Diet":
                DietFragment dietFragment = new DietFragment();

                transaction =
                        Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
                transaction.replace(R.id.fragment_home, dietFragment);
                transaction.addToBackStack(null).commit();
                change = "changed";
                break;
        }
    }
}

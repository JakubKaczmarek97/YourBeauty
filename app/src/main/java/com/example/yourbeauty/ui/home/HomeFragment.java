package com.example.yourbeauty.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.yourbeauty.LoggedUser.UserActivity;
import com.example.yourbeauty.R;
import com.example.yourbeauty.UnregisteredUser.CityFragment;
import com.example.yourbeauty.ui.Menu.BarberFragment;
import com.example.yourbeauty.ui.Menu.BeautyFragment;
import com.example.yourbeauty.ui.Menu.DietFragment;
import com.example.yourbeauty.ui.Menu.HairFragment;
import com.example.yourbeauty.ui.Menu.MedicineFragment;
import com.example.yourbeauty.ui.Menu.NailsFragment;

import java.util.Objects;

public class HomeFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView
            (@NonNull LayoutInflater inflater,@Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button btnHair = view.findViewById(R.id.btnWoman);
        Button btnBarber = view.findViewById(R.id.btnScissors);
        Button btnBeauty = view.findViewById(R.id.btnBeauty);
        Button btnMedic = view.findViewById(R.id.btnMedic);
        Button btnNails = view.findViewById(R.id.btnNails);
        Button btnDiet = view.findViewById(R.id.btnFruit);

        final String userID = UserActivity.getUserId();

        btnHair.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!userID.equals(""))
                {
                    HairFragment hairFragment = new HairFragment();

                    FragmentTransaction transaction =
                            Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
                    transaction.replace(R.id.fragment_home, hairFragment);
                    transaction.addToBackStack(null).commit();
                }
                else
                {
                    CityFragment cityFragment = new CityFragment();

                    Bundle args = new Bundle();
                    args.putString("CHANGE", "Hair");

                    cityFragment.setArguments(args);

                    FragmentTransaction transaction =
                            Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
                    transaction.replace(R.id.fragment_home, cityFragment);
                    transaction.addToBackStack(null).commit();
                }

            }
        });

        btnBarber.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!userID.equals(""))
                {
                    BarberFragment barberFragment = new BarberFragment();

                    FragmentTransaction transaction =
                            Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
                    transaction.replace(R.id.fragment_home, barberFragment);
                    transaction.addToBackStack(null).commit();
                }
                else
                {
                    CityFragment cityFragment = new CityFragment();

                    Bundle args = new Bundle();
                    args.putString("CHANGE", "Barber");

                    cityFragment.setArguments(args);

                    FragmentTransaction transaction =
                            Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
                    transaction.replace(R.id.fragment_home, cityFragment);
                    transaction.addToBackStack(null).commit();
                }
            }
        });

        btnBeauty.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!userID.equals(""))
                {
                    BeautyFragment beautyFragment = new BeautyFragment();

                    FragmentTransaction transaction =
                            Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
                    transaction.replace(R.id.fragment_home, beautyFragment);
                    transaction.addToBackStack(null).commit();
                }
                else
                {
                    CityFragment cityFragment = new CityFragment();

                    Bundle args = new Bundle();
                    args.putString("CHANGE", "Beauty");

                    cityFragment.setArguments(args);

                    FragmentTransaction transaction =
                            Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
                    transaction.replace(R.id.fragment_home, cityFragment);
                    transaction.addToBackStack(null).commit();
                }
            }
        });

        btnMedic.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!userID.equals(""))
                {
                    MedicineFragment medicineFragment = new MedicineFragment();

                    FragmentTransaction transaction =
                            Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
                    transaction.replace(R.id.fragment_home, medicineFragment);
                    transaction.addToBackStack(null).commit();
                }
                else
                {
                    CityFragment cityFragment = new CityFragment();

                    Bundle args = new Bundle();
                    args.putString("CHANGE", "Medic");

                    cityFragment.setArguments(args);

                    FragmentTransaction transaction =
                            Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
                    transaction.replace(R.id.fragment_home, cityFragment);
                    transaction.addToBackStack(null).commit();
                }

            }
        });

        btnNails.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!userID.equals(""))
                {
                    NailsFragment nailsFragment = new NailsFragment();

                    FragmentTransaction transaction =
                            Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
                    transaction.replace(R.id.fragment_home, nailsFragment);
                    transaction.addToBackStack(null).commit();
                }
                else
                {
                    CityFragment cityFragment = new CityFragment();

                    Bundle args = new Bundle();
                    args.putString("CHANGE", "Nails");

                    cityFragment.setArguments(args);

                    FragmentTransaction transaction =
                            Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
                    transaction.replace(R.id.fragment_home, cityFragment);
                    transaction.addToBackStack(null).commit();
                }
            }
        });

        btnDiet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!userID.equals(""))
                {
                    DietFragment dietFragment = new DietFragment();

                    FragmentTransaction transaction =
                            Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
                    transaction.replace(R.id.fragment_home, dietFragment);
                    transaction.addToBackStack(null).commit();
                }
                else
                {
                    CityFragment cityFragment = new CityFragment();

                    Bundle args = new Bundle();
                    args.putString("CHANGE", "Diet");

                    cityFragment.setArguments(args);

                    FragmentTransaction transaction =
                            Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
                    transaction.replace(R.id.fragment_home, cityFragment);
                    transaction.addToBackStack(null).commit();
                }
            }
        });

        return view;
    }
}
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

import com.example.yourbeauty.R;
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

        btnHair.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                HairFragment hairFragment = new HairFragment();

                FragmentTransaction transaction =
                        Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
                transaction.replace(R.id.fragment_home, hairFragment);
                transaction.addToBackStack(null).commit();
            }
        });

        btnBarber.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                BarberFragment barberFragment = new BarberFragment();

                FragmentTransaction transaction =
                        Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
                transaction.replace(R.id.fragment_home, barberFragment);
                transaction.addToBackStack(null).commit();
            }
        });

        btnBeauty.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                BeautyFragment beautyFragment = new BeautyFragment();

                FragmentTransaction transaction =
                        Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
                transaction.replace(R.id.fragment_home, beautyFragment);
                transaction.addToBackStack(null).commit();
            }
        });

        btnMedic.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MedicineFragment medicineFragment = new MedicineFragment();

                FragmentTransaction transaction =
                        Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
                transaction.replace(R.id.fragment_home, medicineFragment);
                transaction.addToBackStack(null).commit();
            }
        });

        btnNails.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                NailsFragment nailsFragment = new NailsFragment();

                FragmentTransaction transaction =
                        Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
                transaction.replace(R.id.fragment_home, nailsFragment);
                transaction.addToBackStack(null).commit();
            }
        });

        btnDiet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DietFragment dietFragment = new DietFragment();

                FragmentTransaction transaction =
                        Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
                transaction.replace(R.id.fragment_home, dietFragment);
                transaction.addToBackStack(null).commit();
            }
        });

        return view;
    }
}
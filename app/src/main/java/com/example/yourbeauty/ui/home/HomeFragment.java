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
import com.example.yourbeauty.ui.Menu.MenuFragment;

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
                changeFragment(0);
            }
        });

        btnBarber.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                changeFragment(1);
            }
        });

        btnBeauty.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                changeFragment(2);
            }
        });

        btnMedic.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                changeFragment(3);
            }
        });

        btnNails.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                changeFragment(4);
            }
        });

        btnDiet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                changeFragment(5);
            }
        });

        return view;
    }

    private void changeFragment(int pos)
    {
        String message = String.valueOf(pos);

        MenuFragment menuFragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString("YourKey", message);
        menuFragment.setArguments(args);

        FragmentTransaction transaction = Objects.requireNonNull(getActivity().getSupportFragmentManager()).beginTransaction();
        transaction.replace(R.id.fragment_home, menuFragment);
        transaction.addToBackStack(null).commit();
    }


}
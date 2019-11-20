package com.example.yourbeauty.ui.Menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.yourbeauty.FragmentAdapter;
import com.example.yourbeauty.R;

public class MenuFragment extends Fragment
{
    private ViewPager mViewPager;

    public View onCreateView
            (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        mViewPager = view.findViewById(R.id.fragment_container);
        setupViewPager(mViewPager);

        String value = getArguments().getString("YourKey");
        int position = Integer.parseInt(value);
        mViewPager.setCurrentItem(position);

        return view;
    }

    private void setupViewPager(ViewPager viewPager)
    {
        FragmentAdapter adapter = new FragmentAdapter(getChildFragmentManager());
        adapter.addFragment(new HairFragment(), "HairFragment");
        adapter.addFragment(new BarberFragment(), "BarberFragment");
        adapter.addFragment(new BeautyFragment(), "BeautyFragment");
        adapter.addFragment(new MedicineFragment(), "MedicineFragment");
        adapter.addFragment(new NailsFragment(), "NailsFragment");
        adapter.addFragment(new DietFragment(), "DietFragment");
        viewPager.setAdapter(adapter);
    }
}

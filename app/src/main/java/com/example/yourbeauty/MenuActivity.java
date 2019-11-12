package com.example.yourbeauty;

import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yourbeauty.ui.Menu.BarberFragment;
import com.example.yourbeauty.ui.Menu.BeautyFragment;
import com.example.yourbeauty.ui.Menu.DietFragment;
import com.example.yourbeauty.ui.Menu.HairFragment;
import com.example.yourbeauty.ui.Menu.MedicineFragment;
import com.example.yourbeauty.ui.Menu.NailsFragment;
import com.example.yourbeauty.ui.home.HomeFragment;

public class MenuActivity extends AppCompatActivity
{
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mViewPager = findViewById(R.id.fragment_container);
        setupViewPager(mViewPager);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            String value = extras.getString("PageNumber");
            int position = Integer.parseInt(value);
            mViewPager.setCurrentItem(position);
        }
    }

    private void setupViewPager(ViewPager viewPager)
    {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new HairFragment(), "HairFragment");
        adapter.addFragment(new BarberFragment(), "BarberFragment");
        adapter.addFragment(new BeautyFragment(), "BeautyFragment");
        adapter.addFragment(new MedicineFragment(), "MedicineFragment");
        adapter.addFragment(new NailsFragment(), "NailsFragment");
        adapter.addFragment(new DietFragment(), "DietFragment");
        viewPager.setAdapter(adapter);
    }
    public void setViewPager(int FragmentNumber)
    {
        mViewPager.setCurrentItem(FragmentNumber);
    }
}

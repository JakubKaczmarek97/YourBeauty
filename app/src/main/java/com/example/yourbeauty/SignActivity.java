package com.example.yourbeauty;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class SignActivity extends AppCompatActivity
{

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager=(ViewPager) findViewById(R.id.fragment_container);
        setupViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());

        adapter.addFragment(new SignFragment(), "signFragment");
        viewPager.setAdapter(adapter);
    }
    public void setViewPager(int FragmentNumber)
    {
        mViewPager.setCurrentItem(FragmentNumber);
    }
}

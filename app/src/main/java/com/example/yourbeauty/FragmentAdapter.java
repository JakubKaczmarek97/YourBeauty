package com.example.yourbeauty;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentStatePagerAdapter
{
    private final List<Fragment> mListaFragmentow = new ArrayList<>();
    private final List<String> mListaTytulowFrag = new ArrayList<>();

    public FragmentAdapter(FragmentManager fm)
    {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }
    public void addFragment(Fragment fragment, String title)
    {
        mListaFragmentow.add(fragment);
        mListaTytulowFrag.add(title);
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        return mListaFragmentow.get(position);
    }

    @Override
    public int getCount() {
        return mListaFragmentow.size();
    }
}
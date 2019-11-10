package com.example.yourbeauty.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.yourbeauty.MainActivity;
import com.example.yourbeauty.R;


public class HomeFragment extends Fragment
{
    private View rootView;
    private Button btnHair;


    @Nullable
    @Override
    public View onCreateView
            (@NonNull LayoutInflater inflater,@Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        btnHair = rootView.findViewById(R.id.btnWoman);
        btnHair.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ((MainActivity)getActivity()).setViewPager(0);
            }
        });

        return rootView;
    }


}
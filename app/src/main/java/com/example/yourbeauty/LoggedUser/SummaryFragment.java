package com.example.yourbeauty.LoggedUser;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.yourbeauty.R;

import java.util.Objects;

public class SummaryFragment extends Fragment
{
    private View view;
    private String firmData;
    private String serviceData;
    private String selectedHour;

    public View onCreateView
            (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_summary, container, false);
        firmData = Objects.requireNonNull(getArguments()).getString("FirmData");
        serviceData = Objects.requireNonNull(getArguments()).getString("ServiceData");
        selectedHour = Objects.requireNonNull(getArguments()).getString("SelectedHour");

        LinearLayout summary = view.findViewById(R.id.summary_linear);

        EditText editText = new EditText(getActivity());
        editText.setText(firmData + "\n\n" + serviceData + "\n\n" + selectedHour);
        editText.setTextColor(Color.rgb(255,255,255));
        editText.setEnabled(false);
        summary.addView(editText);


        //new HoursFragment.ListAllHours().execute();
        return view;
    }
}

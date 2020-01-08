package com.example.yourbeauty.LoggedUser;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.yourbeauty.R;

import java.util.Objects;

public class SummaryFragment extends Fragment
{
    private View view;
    private String firmData;
    private String serviceData;
    private String selectedHour;
    private String selectedDate;
    private String workerID;
    private String serviceID;
    private String clientID;
    private String payInAdvance = "N";

    public View onCreateView
            (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_summary, container, false);

        firmData = Objects.requireNonNull(getArguments()).getString("FirmData");
        serviceData = Objects.requireNonNull(getArguments()).getString("ServiceData");
        selectedHour = Objects.requireNonNull(getArguments()).getString("SelectedHour");
        selectedDate = Objects.requireNonNull(getArguments()).getString("DateVisit");
        workerID = Objects.requireNonNull(getArguments()).getString("WorkerID");
        serviceID = Objects.requireNonNull(getArguments()).getString("ServiceID");
        clientID = UserActivity.getUserId();

        LinearLayout summary = view.findViewById(R.id.summary_linear);

        EditText editText = new EditText(getActivity());
        editText.setText(firmData + "\n\n" + serviceData + "\n\n" + selectedDate + "\n" + selectedHour);
        editText.setTextColor(Color.rgb(255,255,255));
        editText.setEnabled(false);
        summary.addView(editText);

        Button btn_without_pay = view.findViewById(R.id.btn_Without_Pay);

        btn_without_pay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                makeOrder();
            }
        });

        return view;
    }

    private void makeOrder()
    {
        MakeOrderFragment makeOrderFragment = new MakeOrderFragment();

        Bundle args = new Bundle();
        args.putString("ServiceID", serviceID);
        args.putString("DateVisit", selectedDate);
        args.putString("HourVisit", selectedHour);
        args.putString("PayInAdvance", payInAdvance);
        args.putString("ID_Worker", workerID);
        args.putString("ID_Client", clientID);

        makeOrderFragment.setArguments(args);

        FragmentTransaction transaction = (Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
        transaction.replace(R.id.fragment_summary, makeOrderFragment);
        transaction.addToBackStack(null).commit();
    }
}

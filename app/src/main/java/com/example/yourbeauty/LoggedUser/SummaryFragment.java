package com.example.yourbeauty.LoggedUser;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.yourbeauty.PayPal.PayPalMainActivity;
import com.example.yourbeauty.R;

import java.util.Objects;

public class SummaryFragment extends Fragment
{
    private String selectedHour;
    private String selectedDate;
    private String workerID;
    private String serviceID;
    private String clientID;
    private String payInAdvance;
    private String servicePrice;

    public View onCreateView
            (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);

        String firmData = Objects.requireNonNull(getArguments()).getString("FirmData");
        String serviceData = Objects.requireNonNull(getArguments()).getString("ServiceData");
        selectedHour = Objects.requireNonNull(getArguments()).getString("SelectedHour");
        selectedDate = Objects.requireNonNull(getArguments()).getString("DateVisit");
        workerID = Objects.requireNonNull(getArguments()).getString("WorkerID");
        serviceID = Objects.requireNonNull(getArguments()).getString("ServiceID");
        clientID = UserActivity.getUserId();
        servicePrice = Objects.requireNonNull(getArguments()).getString("ServicePrice");

        LinearLayout summary = view.findViewById(R.id.summary_linear);

        Typeface typeface = ResourcesCompat.getFont(Objects.requireNonNull(getActivity()),R.font.oregano);

        EditText editText = new EditText(getActivity());
        String temp = firmData + "\n\n" + serviceData + "\n\n" + selectedDate + "\n" + selectedHour;
        editText.setText(temp);
        editText.setTextColor(Color.rgb(255,255,255));
        editText.setEnabled(false);
        editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        editText.setTypeface(typeface);
        editText.setTextSize(20);
        summary.addView(editText);

        Button btn_without_pay = view.findViewById(R.id.btn_Without_Pay);
        Button btn_with_pay = view.findViewById(R.id.btn_With_Pay);

        btn_without_pay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                payInAdvance = "N";
                makeOrder();
            }
        });

        btn_with_pay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivityForResult
                        (new Intent(getActivity(), PayPalMainActivity.class).putExtra("AMOUNT",servicePrice),808);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 808)
        {
            payInAdvance = "Y";
            makeOrder();
        }
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

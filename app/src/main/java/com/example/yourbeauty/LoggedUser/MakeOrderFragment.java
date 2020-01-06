package com.example.yourbeauty.LoggedUser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.yourbeauty.R;

import java.util.Objects;

public class MakeOrderFragment extends Fragment
{
    private View view;

    private String idService;
    private String dateVisit;
    private String hourVisit;
    private String payInAdvance;
    private String idWorker;
    private String idClient;

    public View onCreateView
            (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_make_order, container, false);

        idService = Objects.requireNonNull(getArguments()).getString("ServiceID");
        dateVisit = Objects.requireNonNull(getArguments()).getString("DateVisit");
        hourVisit = Objects.requireNonNull(getArguments()).getString("HourVisit");
        payInAdvance = Objects.requireNonNull(getArguments()).getString("PayInAdvance");
        idWorker = Objects.requireNonNull(getArguments()).getString("ID_Worker");
        idClient = Objects.requireNonNull(getArguments()).getString("ID_Client");

        System.out.println("Make Order: " + idService + " " + dateVisit + " " +
                hourVisit + " " + payInAdvance + " " + idWorker + " " + idClient);


        return view;
    }
}

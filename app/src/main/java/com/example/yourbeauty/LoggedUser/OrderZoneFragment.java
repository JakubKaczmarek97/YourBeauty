package com.example.yourbeauty.LoggedUser;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.yourbeauty.JsonParser;
import com.example.yourbeauty.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OrderZoneFragment extends Fragment
{
    private ProgressDialog pDialog;
    private View view;
    private String selectedService;

    private String currentDate;
    private String selectedDate;

    private String serviceTime;

    public View onCreateView
            (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        Date date = new Date();
        currentDate = formatter.format(date);
        selectedDate = currentDate;

        view = inflater.inflate(R.layout.fragment_order_zone, container, false);
        selectedService = Objects.requireNonNull(getArguments()).getString("ServiceID");
        String firmData = getArguments().getString("FirmData");
        serviceTime = Objects.requireNonNull(getArguments()).getString("Time");

        LinearLayout firms = view.findViewById(R.id.firm_data);

        EditText edit = new EditText(getActivity());
        edit.setText(firmData);
        edit.setTextColor(Color.rgb(255,255,255));
        edit.setEnabled(false);
        firms.addView(edit);

        new OrderZoneFragment.ListAllWorkers().execute();

        return view;
    }

    class ListAllWorkers extends AsyncTask<String, String, String>
    {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(getResources().getString(R.string.wait_workers));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings)
        {
            OkHttpClient client = new OkHttpClient();

            RequestBody postData = new FormBody.Builder()
                    .add("idService", selectedService)
                    .build();

            String url_order_zone = "http://10.0.2.2/bayb/order_zone.php";
            Request request = new Request.Builder()
                    .url(url_order_zone)
                    .post(postData)
                    .build();
            try
            {
                Response response = client.newCall(request).execute();
                String result = Objects.requireNonNull(response.body()).string();

                //Use of JsonParser class.

                JsonParser jsonParser = new JsonParser();
                final LinkedHashMap<String, String> parsedWorkers;
                parsedWorkers = jsonParser.parseWorkers(result);
                final Object[] keys = parsedWorkers.keySet().toArray();

                //Generate buttons dynamically based on JSON

                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        LinearLayout workers = view.findViewById(R.id.workers_list);

                        if(parsedWorkers.isEmpty())
                        {
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);

                            Button btn = new Button(getActivity());
                            btn.setText(R.string.no_workers); // No workers available
                            btn.setBackgroundColor(Color.rgb(255,100,100));
                            btn.setTextColor(Color.rgb(255,255,255));

                            params.setMargins(10, 10, 10, 3);
                            workers.addView(btn, params);

                            Button next = view.findViewById(R.id.btnNext);
                            next.setEnabled(false);
                            next.setTextColor(Color.rgb(150,150,150));
                        }
                        else {
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);

                            final RadioGroup radioGroup = view.findViewById(R.id.radio_group);

                            for (int i = 0; i < Objects.requireNonNull(keys).length; i += 3)
                            {
                                RadioButton rb = new RadioButton(getActivity());
                                String rbText = parsedWorkers.get(keys[i+1]) + " " + parsedWorkers.get(keys[i+2]);

                                rb.setText(rbText);
                                rb.setTextColor(Color.rgb(230,230,230));
                                rb.setTextSize(18);
                                rb.setChecked(true);
                                rb.setId(Integer.parseInt(Objects.requireNonNull(parsedWorkers.get(keys[i]))));

                                params.setMargins(5, 0, 5, 8);
                                radioGroup.addView(rb);
                            }


                            Button next = view.findViewById(R.id.btnNext);
                            next.setEnabled(true);

                            CalendarView calendarView = view.findViewById(R.id.calendarView);


                            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
                            {
                                @Override
                                public void onSelectedDayChange
                                        (@NonNull CalendarView calendarView, int selected_year, int selected_month, int selected_day)
                                {
                                    String dayOfMonth;
                                    String month;

                                    if(selected_day < 10)
                                    {
                                        dayOfMonth= "0" + selected_day;
                                    }
                                    else
                                    {
                                        dayOfMonth = "" + selected_day;
                                    }

                                    if(selected_month < 9)
                                    {
                                        month = "0" + (selected_month + 1);
                                    }
                                    else
                                    {
                                        month = "" + (selected_month + 1);
                                    }

                                    selectedDate = dayOfMonth + "." + month + "." + selected_year;
                                }
                            });

                            next.setOnClickListener(new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View v)
                                {
                                    System.out.println(currentDate + " " + radioGroup.getCheckedRadioButtonId());
                                    String ID = String.valueOf(radioGroup.getCheckedRadioButtonId());

                                    changeToHours(selectedDate,ID, serviceTime);
                                }
                            });

                        }
                    }
                });

            } catch (Exception e)
            {
                System.out.println("Error: " + e);
            }
            return null;
        }

        protected void onPostExecute(String result)
        {
            pDialog.dismiss();
        }
    }

    private void changeToHours(String dateVisit, String idWorker, String timeOfService)
    {
        HoursFragment hoursFragment = new HoursFragment();

        Bundle args = new Bundle();
        args.putString("DateVisit", dateVisit);
        args.putString("ID_Worker", idWorker);
        args.putString("TimeOfService", timeOfService);
        hoursFragment.setArguments(args);

        FragmentTransaction transaction = (Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
        transaction.replace(R.id.fragment_order_zone, hoursFragment);
        transaction.addToBackStack(null).commit();
    }
}
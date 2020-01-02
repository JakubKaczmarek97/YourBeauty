package com.example.yourbeauty.LoggedUser;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.yourbeauty.JsonParser;
import com.example.yourbeauty.R;

import java.util.LinkedHashMap;
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

    public View onCreateView
            (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_order_zone, container, false);
        selectedService = Objects.requireNonNull(getArguments()).getString("ServiceID");
        String firmData = getArguments().getString("FirmData");

        LinearLayout firms = view.findViewById(R.id.firm_data);

        EditText edit = new EditText(getActivity());
        edit.setText(firmData);
        edit.setTextColor(Color.rgb(255,255,255));
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
            pDialog.setMessage("Please wait to list all workers...");
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

                            for (int i = 0; i < Objects.requireNonNull(keys).length; i += 2)
                            {
                                RadioGroup radioGroup = view.findViewById(R.id.radio_group);

                                RadioButton rb = new RadioButton(getActivity());
                                rb.setText(parsedWorkers.get(keys[i]) + " " + parsedWorkers.get(keys[i+1]));
                                rb.setTextColor(Color.rgb(230,230,230));
                                rb.setTextSize(18);

                                params.setMargins(5, 0, 5, 8);
                                radioGroup.addView(rb);
                            }

                            Button next = view.findViewById(R.id.btnNext);
                            next.setEnabled(true);

                        }
                    }
                });

            } catch (Exception e)
            {
                System.out.println("Błąd: " + e);
            }
            return null;
        }

        protected void onPostExecute(String result)
        {
            pDialog.dismiss();
        }
    }
}
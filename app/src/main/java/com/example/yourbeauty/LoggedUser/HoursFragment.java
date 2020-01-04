package com.example.yourbeauty.LoggedUser;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class HoursFragment extends Fragment
{
    private ProgressDialog pDialog;
    private View view;
    private String timeOfService;
    private String dateVisit;
    private String workerID;

    public View onCreateView
            (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_hours, container, false);
        timeOfService = Objects.requireNonNull(getArguments()).getString("TimeOfService");
        dateVisit = Objects.requireNonNull(getArguments()).getString("DateVisit");
        workerID = Objects.requireNonNull(getArguments()).getString("ID_Worker");

        new HoursFragment.ListAllHours().execute();

        return view;
    }

    class ListAllHours extends AsyncTask<String, String, String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
             pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(getResources().getString(R.string.wait_hours));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings)
        {
            OkHttpClient client = new OkHttpClient();

            RequestBody postData = new FormBody.Builder()
                    .add("timeOfService", timeOfService)
                    .add("dateVisit", dateVisit)
                    .add("idWorker", workerID)
                    .build();

            String url_order_zone = "http://10.0.2.2/bayb/prepare_hour.php";
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
                final LinkedHashMap<String, String> parsedHours;
                parsedHours = jsonParser.parseHours(result);
                final Object[] keys = parsedHours.keySet().toArray();

                //Generate buttons dynamically based on JSON

                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        LinearLayout linear = view.findViewById(R.id.hours_linear);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT);

                        if(parsedHours.isEmpty())
                        {
                            Button btn = new Button(getActivity());
                            btn.setText(R.string.no_hours);
                            btn.setBackgroundResource(R.drawable.gradient_1);
                            btn.setTextColor(Color.rgb(255,255,255));

                            params.setMargins(10, 3, 10, 3);
                            linear.addView(btn, params);
                        }
                        else
                        {
                            final RadioGroup radioGroup = view.findViewById(R.id.radio_group_hours);

                            for (int i = 0; i < Objects.requireNonNull(keys).length; i++)
                            {
                                RadioButton rb = new RadioButton(getActivity());
                                String rbText = parsedHours.get(keys[i]);

                                rb.setText(rbText);
                                rb.setTextColor(Color.rgb(230,230,230));
                                rb.setTextSize(18);

                                if(i==0)
                                    rb.setChecked(true);

                                //rb.setId(Integer.parseInt(Objects.requireNonNull(parsedHours.get(keys[i]))));

                                params.setMargins(5, 0, 5, 8);
                                radioGroup.addView(rb);
                            }
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
}
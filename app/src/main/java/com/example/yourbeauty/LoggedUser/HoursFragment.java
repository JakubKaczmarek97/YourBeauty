package com.example.yourbeauty.LoggedUser;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
   // private String selectedService;

   // private String currentDate;

    public View onCreateView
            (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        view = inflater.inflate(R.layout.fragment_hours, container, false);
        //selectedService = Objects.requireNonNull(getArguments()).getString("ServiceID");
        //String firmData = getArguments().getString("FirmData");

       // LinearLayout firms = view.findViewById(R.id.firm_data);


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
                    .add("timeOfService", "todo")
                    .add("dateVisit", "todo")
                    .add("idWorker", "todo")
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
                final LinkedHashMap<String, String> parsedWorkers;
                parsedWorkers = jsonParser.parseWorkers(result);
                final Object[] keys = parsedWorkers.keySet().toArray();

                //Generate buttons dynamically based on JSON

                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
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
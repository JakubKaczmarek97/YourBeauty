package com.example.yourbeauty.LoggedUser;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

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
    private String firmData;

    public View onCreateView
            (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_order_zone, container, false);
        selectedService = getArguments().getString("ServiceID");
        firmData = getArguments().getString("FirmData");

        LinearLayout firms = view.findViewById(R.id.firm_data);

        EditText edit = new EditText(getActivity());
        edit.setText(firmData);

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
                    .add("idFirm", selectedService)
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
                final LinkedHashMap<String, String> parsedServices;
                parsedServices = jsonParser.parseWorkers(result);
                final Object[] keys = parsedServices.keySet().toArray();

                //Generate buttons dynamically based on JSON
/*
                getActivity().runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        LinearLayout linear = view.findViewById(R.id.workers_list);

                        if(parsedServices.isEmpty())
                        {
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);

                            Button btn = new Button(getActivity());
                            btn.setText("No workers found at database");
                            btn.setBackgroundResource(R.drawable.gradient_1);
                            btn.setTextColor(Color.rgb(255,255,255));

                            params.setMargins(10, 3, 10, 3);
                            linear.addView(btn, params);
                        }
                        else {

                            for (int i = 0; i < keys.length; i += 5)
                            {

                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT);

                                Button btn = new Button(getActivity());
                                EditText edit = new EditText(getActivity());

                                btn.setText(parsedServices.get(keys[i + 1]));

                                edit.setText(" " +parsedServices.get(keys[i + 2]) + "\n"
                                        + " Price: " + parsedServices.get(keys[i + 3]) + "\n"
                                        + " Time: " + parsedServices.get(keys[i + 4]));

                                btn.setBackgroundResource(R.drawable.gradient_1);
                                btn.setTextColor(Color.rgb(255,255,255));
                                edit.setBackgroundColor(Color.rgb(230,230,230));
                                edit.setEnabled(false);
                                edit.setTextColor(Color.rgb(0,0,0));

                                btn.setGravity(Gravity.CENTER);



                                btn.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        if(UserActivity.getUserId() == "")  //User is not logged in
                                        {
                                            Toast.makeText(getActivity(),
                                                    "You have to sign in first!", Toast.LENGTH_LONG).show();
                                        }
                                        else    //User is logged in
                                        {
                                            //TODO: Open Order Zone here

                                        }
                                    }
                                });

                                params.setMargins(5, 0, 5, 8);
                                linear.addView(btn);
                                linear.addView(edit,params);
                            }
                        }
                    }
                });
                */
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

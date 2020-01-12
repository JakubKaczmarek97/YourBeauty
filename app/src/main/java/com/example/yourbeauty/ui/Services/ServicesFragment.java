package com.example.yourbeauty.ui.Services;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.yourbeauty.JsonParser;
import com.example.yourbeauty.LoggedUser.OrderZoneFragment;
import com.example.yourbeauty.LoggedUser.UserActivity;
import com.example.yourbeauty.R;

import java.util.LinkedHashMap;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServicesFragment extends Fragment
{
    private ProgressDialog pDialog;
    private View view;

    private String selectedFirm;
    private String firmName;
    private String firmData;

    public View onCreateView
            (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_services, container, false);
        selectedFirm = Objects.requireNonNull(getArguments()).getString("FirmID");
        firmName = Objects.requireNonNull(getArguments()).getString("FirmName");
        firmData = Objects.requireNonNull(getArguments()).getString("FirmData");

        new ServicesFragment.ListAllServices().execute();

        return view;
    }

    class ListAllServices extends AsyncTask<String, String, String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(getResources().getString(R.string.wait_services));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings)
        {
            OkHttpClient client = new OkHttpClient();

            RequestBody postData = new FormBody.Builder()
                    .add("idFirm", selectedFirm)
                    .build();

            String url_services = "http://10.0.2.2/bayb/display_services_of_firm.php";
            Request request = new Request.Builder()
                    .url(url_services)
                    .post(postData)
                    .build();
            try
            {
                Response response = client.newCall(request).execute();
                String result = Objects.requireNonNull(response.body()).string();

                //Use of JsonParser class.

                JsonParser jsonParser = new JsonParser();
                final LinkedHashMap<String, String> parsedServices;
                parsedServices = jsonParser.parseServices(result);
                final Object[] keys = parsedServices.keySet().toArray();

                //Generate buttons dynamically based on JSON

                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        LinearLayout linear = view.findViewById(R.id.services_linear);

                        Typeface typeface = ResourcesCompat.getFont(getActivity(), R.font.oregano);

                        if(parsedServices.isEmpty())
                        {
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                                    (LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);

                            Button btn = new Button(getActivity());
                            btn.setText(R.string.no_services);
                            btn.setBackgroundResource(R.drawable.gradient_1);
                            btn.setTextColor(Color.rgb(255,255,255));
                            btn.setTypeface(typeface);

                            params.setMargins(10, 3, 10, 3);
                            linear.addView(btn, params);
                        }
                        else {

                            for (int i = 0; i < Objects.requireNonNull(keys).length; i += 5)
                            {
                                LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams
                                    (LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT);

                                LinearLayout.LayoutParams editParams = new LinearLayout.LayoutParams
                                        (LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT);

                                Button btn = new Button(getActivity());
                                EditText edit = new EditText(getActivity());

                                final String argument = parsedServices.get(keys[i]);
                                final String firmData = " " + parsedServices.get(keys[i + 2]) + "\n"
                                        + " " + getResources().getString(R.string.price)+ " " + parsedServices.get(keys[i + 3]) + " PLN \n"
                                        + " " + getResources().getString(R.string.time) + " " + parsedServices.get(keys[i + 4]) + " MIN";
                                final String serviceTime = parsedServices.get(keys[i + 4]);
                                final String serviceName = parsedServices.get(keys[i + 1]);
                                final String servicePrice = parsedServices.get(keys[i+3]);

                                btn.setText(serviceName);
                                btn.setBackgroundResource(R.drawable.gradient_buttons);
                                btn.setTextColor(Color.rgb(255,255,255));
                                btn.setGravity(Gravity.CENTER);
                                btn.setTypeface(typeface);

                                edit.setBackgroundColor(Color.rgb(230,230,230));
                                edit.setEnabled(false);
                                edit.setTextColor(Color.rgb(0,0,0));
                                edit.setText(firmData);
                                edit.setGravity(Gravity.CENTER);
                                edit.setTypeface(typeface);

                                btn.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        if(UserActivity.getUserId().equals(""))  //User is not logged in
                                        {
                                            Toast.makeText(getActivity(),
                                                    R.string.sign_in_first, Toast.LENGTH_LONG).show();
                                        }
                                        else    //User is logged in
                                        {
                                            changeFragment(argument, firmData, serviceTime, serviceName, servicePrice);
                                        }
                                    }
                                });

                                btnParams.setMargins(5, 0, 5, 0);
                                linear.addView(btn, btnParams);

                                editParams.setMargins(5, 0, 5, 10);
                                linear.addView(edit,editParams);
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

    private void changeFragment(String ID, String data, String time, String serviceName, String servicePrice)
    {
        OrderZoneFragment orderZoneFragment = new OrderZoneFragment();

        Bundle args = new Bundle();
        args.putString("ServiceID", ID);
        args.putString("ServiceData", data);
        args.putString("Time", time);
        args.putString("FirmName", firmName);
        args.putString("FirmData", firmData);
        args.putString("ServiceName", serviceName);
        args.putString("ServicePrice", servicePrice);
        orderZoneFragment.setArguments(args);

        FragmentTransaction transaction = (Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
        transaction.replace(R.id.fragment_services, orderZoneFragment);
        transaction.addToBackStack(null).commit();
    }
}
package com.example.yourbeauty.ui.Services;

import android.app.ProgressDialog;
import android.graphics.Color;
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

    public View onCreateView
            (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_services, container, false);
        selectedFirm = getArguments().getString("YourKey");

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
            pDialog.setMessage("Please wait to list all services...");
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

                getActivity().runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        LinearLayout linear = view.findViewById(R.id.services_linear);

                        if(parsedServices.isEmpty())
                        {
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);

                            Button btn = new Button(getActivity());
                            btn.setText("No services found at database");
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

                                final String argument = parsedServices.get(keys[i]);
                                final String firmData = " " +parsedServices.get(keys[i + 2]) + "\n"
                                        + " Price: " + parsedServices.get(keys[i + 3]) + "\n"
                                        + " Time: " + parsedServices.get(keys[i + 4]);

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
                                            changeFragment(argument, firmData);
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

    private void changeFragment(String message, String data)
    {
        OrderZoneFragment orderZoneFragment = new OrderZoneFragment();

        Bundle args = new Bundle();
        args.putString("ServiceID", message);
        args.putString("FirmData", data);
        orderZoneFragment.setArguments(args);

        FragmentTransaction transaction = (Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
        transaction.replace(R.id.fragment_services, orderZoneFragment);
        transaction.addToBackStack(null).commit();
    }
}

package com.example.yourbeauty.LoggedUser;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
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

public class MyOrdersFragment extends Fragment
{
    private ProgressDialog pDialog;
    private View view;

    public View onCreateView
            (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_my_orders, container, false);

        new MyOrdersFragment.ListAllOrders().execute();

        return view;
    }

    class ListAllOrders extends AsyncTask <String, String, String>
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
                    .add("id", UserActivity.getUserId())
                    .build();

            String url_visits = "http://10.0.2.2/bayb/display_user_visits.php";
            Request request = new Request.Builder()
                    .url(url_visits)
                    .post(postData)
                    .build();

            try
            {
                Response response = client.newCall(request).execute();
                String result = Objects.requireNonNull(response.body()).string();

                //Use of JsonParser class.

                JsonParser jsonParser = new JsonParser();
                final LinkedHashMap<String, String> parsedOrders;
                parsedOrders = jsonParser.parseOrders(result);
                final Object[] keys = parsedOrders.keySet().toArray();

                //Generate buttons dynamically based on JSON

                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        LinearLayout linear = view.findViewById(R.id.orders_linear);

                        Typeface typeface = ResourcesCompat.getFont(getActivity(), R.font.oregano);

                        if(parsedOrders.isEmpty())
                        {
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                                    (LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT);

                            Button btn = new Button(getActivity());
                            btn.setText("You don't have ordered visits");
                            btn.setBackgroundResource(R.drawable.gradient_buttons);
                            btn.setTextColor(Color.rgb(255,255,255));
                            btn.setTypeface(typeface);

                            params.setMargins(10, 3, 10, 3);
                            linear.addView(btn, params);
                        }
                        else {

                            for (int i = 0; i < Objects.requireNonNull(keys).length; i += 9)
                            {
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                                        (LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT);

                                EditText edit = new EditText(getActivity());
                                edit.setTypeface(typeface);

                                String orderData =
                                        "Service:  " + parsedOrders.get(keys[i]) + "\n"
                                        + "Worker:  "  + parsedOrders.get(keys[i+1]) + " "
                                        + parsedOrders.get(keys[i+2]) + "\n"
                                        + "Date:  " + parsedOrders.get(keys[i+3]) + "\n"
                                        + "Hour:  " + parsedOrders.get(keys[i+4]) + "\n"
                                        + "Firm Name:  " + parsedOrders.get(keys[i+5]) + "\n"
                                        + "Address:  " + parsedOrders.get(keys[i+6]) + " "
                                        + parsedOrders.get(keys[i+7]) + "\n";

                                if(Objects.requireNonNull(parsedOrders.get(keys[i + 8])).equals("Y"))
                                {
                                    orderData += "Payed in advance";
                                }
                                else
                                    orderData += "Not payed in advance";

                                if(i%2 == 0)
                                {
                                    edit.setBackgroundColor(Color.argb (160,185, 90, 226));
                                    edit.setTextColor(Color.rgb(255,255,255));
                                }
                                else
                                {
                                    edit.setBackgroundColor(Color.argb(200, 184, 236, 248));
                                    edit.setTextColor(Color.rgb(0,0,0));
                                }

                                edit.setEnabled(false);
                                edit.setText(orderData);
                                edit.setTextSize(24);
                                edit.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                                params.setMargins(16, 0, 16, 16);
                                linear.addView(edit,params);
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
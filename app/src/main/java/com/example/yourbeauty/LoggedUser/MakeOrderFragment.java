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
import androidx.fragment.app.FragmentTransaction;

import com.example.yourbeauty.R;
import com.example.yourbeauty.ui.home.HomeFragment;

import org.json.JSONObject;

import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MakeOrderFragment extends Fragment
{
    private View view;
    private ProgressDialog pDialog;

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

        new MakeOrderFragment.MakeOrder().execute();

        return view;
    }

    class MakeOrder extends AsyncTask<String,String,String>
    {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(getResources().getString(R.string.wait_make));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings)
        {
            OkHttpClient client = new OkHttpClient();

            RequestBody postData = new FormBody.Builder()
                    .add("idService", idService)
                    .add("dateVisit", dateVisit)
                    .add("hourVisit", hourVisit)
                    .add("payInAdvance", payInAdvance)
                    .add("idWorker", idWorker)
                    .add("idClient", idClient)
                    .build();

            String url_make_order = "http://10.0.2.2/bayb/order_visit.php";
            Request request = new Request.Builder()
                    .url(url_make_order)
                    .post(postData)
                    .build();

            try
            {
                Response response = client.newCall(request).execute();
                String result = Objects.requireNonNull(response.body()).string();

                JSONObject jsonObject = new JSONObject(result);
                final int validate = jsonObject.getInt("success");

                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        LinearLayout linear = view.findViewById(R.id.make_order_linear);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT);

                        Typeface typeface = ResourcesCompat.getFont(getActivity(), R.font.oregano);

                        EditText edit = new EditText(getActivity());
                        edit.setTextColor(Color.rgb(255,255,255));
                        edit.setEnabled(false);
                        edit.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        edit.setTypeface(typeface);

                        if(validate == 1)
                        {
                            edit.setText(R.string.thank_order);

                            Button homeButton = new Button(getActivity());
                            homeButton.setText(R.string.back_home);
                            homeButton.setBackgroundResource(R.drawable.gradient_1);
                            homeButton.setTextColor(Color.rgb(255,255,255));
                            homeButton.setTypeface(typeface);

                            homeButton.setOnClickListener(new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View v)
                                {
                                    HomeFragment homeFragment = new HomeFragment();

                                    FragmentTransaction transaction = (Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
                                    transaction.replace(R.id.fragment_summary, homeFragment);
                                    transaction.addToBackStack(null).commit();
                                }
                            });

                            linear.addView(edit,params);
                            linear.addView(homeButton, params);
                        }
                        else
                        {
                            edit.setText(R.string.order_error);
                            linear.addView(edit,params);
                        }

                    }
                });
            }
            catch (Exception e)
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
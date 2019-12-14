package com.example.yourbeauty.ui.Menu;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.yourbeauty.JsonParser;
import com.example.yourbeauty.R;
import com.example.yourbeauty.ui.Services.ServicesFragment;

import java.util.LinkedHashMap;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NailsFragment extends Fragment
{
    private ProgressDialog pDialogNails;
    private View view;

    public View onCreateView
            (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        view = inflater.inflate(R.layout.fragment_nails, container, false);

        new NailsFragment.ListAllNails().execute();
        return view;
    }
    class ListAllNails extends AsyncTask<String, String, String>
    {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialogNails = new ProgressDialog(getActivity());
            pDialogNails.setMessage("Please wait...");
            pDialogNails.setIndeterminate(false);
            pDialogNails.setCancelable(true);
            pDialogNails.show();
        }

        @Override
        protected String doInBackground(String... strings)
        {

            OkHttpClient client = new OkHttpClient();

            RequestBody postData = new FormBody.Builder()
                    .build();

            String url_nails = "http://10.0.2.2/bayb/display_category_nails.php";
            Request request = new Request.Builder()
                    .url(url_nails)
                    .post(postData)
                    .build();
            try
            {
                Response response = client.newCall(request).execute();
                String result = Objects.requireNonNull(response.body()).string();

                //Use of JsonParser class.

                JsonParser jsonParser = new JsonParser();
                final LinkedHashMap<String, String> parsedJson;
                parsedJson = jsonParser.parseJson(result);
                final Object[] keys = parsedJson.keySet().toArray();

                //Generate buttons dynamically based on JSON

                getActivity().runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        LinearLayout linear = view.findViewById(R.id.fragment_nails);

                        if(parsedJson.isEmpty()) {
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);

                            Button btn = new Button(getActivity());
                            btn.setText("No nails makers found at database");
                            btn.setBackgroundColor(Color.rgb(3, 136, 252));

                            params.setMargins(10, 3, 10, 3);
                            linear.addView(btn, params);
                        }
                        else {

                            for (int i = 0; i < keys.length; i += 4) {

                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT);

                                Button btn = new Button(getActivity());
                                btn.setId(i);
                                btn.setText(
                                        parsedJson.get(keys[i + 1]) + "\n"
                                                + parsedJson.get(keys[i + 2]) + "\n"
                                                + parsedJson.get(keys[i + 3]));
                                btn.setBackgroundColor(Color.rgb(3, 136, 252));

                                final String argument = parsedJson.get(keys[i]);

                                btn.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        changeFragment(argument);
                                    }
                                });

                                params.setMargins(10, 3, 10, 3);
                                linear.addView(btn, params);
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
            pDialogNails.dismiss();
        }
    }

    private void changeFragment(String message)
    {
        ServicesFragment servicesFragment = new ServicesFragment();

        Bundle args = new Bundle();
        args.putString("YourKey", message);
        servicesFragment.setArguments(args);

        FragmentTransaction transaction = Objects.requireNonNull(getFragmentManager()).beginTransaction();
        transaction.replace(R.id.nails_fragment, servicesFragment);
        transaction.addToBackStack(null).commit();
    }
}

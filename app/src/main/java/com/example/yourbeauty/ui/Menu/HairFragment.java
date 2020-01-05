package com.example.yourbeauty.ui.Menu;

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

public class HairFragment extends Fragment
{
    private ProgressDialog pDialogHair;
    private View view;

    public View onCreateView
            (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_hair, container, false);

        new HairFragment.ListAllHairdressers().execute();

        return view;
    }
    class ListAllHairdressers extends AsyncTask<String, String, String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialogHair = new ProgressDialog(getActivity());
            pDialogHair.setMessage(getResources().getString(R.string.wait));
            pDialogHair.setIndeterminate(false);
            pDialogHair.setCancelable(true);
            pDialogHair.show();
        }

        @Override
        protected String doInBackground(String... strings)
        {

            OkHttpClient client = new OkHttpClient();

            RequestBody postData = new FormBody.Builder()
                    .build();

            String url_hairdressers = "http://10.0.2.2/bayb/display_category_hairdressers.php";
            Request request = new Request.Builder()
                    .url(url_hairdressers)
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
                        LinearLayout linear = view.findViewById(R.id.fragment_hair);

                        if(parsedJson.isEmpty())
                        {
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                                    (LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);

                            Button btn = new Button(getActivity());
                            btn.setText(R.string.no_hairdressers);
                            btn.setBackgroundResource(R.drawable.gradient_1);
                            btn.setTextColor(Color.rgb(255,255,255));

                            params.setMargins(10, 3, 10, 3);
                            linear.addView(btn, params);
                        }
                        else {

                            for (int i = 0; i < keys.length; i += 4)
                            {
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                                        (LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT);

                                Button btn = new Button(getActivity());
                                EditText editText = new EditText(getActivity());

                                final String bText = parsedJson.get(keys[i + 1]);                   //Name of Firm
                                final String eText = " " + parsedJson.get(keys[i + 2]) + "\n"       //Address
                                        + parsedJson.get(keys[i + 3]);
                                final String argument = parsedJson.get(keys[i]);                    //Firm ID

                                btn.setText(bText);
                                btn.setBackgroundResource(R.drawable.gradient_1);
                                btn.setTextColor(Color.rgb(255,255,255));
                                btn.setGravity(Gravity.CENTER);

                                editText.setBackgroundColor(Color.rgb(230,230,230));
                                editText.setEnabled(false);
                                editText.setTextColor(Color.rgb(0,0,0));
                                editText.setText(eText);
                                editText.setGravity(Gravity.CENTER);

                                btn.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        changeFragment(argument, bText, eText);
                                    }
                                });

                                params.setMargins(5, 0, 5, 8);
                                linear.addView(btn);
                                linear.addView(editText,params);
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
            pDialogHair.dismiss();
        }
    }
    private void changeFragment(String ID, String name, String data)
    {
        ServicesFragment servicesFragment = new ServicesFragment();

        Bundle args = new Bundle();
        args.putString("FirmID", ID);
        args.putString("FirmName", name);
        args.putString("FirmData", data);
        servicesFragment.setArguments(args);

        FragmentTransaction transaction = (Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
        transaction.replace(R.id.hair_fragment, servicesFragment);
        transaction.addToBackStack(null).commit();
    }
}
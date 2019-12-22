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

public class DietFragment extends Fragment
{
    private ProgressDialog pDialogDiet;
    private View view;

    public View onCreateView
            (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        view = inflater.inflate(R.layout.fragment_diet, container, false);

        new DietFragment.ListAllDietetics().execute();
        return view;
    }
    class ListAllDietetics extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialogDiet = new ProgressDialog(getActivity());
            pDialogDiet.setMessage("Please wait...");
            pDialogDiet.setIndeterminate(false);
            pDialogDiet.setCancelable(true);
            pDialogDiet.show();
        }

        @Override
        protected String doInBackground(String... strings)
        {

            OkHttpClient client = new OkHttpClient();

            RequestBody postData = new FormBody.Builder()
                    .build();

            String url_diet = "http://10.0.2.2/bayb/display_category_dietetics.php";
            Request request = new Request.Builder()
                    .url(url_diet)
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
                        LinearLayout linear = view.findViewById(R.id.fragment_diet);

                        if(parsedJson.isEmpty()) {
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);

                            Button btn = new Button(getActivity());
                            btn.setText("No dietetics found at database");
                            btn.setBackgroundResource(R.drawable.gradient_1);
                            btn.setTextColor(Color.rgb(255,255,255));

                            params.setMargins(10, 3, 10, 3);
                            linear.addView(btn, params);
                        }
                        else {

                            for (int i = 0; i < keys.length; i += 4) {

                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT);

                                Button btn = new Button(getActivity());
                                EditText editText = new EditText(getActivity());

                                btn.setText(parsedJson.get(keys[i + 1]));

                                editText.setText(" " + parsedJson.get(keys[i + 2])
                                        + parsedJson.get(keys[i + 3]));

                                btn.setBackgroundResource(R.drawable.gradient_1);
                                btn.setTextColor(Color.rgb(255,255,255));
                                editText.setBackgroundColor(Color.rgb(230,230,230));
                                editText.setEnabled(false);
                                editText.setTextColor(Color.rgb(0,0,0));

                                btn.setGravity(Gravity.CENTER);
                                editText.setGravity(Gravity.CENTER);

                                final String argument = parsedJson.get(keys[i]);

                                btn.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        changeFragment(argument);
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
            pDialogDiet.dismiss();
        }
    }

    private void changeFragment(String message)
    {
        ServicesFragment servicesFragment = new ServicesFragment();

        Bundle args = new Bundle();
        args.putString("YourKey", message);
        servicesFragment.setArguments(args);

        FragmentTransaction transaction = Objects.requireNonNull(getFragmentManager()).beginTransaction();
        transaction.replace(R.id.diet_fragment, servicesFragment);
        transaction.addToBackStack(null).commit();
    }
}

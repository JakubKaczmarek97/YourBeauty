package com.example.yourbeauty.ui.Menu;

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

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.yourbeauty.JsonParser;
import com.example.yourbeauty.LoggedUser.UserActivity;
import com.example.yourbeauty.R;
import com.example.yourbeauty.SharedPrefs;
import com.example.yourbeauty.UnregisteredUser.MainActivity;
import com.example.yourbeauty.ui.Services.ServicesFragment;

import java.util.LinkedHashMap;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BeautyFragment extends Fragment
{
    private ProgressDialog pDialogBeauty;
    private View view;

    public View onCreateView
            (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        view = inflater.inflate(R.layout.fragment_beauty, container, false);

        new BeautyFragment.ListAllBeauticians().execute();

        return view;
    }

    class ListAllBeauticians extends AsyncTask<String, String, String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialogBeauty = new ProgressDialog(getActivity());
            pDialogBeauty.setMessage(getResources().getString(R.string.wait));
            pDialogBeauty.setIndeterminate(false);
            pDialogBeauty.setCancelable(true);
            pDialogBeauty.show();
        }

        @Override
        protected String doInBackground(String... strings)
        {
            String userID = UserActivity.getUserId();
            String isLogged = SharedPrefs.loadData(getActivity(), "is_logged_in" + userID);

            OkHttpClient client = new OkHttpClient();
            RequestBody postData;

            if(isLogged.equals("true"))
            {
                postData = new FormBody.Builder()
                        .add("city", UserActivity.getUserCity())
                        .build();
            }
            else
            {
                postData = new FormBody.Builder()
                        .add("city", MainActivity.getUserCity())
                        .build();
            }

            String url_beauticians = "http://10.0.2.2/bayb/display_category_beauticians.php";
            Request request = new Request.Builder()
                    .url(url_beauticians)
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

                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        LinearLayout linear = view.findViewById(R.id.fragment_beauty);

                        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT);

                        LinearLayout.LayoutParams editParams = new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT);

                        Typeface typeface = ResourcesCompat.getFont(getActivity(),R.font.oregano);

                        if(parsedJson.isEmpty())
                        {
                            Button btn = new Button(getActivity());
                            btn.setText(R.string.no_beauticians);
                            btn.setBackgroundResource(R.drawable.gradient_buttons);
                            btn.setTextColor(Color.rgb(255,255,255));
                            btn.setTypeface(typeface);

                            btnParams.setMargins(10, 3, 10, 3);
                            linear.addView(btn, btnParams);
                        }
                        else {
                            for (int i = 0; i < keys.length; i += 4)
                            {
                                Button btn = new Button(getActivity());
                                EditText editText = new EditText(getActivity());

                                final String bText = parsedJson.get(keys[i + 1]);                   //Name of Firm
                                final String eText = " " + parsedJson.get(keys[i + 2]) + "\n"       //Address
                                        + parsedJson.get(keys[i + 3]);
                                final String argument = parsedJson.get(keys[i]);                    //Firm ID

                                btn.setText(bText);
                                btn.setBackgroundResource(R.drawable.gradient_buttons);
                                btn.setTextColor(Color.rgb(255,255,255));
                                btn.setGravity(Gravity.CENTER);
                                btn.setTypeface(typeface);

                                editText.setBackgroundColor(Color.rgb(230,230,230));
                                editText.setEnabled(false);
                                editText.setTextColor(Color.rgb(0,0,0));
                                editText.setText(eText);
                                editText.setGravity(Gravity.CENTER);
                                editText.setTypeface(typeface);

                                btn.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        changeFragment(argument, bText, eText);
                                    }
                                });

                                btnParams.setMargins(5, 0, 5, 0);
                                linear.addView(btn,btnParams);

                                editParams.setMargins(5, 0, 5, 8);
                                linear.addView(editText,editParams);
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
            pDialogBeauty.dismiss();
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
        transaction.replace(R.id.beauty_fragment, servicesFragment);
        transaction.addToBackStack(null).commit();
    }
}
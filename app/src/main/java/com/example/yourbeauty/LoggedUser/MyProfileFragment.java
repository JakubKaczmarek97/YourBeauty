package com.example.yourbeauty.LoggedUser;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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

public class MyProfileFragment extends Fragment
{
    private ProgressDialog pDialog;

    private String userName;
    private String userSecondName;
    private String userSurname;
    private String userBirth;
    private String userGender;
    private String userMail;
    private String userAccountNumber;

    private EditText showName;
    private EditText showSecondName;
    private EditText showSurname;
    private EditText showBirth;
    private EditText showGender;
    private EditText showMail;
    private EditText showAccountNumber;

    public View onCreateView
            (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);

        showName = view.findViewById(R.id.showName);
        showSecondName = view.findViewById(R.id.showSecondName);
        showSurname = view.findViewById(R.id.showSurname);
        showBirth = view.findViewById(R.id.showBirth);
        showGender = view.findViewById(R.id.showGender);
        showMail = view.findViewById(R.id.showMail);
        showAccountNumber = view.findViewById(R.id.showNumber);

        new MyProfileFragment.ListUserProfile().execute();

        return view;
    }

    class ListUserProfile extends AsyncTask<String,String, String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(getResources().getString(R.string.wait_profile));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings)
        {
            String ID = UserActivity.getUserId();

            OkHttpClient client = new OkHttpClient();

            RequestBody postData = new FormBody.Builder()
                    .add("idUser", ID)
                    .build();

            String url_user = "http://10.0.2.2/bayb/user_profile.php";
            Request request = new Request.Builder()
                    .url(url_user)
                    .post(postData)
                    .build();

            try
            {
                Response response = client.newCall(request).execute();
                String result = Objects.requireNonNull(response.body()).string();

                //Use of JsonParser class.

                JsonParser jsonParser = new JsonParser();
                final LinkedHashMap<String, String> parsedUserProfile;
                parsedUserProfile = jsonParser.parseUserProfile(result);
                final Object[] keys = parsedUserProfile.keySet().toArray();

                userSurname = parsedUserProfile.get(keys[0]);
                userName = parsedUserProfile.get(keys[1]);
                userSecondName = parsedUserProfile.get(keys[2]);
                userBirth = parsedUserProfile.get(keys[3]);
                userGender = parsedUserProfile.get(keys[4]);
                userMail = parsedUserProfile.get(keys[5]);
                userAccountNumber = parsedUserProfile.get(keys[6]);

                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        showName.setText(userName);
                        showSecondName.setText(userSecondName);
                        showSurname.setText(userSurname);
                        showBirth.setText(userBirth);
                        showGender.setText(userGender);
                        showMail.setText(userMail);
                        showAccountNumber.setText(userAccountNumber);

                        showName.setTextColor(Color.rgb(255,255,255));
                        showSecondName.setTextColor(Color.rgb(255,255,255));
                        showSurname.setTextColor(Color.rgb(255,255,255));
                        showBirth.setTextColor(Color.rgb(255,255,255));
                        showGender.setTextColor(Color.rgb(255,255,255));
                        showMail.setTextColor(Color.rgb(255,255,255));
                        showAccountNumber.setTextColor(Color.rgb(255,255,255));


                        showName.setTextSize(24);
                        showSecondName.setTextSize(24);
                        showSurname.setTextSize(24);
                        showBirth.setTextSize(24);
                        showGender.setTextSize(24);
                        showMail.setTextSize(24);
                        showAccountNumber.setTextSize(24);



                        Typeface typeface = ResourcesCompat.getFont(getActivity(), R.font.oregano);

                        showName.setTypeface(typeface);
                        showSecondName.setTypeface(typeface);
                        showSurname.setTypeface(typeface);
                        showBirth.setTypeface(typeface);
                        showGender.setTypeface(typeface);
                        showMail.setTypeface(typeface);
                        showAccountNumber.setTypeface(typeface);
                    }
                });
            }
            catch (Exception e)
            {
                System.out.println("Error: " + e);
            }

            return null;
        }

        protected void onPostExecute(String s)
        {
            pDialog.dismiss();
        }
    }
}
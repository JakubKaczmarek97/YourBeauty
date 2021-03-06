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

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
    private View view;

    private String userName;
    private String userSecondName;
    private String userSurname;
    private String userMail;

    private EditText showName;
    private EditText showSecondName;
    private EditText showSurname;
    private EditText showMail;

    private Button btn;
    private String ID;

    public View onCreateView
            (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        ID = UserActivity.getUserId();

        showName = view.findViewById(R.id.showName);
        showSecondName = view.findViewById(R.id.showSecondName);
        showSurname = view.findViewById(R.id.showSurname);
        showMail = view.findViewById(R.id.showMail);

        btn = view.findViewById(R.id.profile_edit_button);

        Button pass = view.findViewById(R.id.profile_edit_password);

        pass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();

                FragmentTransaction transaction =
                        Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
                transaction.replace(R.id.fragment_my_profile, changePasswordFragment);
                transaction.addToBackStack(null).commit();
            }
        });

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

            String url_user = "http://yourbeauty.cba.pl/user_profile.php";
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
                userMail = parsedUserProfile.get(keys[3]);

                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        showName.setText(userName);
                        showSecondName.setText(userSecondName);
                        showSurname.setText(userSurname);
                        showMail.setText(userMail);

                        showName.setTextColor(Color.rgb(255,255,255));
                        showSecondName.setTextColor(Color.rgb(255,255,255));
                        showSurname.setTextColor(Color.rgb(255,255,255));
                        showMail.setTextColor(Color.rgb(255,255,255));

                        showName.setTextSize(24);
                        showSecondName.setTextSize(24);
                        showSurname.setTextSize(24);
                        showMail.setTextSize(24);

                        Typeface typeface = ResourcesCompat.getFont(getActivity(), R.font.oregano);

                        showName.setTypeface(typeface);
                        showSecondName.setTypeface(typeface);
                        showSurname.setTypeface(typeface);
                        showMail.setTypeface(typeface);

                        btn.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                showName.setEnabled(true);
                                showSecondName.setEnabled(true);
                                showSurname.setEnabled(true);
                                showMail.setEnabled(true);

                                btn.setText("Update");

                                btn.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View view)
                                    {
                                        showName.setEnabled(false);
                                        showSecondName.setEnabled(false);
                                        showSurname.setEnabled(false);
                                        showMail.setEnabled(false);

                                        new MyProfileFragment.UpdateUserProfile().execute();

                                        MyProfileFragment myProfileFragment = new MyProfileFragment();

                                        FragmentTransaction transaction =
                                                Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager()).beginTransaction();
                                        transaction.replace(R.id.fragment_my_profile, myProfileFragment);
                                        transaction.addToBackStack(null).commit();
                                    }
                                });
                            }
                        });
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

    class UpdateUserProfile extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait to update...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings)
        {
            String Name = showName.getText().toString();
            String SecondName = showSecondName.getText().toString();
            String Surname = showSurname.getText().toString();
            String Mail = showMail.getText().toString();

            String ID = UserActivity.getUserId();

            OkHttpClient client = new OkHttpClient();

            RequestBody postData = new FormBody.Builder()
                    .add("id", ID)
                    .add("surname", Surname)
                    .add("name", Name)
                    .add("name2", SecondName)
                    .add("email", Mail)
                    .build();

            String url_update = "http://yourbeauty.cba.pl/update_user_profile.php";
            Request request = new Request.Builder()
                    .url(url_update)
                    .post(postData)
                    .build();

            try
            {
                client.newCall(request).execute();
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
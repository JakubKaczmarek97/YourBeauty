package com.example.yourbeauty.ui.SignIn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.yourbeauty.JsonParser;
import com.example.yourbeauty.LoggedUser.UserActivity;
import com.example.yourbeauty.R;

import java.util.LinkedHashMap;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class SignInFragment extends Fragment
{
    private ProgressDialog pDialog;

    private EditText inputEmail;
    private EditText inputPassword;

    private LinkedHashMap<String, String> parsedJson = new LinkedHashMap<>();

    private String userId;

    public View onCreateView
            (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);

        inputEmail = view.findViewById(R.id.plainEmail);
        inputPassword = view.findViewById(R.id.plainPass);

        Button signIn = view.findViewById(R.id.btnSignIn);

        signIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new SignInFragment.UserLogin().execute();
            }
        });

        return view;
    }

    class UserLogin extends AsyncTask<String, String, String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait to Sign In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings)
        {

            String Email = inputEmail.getText().toString();
            String Password = inputPassword.getText().toString();

            OkHttpClient client = new OkHttpClient();

            RequestBody postData = new FormBody.Builder()
                    .add("login", Email)
                    .add("password", Password)
                    .build();

            String url_create_product = "http://10.0.2.2/bayb/login.php";
            Request request = new Request.Builder()
                    .url(url_create_product)
                    .post(postData)
                    .build();
            try
            {
                Response response = client.newCall(request).execute();
                String result = Objects.requireNonNull(response.body()).string();

                JsonParser jsonParser = new JsonParser();


                parsedJson = jsonParser.parseLogin(result);
                final Object[] keys = parsedJson.keySet().toArray();
                userId = parsedJson.get(Objects.requireNonNull(keys)[0]);
            }
            catch (Exception e)
            {
                System.out.println("Błąd: " + e);
            }
            return null;
        }
        protected void onPostExecute(String result)
        {
            pDialog.dismiss();

            if(parsedJson.isEmpty())
            {
                Toast.makeText(getActivity(),
                        "Bad e-mail or password!", Toast.LENGTH_LONG).show();
            }
            else
            {
                Intent intent = new Intent(getActivity(), UserActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
                Objects.requireNonNull(getActivity()).finish();
            }
        }
    }
}
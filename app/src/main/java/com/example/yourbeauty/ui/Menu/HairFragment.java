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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.yourbeauty.JsonParser;
import com.example.yourbeauty.R;
import com.example.yourbeauty.ui.SignUp.SignUpFragment;

import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HairFragment extends Fragment
{
    private ProgressDialog pDialog;

    public View onCreateView
            (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_hair, container, false);
        /*
        LinearLayout linear = view.findViewById(R.id.fragment_hair);

        for (int i = 1; i <= 20; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            Button btn = new Button(getActivity());
            btn.setId(i);
            final int id_ = btn.getId();
            btn.setText("button " + id_);
            btn.setBackgroundColor(Color.rgb(70, 80, 90));
            linear.addView(btn, params);

        }
         */

        new HairFragment.ListAllHairdressers().execute();
        return view;
    }
    class ListAllHairdressers extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait to list all hairdressers...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings)
        {
            OkHttpClient client = new OkHttpClient();

            RequestBody postData = new FormBody.Builder()
                    .build();

            String url_create_product = "http://10.0.2.2/bayb/display_category_hairdressers.php";
            Request request = new Request.Builder()
                    .url(url_create_product)
                    .post(postData)
                    .build();
            try
            {
                Response response = client.newCall(request).execute();
                String result = Objects.requireNonNull(response.body()).string();
                JsonParser jsonParser = new JsonParser();
                String[] parts = jsonParser.splitString(result);
                System.out.println("Response:" + "Part1: " + parts[0] + "Part2: " + parts[1]);
            } catch (Exception e)
            {
                System.out.println("Błąd: " + e);
            }
            return null;
        }

        protected void onPostExecute(String result)
        {
            pDialog.dismiss();
            Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                public void run() { }
            });
        }
    }
}



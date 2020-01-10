package com.example.yourbeauty.LoggedUser;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.yourbeauty.R;

import org.json.JSONObject;

import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChangePasswordFragment extends Fragment
{
    private ProgressDialog pDialog;
    private View view;

    private EditText plainOldPass;
    private EditText plainNewPass;
    private EditText plainConfirm;

    public View onCreateView
            (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_change_password, container, false);

        plainOldPass = view.findViewById(R.id.plainOldPass);
        plainNewPass = view.findViewById(R.id.plainNewPass);
        plainConfirm = view.findViewById(R.id.plainConfirm);

        Button change = view.findViewById(R.id.pass_change_button);

        change.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new ChangePasswordFragment.changePassword().execute();
            }
        });

        return view;
    }

    class changePassword extends AsyncTask<String,String,String>
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
            String oldPassword = plainOldPass.getText().toString();
            String newPassword = plainNewPass.getText().toString();
            String confirmPassword = plainConfirm.getText().toString();

            System.out.println("OLD: " + oldPassword);
            System.out.println("NEW: " + newPassword);
            System.out.println("CONFIRM: " + confirmPassword);

            String ID = UserActivity.getUserId();

            System.out.println("ID: " + ID);

            OkHttpClient client = new OkHttpClient();

            RequestBody postData = new FormBody.Builder()
                    .add("id", ID)
                    .add("password_old", oldPassword)
                    .add("password_new", newPassword)
                    .add("password_new2", confirmPassword)
                    .build();

            String url_pass = "http://10.0.2.2/bayb/update_password.php";
            Request request = new Request.Builder()
                    .url(url_pass)
                    .post(postData)
                    .build();

            try
            {
                Response response = client.newCall(request).execute();
                String result = Objects.requireNonNull(response.body()).string();

                System.out.println("RESULT: " + result);

                int validate;
                String message;

                JSONObject jsonObject = new JSONObject(result);
                validate = jsonObject.getInt("success");
                message = jsonObject.getString("message");

                System.out.println("SUCCESS: " + validate + " Message: " + message);

            }
            catch (Exception e) {
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

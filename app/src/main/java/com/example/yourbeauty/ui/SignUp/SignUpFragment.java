package com.example.yourbeauty.ui.SignUp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.yourbeauty.R;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpFragment extends Fragment
{
    int SUCCESS ;

    private ProgressDialog pDialog;

    EditText inputName;
    EditText inputSecondName;
    EditText inputSurname;
    EditText inputdateOfBirth;
    RadioButton inputgenderWoman;
    RadioButton inputgenderMan;
    EditText inputEmail;
    EditText inputPassword;
    EditText inputAccountNumber;
    Button signUp;

    private static String url_create_product = "http://10.0.2.2/bayb/add_new_user.php";

    public View onCreateView
            (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        //Intent intent = new Intent(getActivity(), SignUpActivity.class);
        //startActivity(intent);

        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        inputName = view.findViewById(R.id.plainName);
        inputSecondName = view.findViewById(R.id.plainSecondName);
        inputSurname = view.findViewById(R.id.plainSurname);
        inputdateOfBirth = view.findViewById(R.id.plainDateOfBirth);
        inputgenderWoman = view.findViewById(R.id.buttonWoman);
        inputgenderMan = view.findViewById(R.id.buttonMan);
        inputEmail = view.findViewById(R.id.plainMail);
        inputPassword = view.findViewById(R.id.plainPassword);
        inputAccountNumber = view.findViewById(R.id.plainBankAccountNumber);

        signUp = view.findViewById(R.id.buttonSignUp);

        signUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // creating new user in background thread
                new SignUpFragment.CreateNewUser().execute();
            }
        });

        return view;
    }
    class CreateNewUser extends AsyncTask<String, String, String>
    {


        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait to sign up...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings)
        {

            String name = inputName.getText().toString();
            String secondName = inputSecondName.getText().toString();
            String surname = inputSurname.getText().toString();
            String dateOfBirth = inputdateOfBirth.getText().toString();
            String gender = "M" ;

            if(inputgenderWoman.isSelected())
            {
                gender ="W";
            }
            String Email = inputEmail.getText().toString();
            String Password = inputPassword.getText().toString();
            String BankAccountNumber = inputAccountNumber.getText().toString();
/*
            ///////////////////
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put("name", name);
            httpParams.put("name2", secondName);
            httpParams.put("surname", surname);
            httpParams.put("date_of_birth", dateOfBirth);
            httpParams.put("gender", gender);
            httpParams.put("account_type", "C");
            httpParams.put("email", Email);
            httpParams.put("password", Password);
            httpParams.put("bank_account_number", BankAccountNumber);


            httpJsonParser.makeHttpRequest(
                    url_create_product, "POST", httpParams);
            try {
                SUCCESS = jsonObject.getInt(KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
            OkHttpClient client = new OkHttpClient();

            RequestBody postData = new FormBody.Builder()
                    .add("name", name)
                    .add("name2", secondName)
                    .add("surname", surname)
                    .add("date_of_birth", dateOfBirth)
                    .add("gender", gender)
                    .add("account_type", "C")
                    .add("email", Email)
                    .add("password", Password)
                    .add("bank_account_number", BankAccountNumber)
                    .build();

            Request request = new Request.Builder()
                    .url(url_create_product)
                    .post(postData)
                    .build();
            try
            {
                Response response = client.newCall(request).execute();
                String result = response.body().string();
                System.out.println("Response:" + result);
                SUCCESS = 1;
                //return result;
            }
            catch (Exception e)
            {
                SUCCESS = 0;
                System.out.println("Błąd: " + e);
            }
            return null;
        }
        protected void onPostExecute(String result)
        {
            pDialog.dismiss();
            getActivity().runOnUiThread(new Runnable()
            {
                public void run()
                {
                    if (SUCCESS == 1)
                    {
                        //Display success message
                        Toast.makeText(getActivity(),
                                "User Added", Toast.LENGTH_LONG).show();

                        //Come back to SignUpFragment after registration
                        getFragmentManager().popBackStackImmediate();
                    }
                    else
                    {
                        Toast.makeText(getActivity(),
                                "Some error occurred while adding new user",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}
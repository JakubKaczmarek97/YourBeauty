package com.example.yourbeauty;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends Activity {

    private static final String KEY_SUCCESS = "success";
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

    private static String url_create_product = "localhost/bayb/add_new_user.php";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_signup);


        inputName = (EditText) findViewById(R.id.plainName);
        inputSecondName = (EditText) findViewById(R.id.plainSecondName);
        inputSurname = (EditText) findViewById(R.id.plainSurname);
        inputdateOfBirth = (EditText) findViewById(R.id.plainDateOfBirth);
        inputgenderWoman = (RadioButton) findViewById(R.id.buttonWoman);
        inputgenderMan = (RadioButton) findViewById(R.id.buttonMan);
        inputEmail = (EditText) findViewById(R.id.plainMail);
        inputPassword = (EditText) findViewById(R.id.plainPassword);
        inputAccountNumber = (EditText) findViewById(R.id.plainBankAccountNumber);

        signUp = (Button) findViewById(R.id.buttonSignUp);

        signUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // creating new user in background thread
               // new CreateNewUser().execute();
            }
        });

    }
    class CreateNewUser extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SignUpActivity.this);
            pDialog.setMessage("Please wait to sign up...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

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

            ///////////////////
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put("name", name);
            httpParams.put("name2", secondName);
            httpParams.put("surname", surname);
            httpParams.put("dateOfBirth", dateOfBirth);
            httpParams.put("gender", gender);
            httpParams.put("userType", "C");
            httpParams.put("email", Email);
            httpParams.put("password", Password);
            httpParams.put("bankAccountNumber", BankAccountNumber);


            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    url_create_product + "add_new_user.php", "POST", httpParams);
            try {
                SUCCESS = jsonObject.getInt(KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }
        protected void onPostExecute(String result) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    if (SUCCESS == 1) {
                        //Display success message
                        Toast.makeText(SignUpActivity.this,
                                "User Added", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(SignUpActivity.this,
                                "Some error occurred while adding new user",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}

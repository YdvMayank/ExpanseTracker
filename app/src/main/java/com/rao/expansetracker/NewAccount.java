package com.rao.expansetracker;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.rao.expansetracker.api.APIs;
import com.rao.expansetracker.data.VolleySingleton;


import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class NewAccount extends AppCompatActivity {

    private Button createAccount;
    private EditText userName;
    private String user_PhoneNo, userDob;
    private RadioGroup rgGender;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_account);

        SharedPreferences preferences = getSharedPreferences("LogIn", MODE_PRIVATE);
        user_PhoneNo = preferences.getString("mobilenumber", "");


        this.showDatePickerDialog();


        userName = (EditText) findViewById(R.id.ed_name);
        rgGender = (RadioGroup) findViewById(R.id.radioGender);

        createAccount = (Button) findViewById(R.id.btn_createAcc);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savingData();
            }
        });



    }

    private void savingData(){

        final String username = userName.getText().toString().trim();
        final String gender = ((RadioButton) findViewById(rgGender.getCheckedRadioButtonId())).getText().toString();
        //first we will do the validations

        if (TextUtils.isEmpty(username)) {
            userName.setError("Please enter username");
            userName.requestFocus();
            return;
        }

        StringRequest request = new StringRequest(Request.Method.POST, APIs.URL_createAccount,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(NewAccount.this, response.toString(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(NewAccount.this, DashboardActivity.class));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewAccount.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                Log.e("error", "error occured");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("gender", gender);
                params.put("name", username);
                params.put("dob", userDob);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                String creds = String.format("%s", "mobilenumber");
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }


    private void showDatePickerDialog()
    {
        Button datePickerDialogButton = (Button)findViewById(R.id.datePickerDialogButton);
        datePickerDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        StringBuffer strBuf = new StringBuffer();
                        //strBuf.append("You select date is ");
                        strBuf.append(year);
                        strBuf.append("-");
                        strBuf.append(month+1);
                        strBuf.append("-");
                        strBuf.append(dayOfMonth);

                        TextView datePickerValueTextView = (TextView)findViewById(R.id.datePickerValue);
                        datePickerValueTextView.setText(strBuf.toString());
                        userDob = strBuf.toString();
                    }
                };

                // Get current year, month and day.
                Calendar now = Calendar.getInstance();
                int year = now.get(java.util.Calendar.YEAR);
                int month = now.get(java.util.Calendar.MONTH);
                int day = now.get(java.util.Calendar.DAY_OF_MONTH);

                // Create the new DatePickerDialog instance.
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewAccount.this, android.R.style.Theme_Holo_Dialog, onDateSetListener, year, month, day);

                // Set dialog icon and title.
                //datePickerDialog.setIcon(R.drawable.if_snowman);
                datePickerDialog.setTitle("Please select date.");

                // Popup the dialog.
                datePickerDialog.show();
            }
        });
    }

}

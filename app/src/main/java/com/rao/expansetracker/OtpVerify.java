package com.rao.expansetracker;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.rao.expansetracker.api.APIs;
import com.rao.expansetracker.data.VolleySingleton;

import java.util.HashMap;
import java.util.Map;


public class OtpVerify extends AppCompatActivity {

    private Button verify;
    private EditText otp;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String phoneno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_verify);
        sharedPreferences=getSharedPreferences("LogIn",MODE_PRIVATE);
        boolean loggedin=sharedPreferences.getBoolean("loggedin",false);
        if (loggedin){
            startActivity(new Intent(OtpVerify.this,NewAccount.class));
        }

        otp = (EditText)findViewById(R.id.ed_Otp);
        Button verify = (Button) findViewById(R.id.btn_Verify);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerifyOtp();



            }
        });
    }

    private void VerifyOtp(){
        sharedPreferences=getSharedPreferences("LogIn",MODE_PRIVATE);
        phoneno=sharedPreferences.getString("mobilenumber","");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIs.URL_verifyotp,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(OtpVerify.this, response, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OtpVerify.this, NewAccount.class));
                        editor=sharedPreferences.edit();
                        editor.putBoolean("loggedin",true);
                        editor.commit();
                        editor.apply();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mobile_number", phoneno);
                params.put("otp", otp.getText().toString());

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

}

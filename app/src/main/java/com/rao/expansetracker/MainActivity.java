package com.rao.expansetracker;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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


public class MainActivity extends AppCompatActivity  {
    private EditText phone;
    private Button submit;
    String code;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phone = (EditText) findViewById(R.id.ed_phone);
        submit = (Button) findViewById(R.id.btn_GetOtp);
        sharedPreferences=getSharedPreferences("LogIn",MODE_PRIVATE);
        boolean logged=sharedPreferences.getBoolean("isLogIn",false);
        if (logged){
            startActivity(new Intent(MainActivity.this,OtpVerify.class));
        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Getting OTp", Toast.LENGTH_SHORT).show();
                sendOTP();
            }
        });

    }
    private void sendOTP(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIs.URL_sendotp,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,OtpVerify.class));
                        sharedPreferences=getSharedPreferences("LogIn",MODE_PRIVATE);
                        editor=sharedPreferences.edit();
                        editor.putBoolean("isLogIn",true);
                        editor.putString("mobilenumber",phone.getText().toString());
                        editor.commit();
                        editor.apply();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error occured", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mobile_number", phone.getText().toString());

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

}

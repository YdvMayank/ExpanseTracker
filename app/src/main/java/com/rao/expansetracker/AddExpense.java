package com.rao.expansetracker;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.rao.expansetracker.api.APIs;
import com.rao.expansetracker.data.DataBaseHandler;
import com.rao.expansetracker.data.VolleySingleton;
import com.rao.expansetracker.model.Expense;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AddExpense extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public String Category[] = {"","Food", "Travel", "Shopping"};
    public String Day[] = {"","Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    public String Month[] = {"","January","February", "March", "April", "May", "June", "July", "August", "September", "October", "November" ,"December"};
    private String category, day, month;

    private EditText amount;
    private Spinner spn_Category, spn_Day, spn_Month;
    private Button Save;
    public static String json_amt, json_Category, json_Day, json_Month, sp_PhoneNo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expense);

        SharedPreferences preferences = getSharedPreferences("LogIn", MODE_PRIVATE);
        sp_PhoneNo = preferences.getString("mobilenumber","");

        amount = (EditText) findViewById(R.id.ed_Amount);
        Save = (Button) findViewById(R.id.btn_Save);

        spn_Category = (Spinner) findViewById(R.id.spinner_Category);
        spn_Category.setOnItemSelectedListener(AddExpense.this);

        spn_Day = (Spinner) findViewById(R.id.spinnerDay);
        spn_Day.setOnItemSelectedListener(this);

        spn_Month = (Spinner) findViewById(R.id.spinnerMonth);
        spn_Month.setOnItemSelectedListener(this);

        ArrayAdapter catAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Category);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_Category.setAdapter(catAdapter);

        ArrayAdapter dayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Day);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_Day.setAdapter(dayAdapter);

        ArrayAdapter monthAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Month);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_Month.setAdapter(monthAdapter);


        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(AddExpense.this, DashboardActivity.class));
//                overridePendingTransition(0,0);
//                finish();
                if(!amount.getText().toString().isEmpty() && !category.isEmpty()
                       && !day.isEmpty() && !month.isEmpty()){
                    Toast.makeText(AddExpense.this, "saving data", Toast.LENGTH_SHORT).show();
                    saveExpenseToDb(v);
                }
            }
        });



    }

    private void saveExpenseToDb(View v) {

//        Expense expense = new Expense();
//
        final String newAmount = amount.getText().toString();
//
//        expense.setAmount(newAmount);
//        expense.setCategory(category);
//        expense.setDay(day);
//        expense.setMonth(month);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIs.URL_addExpense, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(AddExpense.this, response.toString(), Toast.LENGTH_SHORT).show();
                parseData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddExpense.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("amount", newAmount);
                params.put("category", category);
                params.put("day", day);
                params.put("month", month);

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

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void parseData(String response) {
        try{
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")){

                JSONArray dataArray = jsonObject.getJSONArray("data");
                for (int i=0; i<dataArray.length(); i++){
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    json_amt = dataobj.getString("amount");
                    json_Category = dataobj.getString("category");
                    json_Day = dataobj.getString("day");
                    json_Month = dataobj.getString("month");

                }

            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spin = (Spinner)parent;
        Spinner spin2 = (Spinner)parent;
        Spinner spin3 = (Spinner)parent;

        if(spin.getId() == R.id.spinner_Category)
        {
            category=Category[position];
            Toast.makeText(this, category.toString(), Toast.LENGTH_SHORT).show();
        }
        else if(spin2.getId() == R.id.spinnerDay)
        {
            day=Day[position];
            Toast.makeText(this, day.toString(), Toast.LENGTH_SHORT).show();
        }

        else if (spin3.getId() == R.id.spinnerMonth)
        {
            month=Month[position];
            Toast.makeText(this, month.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

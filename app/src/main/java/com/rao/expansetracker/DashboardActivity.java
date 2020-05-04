package com.rao.expansetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.rao.expansetracker.data.DataBaseHandler;
import com.rao.expansetracker.model.Expense;
import com.rao.expansetracker.ui.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private AlertDialog.Builder ab_Builder;
    private AlertDialog alertDialog;

    private ImageButton filters, newExpense;
    private RecyclerView rView;
    private List<Expense> expenseList;
    private List<Expense> listExp;
    private DataBaseHandler db;
    private RecyclerViewAdapter recyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        db = new DataBaseHandler(this);

        getSupportActionBar().setTitle("Welcome Harsh");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        rView = (RecyclerView) findViewById(R.id.recyclerView);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(new LinearLayoutManager(this));

        expenseList =  new ArrayList<>();
        listExp = new ArrayList<>();

        //expenseList = db.getAllExpense();
        for(Expense i : expenseList){
            Expense expense= new Expense();
            expense.setAmount(i.getAmount());
            expense.setCategory(i.getCategory());
            expense.setDay(i.getDay());
            expense.setMonth(i.getMonth());

            listExp.add(expense);
        }

        recyclerViewAdapter = new RecyclerViewAdapter(this, listExp);
        rView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();


        filters = (ImageButton) findViewById(R.id.filters);
        newExpense = (ImageButton) findViewById(R.id.addNewExpense);

       filters.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(DashboardActivity.this, Filters.class));


           }
       });

       newExpense.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(DashboardActivity.this, AddExpense.class));
           }
       });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                startActivity(new Intent(DashboardActivity.this, NewAccount.class));
                overridePendingTransition(0, 0);
                return true;
            case R.id.category:
                startActivity(new Intent(DashboardActivity.this, AddCategory.class));
                overridePendingTransition(0, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

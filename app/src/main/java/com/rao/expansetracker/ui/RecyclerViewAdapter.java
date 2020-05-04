package com.rao.expansetracker.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rao.expansetracker.R;
import com.rao.expansetracker.model.Expense;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Expense> expenseList;

    public RecyclerViewAdapter(Context context, List<Expense> expenseList) {
        this.context = context;
        this.expenseList = expenseList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row , parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

        Expense expense = expenseList.get(position);

        holder.amount.setText(expense.getAmount());
        holder.category.setText(expense.getCategory());
        holder.day.setText(expense.getDay());
        holder.month.setText(expense.getMonth());


    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView amount, category, day, month;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            amount = itemView.findViewById(R.id.tv_amount);
            category = itemView.findViewById(R.id.tv_Category);
            day = itemView.findViewById(R.id.tv_Day);
            month = itemView.findViewById(R.id.tv_Month);
        }
    }
}

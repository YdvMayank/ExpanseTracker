package com.rao.expansetracker.data;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.rao.expansetracker.model.Expense;
import com.rao.expansetracker.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {


    private Context ctx;
    public DataBaseHandler(Context context){
        super(context, Constants.TABLE_NAME, null, Constants.DB_Version);
        this.ctx = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EXPENSE_TABLE = "CREATE TABLE " +Constants.TABLE_NAME + "("
                + Constants.KEY_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," + Constants.KEY_EXPENSE_AMOUNT + "TEXT,"
                + Constants.KEY_CATEGORY + "TEXT,"
                + Constants.KEY_DAY + "TEXT,"
                + Constants.KEY_MONTH + "TEXT);";

        db.execSQL(CREATE_EXPENSE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addExpense(Expense expense){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Constants.KEY_EXPENSE_AMOUNT, expense.getAmount());
        cv.put(Constants.KEY_CATEGORY, expense.getCategory());
        cv.put(Constants.KEY_DAY, expense.getDay());
        cv.put(Constants.KEY_MONTH, expense.getMonth());

        //Insert all the values in a row
        db.insert(Constants.TABLE_NAME, null, cv);

    }

    private Expense getExpesne(int id){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[] { Constants.KEY_ID,
                Constants.KEY_EXPENSE_AMOUNT, Constants.KEY_CATEGORY, Constants.KEY_DAY, Constants.KEY_MONTH},
                Constants.KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();

            Expense expense = new Expense();
            expense.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
            expense.setAmount(cursor.getString(cursor.getColumnIndex(Constants.KEY_EXPENSE_AMOUNT)));
            expense.setDay(cursor.getString(cursor.getColumnIndex(Constants.KEY_DAY)));
            expense.setMonth(cursor.getString(cursor.getColumnIndex(Constants.KEY_MONTH)));

        return expense;
    }

    public List<Expense> getAllExpense(){

        SQLiteDatabase db = this.getReadableDatabase();
        List<Expense> expenseList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[] {Constants.KEY_ID,
                Constants.KEY_EXPENSE_AMOUNT, Constants.KEY_CATEGORY, Constants.KEY_DAY, Constants.KEY_MONTH},null,null, null, null, Constants.KEY_ID + "DESC");

        if(cursor.moveToFirst())
            do{
                Expense expense = new Expense();
                expense.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                expense.setAmount(cursor.getString(cursor.getColumnIndex(Constants.KEY_EXPENSE_AMOUNT)));
                expense.setDay(cursor.getString(cursor.getColumnIndex(Constants.KEY_DAY)));
                expense.setMonth(cursor.getString(cursor.getColumnIndex(Constants.KEY_MONTH)));

                expenseList.add(expense);
            }while (cursor.moveToNext());

            return expenseList;

    }

    public int updateExpense(Expense expense){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Constants.KEY_EXPENSE_AMOUNT, expense.getAmount());
        cv.put(Constants.KEY_CATEGORY, expense.getCategory());
        cv.put(Constants.KEY_DAY, expense.getDay());
        cv.put(Constants.KEY_MONTH, expense.getMonth());

        return db.update(Constants.TABLE_NAME, cv, Constants.KEY_ID + "=?", new String[] {String.valueOf(expense.getId())});
    }

    public int getExpensecount(Expense expense){
        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }


}

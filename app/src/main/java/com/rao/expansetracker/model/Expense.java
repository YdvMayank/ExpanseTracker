package com.rao.expansetracker.model;

public class Expense {

    int Id;
    String Amount;
    String Category;
    String Day;
    String Month;

    public Expense(int Id, String amount, String category, String day, String month) {
        Id = Id;
        Amount = amount;
        Category = category;
        Day = day;
        Month = month;
    }

    public Expense(){

    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        Id = Id;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }
}

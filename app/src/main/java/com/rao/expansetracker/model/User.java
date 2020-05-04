package com.rao.expansetracker.model;

public class User {

    String Name;
    String Gender;
    int Phone_no;
    String DOB;

    public User(String name, String gender, int phone_no, String DOB) {
        Name = name;
        Gender = gender;
        Phone_no = phone_no;
        this.DOB = DOB;
    }

    public User() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public int getPhone_no() {
        return Phone_no;
    }

    public void setPhone_no(int phone_no) {
        Phone_no = phone_no;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }
}

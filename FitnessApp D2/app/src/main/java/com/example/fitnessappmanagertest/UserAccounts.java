package com.example.fitnessappmanagertest;

import java.util.ArrayList;

public class UserAccounts {

    private String fname, lname, username, pwd, accountType;
    private ArrayList<GymClass> classes;
    public UserAccounts(String fname, String lname, String username, String pwd, String accountType) {
        this.fname = fname;
        this.lname = lname;
        this.username = username;
        this.pwd = pwd;
        this.accountType = accountType;
        this.classes = new ArrayList<>();
    }

    public String getAccountType() {
        return accountType;
    }
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getFname() {
        return fname;
    }
    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }
    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public ArrayList<GymClass> getClasses(){
        return classes;
    }
    public void addClass(GymClass newClass){
        classes.add(newClass);
    }
    public void removeClass(GymClass newClass){
        if(classes.contains(newClass)) {
            classes.remove(newClass);
        }else{
            System.out.println("Class does not exist");
        }
    }

    @Override
    public String toString() {
        return "UserAccounts{" +
                "fname: '" + fname + '\'' +
                ", lname: '" + lname + '\'' +
                ", username: '" + username + '\'' +
                ", pwd: '" + pwd + '\'' +
                '}';
    }
}

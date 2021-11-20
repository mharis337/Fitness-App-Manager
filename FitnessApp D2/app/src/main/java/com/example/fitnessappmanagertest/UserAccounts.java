package com.example.fitnessappmanagertest;

public class UserAccounts {

    private String fname, lname, username, pwd, accountType;

    public UserAccounts(String fname, String lname, String username, String pwd, String accountType) {
        this.fname = fname;
        this.lname = lname;
        this.username = username;
        this.pwd = pwd;
        this.accountType = accountType;
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

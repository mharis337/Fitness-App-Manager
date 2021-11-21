package com.example.fitnessappmanagertest;

import org.junit.Test;
import static org.junit.Assert.*;

public class UserAccounts_Test {

    @Test
    public void getAccountType_test() {
        UserAccounts user = new UserAccounts("Claudia", "Santa", "Clau",
                "abc123%", "member");
        assertEquals("member", user.getAccountType());
        user.setAccountType("instructor");
        assertEquals("instructor", user.getAccountType());
    }

    @Test
    public void getFname_test() {
        UserAccounts user = new UserAccounts("Claudia", "Santa", "Clau",
                "abc123%", "member");
        assertEquals("Claudia", user.getFname());
        user.setFname("Jackson");
        assertEquals("Jackson", user.getFname());
    }

    @Test
    public void getLname_test() {
        UserAccounts user = new UserAccounts("Claudia", "Santa", "Clau",
                "abc123%", "member");
        assertEquals("Santa", user.getLname());
        user.setLname("Dia");
        assertEquals("Dia", user.getLname());
    }

    @Test
    public void getUsername_test() {
        UserAccounts user = new UserAccounts("Claudia", "Santa", "Clau",
                "abc123%", "member");
        assertEquals("Clau", user.getUsername());
        user.setUsername("Boo");
        assertEquals("Boo", user.getUsername());
    }

    @Test
    public void getPwd_test() {
        UserAccounts user = new UserAccounts("Claudia", "Santa", "Clau",
                "abc123%", "member");
        assertEquals("abc123%", user.getPwd());
        user.setPwd("abc456%");
        assertEquals("abc456%", user.getPwd());
    }

}

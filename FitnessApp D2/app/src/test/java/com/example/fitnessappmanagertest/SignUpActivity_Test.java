package com.example.fitnessappmanagertest;

import org.junit.Test;
import static org.junit.Assert.*;

public class SignUpActivity_Test {

    @Test
    public void ValidatePass_test() {
        // Test the correct password format is True
        assertTrue(SignUpActivity.ValidatePass("abc123;"));
        assertTrue(SignUpActivity.ValidatePass("123%"));
        assertTrue(SignUpActivity.ValidatePass("abc%"));
        assertTrue(SignUpActivity.ValidatePass("&12abhg"));
        assertTrue(SignUpActivity.ValidatePass("12%abh"));
        assertTrue(SignUpActivity.ValidatePass("12a%"));
        assertTrue(SignUpActivity.ValidatePass("03345uyh&12"));

        // Test that password is false if it has no special characters
        assertFalse(SignUpActivity.ValidatePass("abc"));
        assertFalse(SignUpActivity.ValidatePass("123"));

        // Test that password is false if it is less than 3 characters
        assertFalse(SignUpActivity.ValidatePass("1%"));
        assertFalse(SignUpActivity.ValidatePass("$h"));
        assertFalse(SignUpActivity.ValidatePass("$ha"));
    }

    @Test
    public void validateName_test() {
        // Test the correct name format is True
        assertTrue(SignUpActivity.validateName("John"));
        assertTrue(SignUpActivity.validateName("Joh"));
        assertTrue(SignUpActivity.validateName("Mmmmmmmmmmm"));
        assertTrue(SignUpActivity.validateName("Claudia"));
        assertTrue(SignUpActivity.validateName("Kiril"));
        assertTrue(SignUpActivity.validateName("JoJo"));

        // Test that the name is false if first letter is not upper case
        assertFalse(SignUpActivity.validateName("john"));
        assertFalse(SignUpActivity.validateName("joohee"));

        // Test that the name is false if it contains number or special characters
        assertFalse(SignUpActivity.validateName("John1"));
        assertFalse(SignUpActivity.validateName("1John"));
        assertFalse(SignUpActivity.validateName("Jo2hn"));
        assertFalse(SignUpActivity.validateName("Joh*n"));
        assertFalse(SignUpActivity.validateName("%John"));
        assertFalse(SignUpActivity.validateName("John$"));
        assertFalse(SignUpActivity.validateName("john$"));

        // Test that the name is false if it is less than 3 letters
        assertFalse(SignUpActivity.validateName("Jo"));
        assertFalse(SignUpActivity.validateName("Jo1"));
        assertFalse(SignUpActivity.validateName("jo"));
    }

        @Test
        public void validateUsername_test() {
            // Test the correct username format is True
            assertTrue(SignUpActivity.validateUsername("John"));
            assertTrue(SignUpActivity.validateUsername("J11oh"));
            assertTrue(SignUpActivity.validateUsername("Mmmmm23mmmmmm"));
            assertTrue(SignUpActivity.validateUsername("ClaUDia"));
            assertTrue(SignUpActivity.validateUsername("KiriL999"));
            assertTrue(SignUpActivity.validateUsername("JoJo531"));
            assertTrue(SignUpActivity.validateUsername("J1234"));
            assertTrue(SignUpActivity.validateUsername("J12"));
            assertTrue(SignUpActivity.validateUsername("Mo1"));

            // Test that the username is false if first letter is not upper case
            assertFalse(SignUpActivity.validateUsername("john12"));
            assertFalse(SignUpActivity.validateUsername("32Joohee"));
            assertFalse(SignUpActivity.validateUsername("32joohee"));

            // Test that the username is false if it contains special characters
            assertFalse(SignUpActivity.validateUsername("Joh*n"));
            assertFalse(SignUpActivity.validateUsername("%John"));
            assertFalse(SignUpActivity.validateUsername("John$"));
            assertFalse(SignUpActivity.validateUsername("john$"));

            // Test that the username is false if it is less than 3 characters
            assertFalse(SignUpActivity.validateUsername("Jo"));
            assertFalse(SignUpActivity.validateUsername("J1"));
        }
}


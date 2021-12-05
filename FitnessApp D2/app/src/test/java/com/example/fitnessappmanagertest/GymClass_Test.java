package com.example.fitnessappmanagertest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GymClass_Test {

    @Test
    public void getDesc_test() {
        GymClass gymClass = new GymClass("yoga","Ruben","hard","meditation","Monday","15","10");
        assertEquals("meditation", gymClass.getDesc());
        gymClass.setDesc("Workout");
        assertEquals("Workout", gymClass.getDesc());

    }
    @Test
    public void getHours_test() {
        GymClass gymClass = new GymClass("yoga","Ruben","hard","meditation","Monday","15","10");
        assertEquals("15", gymClass.getHours());
        gymClass.setHours("12");
        assertEquals("12", gymClass.getHours());

    }
    @Test
    public void getInstructor_test() {
        GymClass gymClass = new GymClass("yoga","Ruben","hard","meditation","Monday","15","10");
        assertEquals("Ruben", gymClass.getInstructor());
        gymClass.setInstructor("Brooklyn");
        assertEquals("Brooklyn", gymClass.getInstructor());
    }
    @Test
    public void getDifficulty_test() {
        GymClass gymClass = new GymClass("yoga","Ruben","hard","meditation","Monday","15","10");
        assertEquals("hard", gymClass.getDifficulty());
        gymClass.setDifficulty("Medium");
        assertEquals("Medium", gymClass.getDifficulty());
    }
    @Test
    public void getName_test() {
        GymClass gymClass = new GymClass("yoga","Ruben","hard","meditation","Monday","15","10");
        assertEquals("yoga", gymClass.getName());
        gymClass.setName("Leg Day");
        assertEquals("Leg Day", gymClass.getName());
    }




}

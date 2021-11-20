package com.example.fitnessappmanagertest;

import java.util.concurrent.ThreadLocalRandom;

public class gymClass {
    private String difficulty, name, day, hours, instructor, desc, capacity;
    private int classID;

    public gymClass(String name, String desc){
        this.name = name;
        this.desc = desc;

        this.day = "----";
        this.hours = "----";
        this.instructor = "----";
        this.difficulty = "----";
        this.capacity = "0";
        this.classID = 0;
    }
    public gymClass(String name, String instructor, String difficulty, String desc, String day, String hours, String capacity) {
        this.difficulty = difficulty;
        this.name = name;
        this.day = day;
        this.hours = hours;
        this.capacity = capacity;
        this.instructor = instructor;
        this.desc = desc;
        this.classID = 0;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getHours(){
        return hours;
    }
    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    @Override
    public String toString() {
        return "gymClass{" +
                "difficulty='" + difficulty + '\'' +
                ", name='" + name + '\'' +
                ", day='" + day + '\'' +
                ", hours='" + hours + '\'' +
                ", instructor='" + instructor + '\'' +
                ", desc='" + desc + '\'' +
                ", capacity='" + capacity + '\'' +
                ", classID=" + classID +
                '}';
    }
}

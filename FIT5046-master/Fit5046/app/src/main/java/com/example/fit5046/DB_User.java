package com.example.fit5046;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class DB_User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "surname")
    private String surname;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "height")
    private int height;

    @ColumnInfo(name = "weight")
    private int weight;

    @ColumnInfo(name = "gender")
    private String gender;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "postcode")
    private int postcode;

    @ColumnInfo(name = "loa")
    private int loa;

    @ColumnInfo(name = "step_per_mile")
    private int step_per_mile;

    @ColumnInfo(name = "dob")
    private String dob;


    public DB_User(int id, String name, String surname, String email, int height, int weight, String gender, String address, int postcode, int loa, int step_per_mile, String dob) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.address = address;
        this.postcode = postcode;
        this.loa = loa;
        this.step_per_mile = step_per_mile;
        this.dob = dob;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public int getLoa() {
        return loa;
    }

    public void setLoa(int loa) {
        this.loa = loa;
    }

    public int getStep_per_mile() {
        return step_per_mile;
    }

    public void setStep_per_mile(int step_per_mile) {
        this.step_per_mile = step_per_mile;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}

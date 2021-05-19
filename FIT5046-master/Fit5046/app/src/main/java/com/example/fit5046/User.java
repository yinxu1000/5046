package com.example.fit5046;

public class User {
    private int id;
    private String name;
    private String surname;
    private String email;
    private int height;
    private int weight;
    private String gender;
    private String address;
    private int postcode;
    private int loa;
    private int step_per_mile;
    private String dob;

    public User(int id, String name, String surname, String email, int height, int weight, String gender, String address, int postcode, int loa, int step_per_mile, String dob) {
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
}

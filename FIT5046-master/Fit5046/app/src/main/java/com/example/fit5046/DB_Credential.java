package com.example.fit5046;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Date;

public class DB_Credential {
    @PrimaryKey(autoGenerate = true)
    private int user_id;

    @ColumnInfo(name = "username")
    private String username="";

    @ColumnInfo(name = "passwordhas")
    private String passwordhash;

    @ColumnInfo(name = "sign_up_date")
    private Date sign_up_date=null;

    public DB_Credential(int user_id, String username, String passwordhash, Date sign_up_date) {
        this.user_id = user_id;
        this.username = username;
        this.passwordhash = passwordhash;
        this.sign_up_date = sign_up_date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordhash() {
        return passwordhash;
    }

    public void setPasswordhash(String passwordhash) {
        this.passwordhash = passwordhash;
    }

    public Date getSign_up_date() {
        return sign_up_date;
    }

    public void setSign_up_date(Date sign_up_date) {
        this.sign_up_date = sign_up_date;
    }
}

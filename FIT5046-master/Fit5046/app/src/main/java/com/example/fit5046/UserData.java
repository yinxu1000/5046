package com.example.fit5046;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class UserData {
    @PrimaryKey(autoGenerate = true)
    public int dailyStepId;

    @ColumnInfo(name = "steps")
    public int steps;

    @ColumnInfo(name = "time")
    public String time;

    public UserData(int steps, String time) {
        this.steps = steps;
        this.time = time;
    }

    public int getDailyStepId() {
        return dailyStepId;
    }

    public void setDailyStepId(int dailyStepId) {
        this.dailyStepId = dailyStepId;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

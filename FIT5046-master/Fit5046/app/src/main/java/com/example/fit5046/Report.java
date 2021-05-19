package com.example.fit5046;

import java.util.Date;

public class Report {
    private Date reportdate;
    private int totalcalconsume;
    private int totalcalburned;
    private int totalsteps;
    private int calgoal;
    private User uid;
    public Report(Date reportdate, int totalcalconsume, int totalcalburned, int totalsteps, int calgoal, User user) {
        this.reportdate = reportdate;
        this.totalcalconsume = totalcalconsume;
        this.totalcalburned = totalcalburned;
        this.totalsteps = totalsteps;
        this.calgoal = calgoal;
        this.uid = user;
    }

    public Date getReportdate() {
        return reportdate;
    }

    public int getCalgoal() {
        return calgoal;
    }

    public int getTotalcalconsume() {
        return totalcalconsume;
    }

    public int getTotalsteps() {
        return totalsteps;
    }

    public int getTotalcalburned() {
        return totalcalburned;
    }
}

package ars.ramsey.interviewhelper.model.bean;

/**
 * Created by Ramsey on 2017/4/26.
 */

public class TodoTask {
    private int id;
    private String companyName;
    private int year;
    private int month;
    private int day;

    public TodoTask(int id, String companyName, int year, int month, int day) {
        this.id = id;
        this.companyName = companyName;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}

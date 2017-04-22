package ars.ramsey.interviewhelper.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Ramsey on 2017/4/7.
 */

public class Task implements Parcelable {
    private int id;
    private String companyName;
    private String jobName;
    private String status;
    private String createDate;
    private String nextDate;
    private String finishedDate;
    private boolean offer;
    private String address;
    private boolean completed;

    public Task(){
        companyName = "";
        jobName = "";
        status = "";
        createDate = "";
        nextDate = "";
        finishedDate = "";
        offer = false;
        address = "";
        completed = false;
    }

    public Task(String companyName,String jobName)
    {
        this.companyName = companyName;
        this.jobName = jobName;
        this.status = "";
        this.createDate = "";
        this.nextDate = null;
        this.finishedDate = null;
        this.offer = false;
    }

    public Task(int id,String companyName,String jobName,String status,String createDate,String nextDate,String finishedDate,boolean offer,String address)
    {
        this.id = id;
        this.companyName = companyName;
        this.jobName = jobName;
        this.status = status;
        this.createDate = createDate;
        this.nextDate = nextDate;
        this.finishedDate = finishedDate;
        this.offer = offer;
        this.address = address;
    }

    //This constructor is used by task list to show something important.
    public Task(int id, String companyName, String jobName, String status, String nextDate, String address) {
        this.id = id;
        this.companyName = companyName;
        this.jobName = jobName;
        this.status = status;
        this.nextDate = nextDate;
        this.address = address;
        this.completed = false;
        this.offer = false;
    }

    protected Task(Parcel in) {
        id = in.readInt();
        companyName = in.readString();
        jobName = in.readString();
        status = in.readString();
        createDate = in.readString();
        nextDate = in.readString();
        finishedDate = in.readString();
        offer = in.readByte() != 0;
        address = in.readString();
        completed = in.readByte() != 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.companyName);
        dest.writeString(this.jobName);
        dest.writeString(this.status);
        dest.writeString(this.createDate);
        dest.writeString(this.nextDate);
        dest.writeString(this.finishedDate);
        dest.writeInt(this.offer?1:0);
        dest.writeString(this.address);
        dest.writeInt(this.completed?1:0);
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getNextDate() {
        return nextDate;
    }

    public void setNextDate(String nextDate) {
        this.nextDate = nextDate;
    }

    public String getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(String finishedDate) {
        this.finishedDate = finishedDate;
    }

    public boolean isOffer() {
        return offer;
    }

    public void setOffer(boolean offer) {
        this.offer = offer;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public int describeContents() {
        return 0;
    }


}

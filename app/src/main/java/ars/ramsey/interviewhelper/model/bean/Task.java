package ars.ramsey.interviewhelper.model.bean;

import java.util.Date;

/**
 * Created by Ramsey on 2017/4/7.
 */

public class Task {
    private int id;
    private String companyName;
    private String jobName;
    private String status;
    private Date createDate;
    private Date nextDate;
    private Date finishedDate;
    private boolean offer;

    public Task(){}

    public Task(String companyName,String jobName)
    {
        this.companyName = companyName;
        this.jobName = jobName;
        this.status = "";
        this.createDate = new Date();
        this.nextDate = null;
        this.finishedDate = null;
        this.offer = false;
    }

    public Task(int id,String companyName,String jobName,String status,Date createDate,Date nextDate,Date finishedDate,boolean offer)
    {
        this.id = id;
        this.companyName = companyName;
        this.jobName = jobName;
        this.status = status;
        this.createDate = createDate;
        this.nextDate = nextDate;
        this.finishedDate = finishedDate;
        this.offer = offer;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getNextDate() {
        return nextDate;
    }

    public void setNextDate(Date nextDate) {
        this.nextDate = nextDate;
    }

    public Date getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(Date finishedDate) {
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
}

package com.suhel.expensetracker.model;

import java.util.Date;

import io.realm.RealmObject;

public class TotalCredit extends RealmObject {

    private String reason;
    private float amount;
    private Date lastUpdated;

    public TotalCredit() {
    }

    public TotalCredit(String reason, float amount, Date lastUpdated) {
        this.reason = reason;
        this.amount = amount;
        this.lastUpdated = lastUpdated;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}

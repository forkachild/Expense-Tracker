package com.suhel.expensetracker.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Balance extends RealmObject {

    private String reason;
    private float balance;
    private Date lastUpdated;

    public Balance() {
    }

    public Balance(String reason, float balance, Date lastUpdated) {
        this.reason = reason;
        this.balance = balance;
        this.lastUpdated = lastUpdated;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}

package com.suhel.expensetracker.model;

import java.util.Date;

public class Balance {

    private float balance;
    private Date lastUpdated;

    public Balance() {
    }

    public Balance(float balance, Date lastUpdated) {
        this.balance = balance;
        this.lastUpdated = lastUpdated;
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

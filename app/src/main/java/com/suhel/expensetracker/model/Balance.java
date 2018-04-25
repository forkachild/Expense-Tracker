package com.suhel.expensetracker.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Balance extends RealmObject {

    @PrimaryKey
    private long id;

    private float balance;
    private Date lastUpdated;

    public Balance() {
    }

    public Balance(long id, float balance, Date lastUpdated) {
        this.id = id;
        this.balance = balance;
        this.lastUpdated = lastUpdated;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

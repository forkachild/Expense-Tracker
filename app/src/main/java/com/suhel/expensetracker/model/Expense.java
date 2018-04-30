package com.suhel.expensetracker.model;

import java.util.Calendar;
import java.util.Date;

import io.realm.RealmObject;

public class Expense extends RealmObject {

    private float amount, cumulativeBalance;
    private String type, reason, comment;
    private Date date;

    public Expense() {
    }

    public Expense(float amount, float cumulativeBalance, Type type, Reason reason, String comment, Date date) {
        this.amount = amount;
        this.cumulativeBalance = cumulativeBalance;
        this.type = type.toString();
        this.reason = reason.toString();
        this.comment = comment;
        this.date = date;
    }

    public Expense(float amount, float cumulativeBalance, Type type, Reason reason, String comment) {
        this(amount, cumulativeBalance, type, reason, comment, Calendar.getInstance().getTime());
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getCumulativeBalance() {
        return cumulativeBalance;
    }

    public void setCumulativeBalance(float cumulativeBalance) {
        this.cumulativeBalance = cumulativeBalance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public enum Type {

        Credit,
        Debit;

    }

    public enum Reason {

        All,
        Food,
        Hotel,
        Cigarette,
        Fuel,
        Alcohol,
        Shopping,
        Loan,
        Salary,
        Transport,
        Other;

        public static final Reason[] values = values();

    }

}

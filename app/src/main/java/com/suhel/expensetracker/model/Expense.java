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

        CREDIT,
        DEBIT;

        public static Type fromString(String string) {
            switch (string) {

                case "Credit":

                    return CREDIT;

                case "Debit":

                    return DEBIT;

            }
            return null;
        }

        @Override
        public String toString() {
            switch (this) {

                case CREDIT:

                    return "Credit";

                case DEBIT:

                    return "Debit";

            }
            return null;
        }

    }

    public enum Reason {

        FOOD,
        HOTEL,
        CIGARETTE,
        FUEL,
        ALCOHOL,
        SHOPPING,
        LOAN,
        SALARY,
        TRANSPORT,
        OTHER;

        public static final Reason[] values = values();

        public static final String[] exhaustiveString = exhaustive();

        public static Reason fromString(String string) {
            switch (string) {

                case "Food":

                    return FOOD;

                case "Hotel":

                    return HOTEL;

                case "Cigarette":

                    return CIGARETTE;

                case "Fuel":

                    return FUEL;

                case "Alcohol":

                    return ALCOHOL;

                case "Shopping":

                    return SHOPPING;

                case "Loan":

                    return LOAN;

                case "Salary":

                    return SALARY;

                case "Transport":

                    return TRANSPORT;

                default:

                    return OTHER;
            }
        }

        private static String[] exhaustive() {
            Reason[] reasons = values();
            String[] exhaustive = new String[reasons.length + 1];
            exhaustive[0] = "All";
            for (int i = 0; i < reasons.length; i++)
                exhaustive[i + 1] = reasons[i].toString();
            return exhaustive;
        }

        @Override
        public String toString() {
            switch (this) {

                case FOOD:

                    return "Food";

                case HOTEL:

                    return "Hotel";

                case CIGARETTE:

                    return "Cigarette";

                case FUEL:

                    return "Fuel";

                case ALCOHOL:

                    return "Alcohol";

                case SHOPPING:

                    return "Shopping";

                case LOAN:

                    return "Loan";

                case SALARY:

                    return "Salary";

                case TRANSPORT:

                    return "Transport";

                default:

                    return "Other";

            }
        }

    }

}

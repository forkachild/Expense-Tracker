package com.suhel.expensetracker.engine;

import com.suhel.expensetracker.model.Balance;
import com.suhel.expensetracker.model.Expense;

import java.util.Calendar;
import java.util.List;

import io.realm.Realm;

public class ExpenseEngine {

    private static final ExpenseEngine INSTANCE = new ExpenseEngine();
    private List<Expense> expenses;

    private ExpenseEngine() {

    }

    public static ExpenseEngine getInstance() {
        return INSTANCE;
    }

    public synchronized List<Expense> getExpenses() {
        return Realm.getDefaultInstance().where(Expense.class).findAll();
    }

    public synchronized List<Expense> getExpenses(String reason) {
        return Realm.getDefaultInstance().where(Expense.class).equalTo("reason", reason).findAll();
    }

    public synchronized void addExpense(float amount, Expense.Type type, Expense.Reason reason, String comment) {
        Realm.getDefaultInstance().executeTransaction((realm) -> {
            float balance = updateMainBalance(realm, type, amount);
            updateReasonBalance(realm, type, reason, amount);
            Expense expense = new Expense(amount, balance, type, reason, comment);
            realm.copyToRealm(expense);
        });
    }

    public synchronized float getMainBalance() {
        Realm realm = Realm.getDefaultInstance();
        Balance balance = realm.where(Balance.class).equalTo("reason", "All").findFirst();
        if (balance == null)
            return 0.0f;
        return balance.getBalance();
    }

    public synchronized float getBalance(String reason) {
        Realm realm = Realm.getDefaultInstance();
        Balance balance = realm.where(Balance.class).equalTo("reason", reason).findFirst();
        if (balance == null)
            return 0.0f;
        return balance.getBalance();
    }

    private float updateMainBalance(Realm realm, Expense.Type type, float amount) {
        Balance balanceObj = realm.where(Balance.class).equalTo("reason", "All").findFirst();
        float balance = 0.0f;

        if (balanceObj != null)
            balance = balanceObj.getBalance();

        if (type == Expense.Type.CREDIT)
            balance += amount;
        else
            balance -= amount;

        if (balanceObj != null) {
            balanceObj.setBalance(balance);
            balanceObj.setLastUpdated(Calendar.getInstance().getTime());
//            realm.copyToRealmOrUpdate(balanceObj);
        } else {
            balanceObj = new Balance("All", balance, Calendar.getInstance().getTime());
            realm.copyToRealm(balanceObj);
        }

        return balance;
    }

    private void updateReasonBalance(Realm realm, Expense.Type type, Expense.Reason reason, float amount) {
        Balance balanceObj = realm.where(Balance.class).equalTo("reason",
                reason.toString()).findFirst();

        float balance = 0.0f;

        if (balanceObj != null)
            balance = balanceObj.getBalance();

        if (type == Expense.Type.CREDIT)
            balance += amount;
        else
            balance -= amount;

        if (balanceObj != null) {
            balanceObj.setBalance(balance);
            balanceObj.setLastUpdated(Calendar.getInstance().getTime());
//            realm.copyToRealmOrUpdate(balanceObj);
        } else {
            balanceObj = new Balance(reason.toString(), balance, Calendar.getInstance().getTime());
            realm.copyToRealm(balanceObj);
        }
    }

}

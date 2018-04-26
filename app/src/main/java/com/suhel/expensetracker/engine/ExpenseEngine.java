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

    public synchronized List<Expense> getExpenses(String filterBy) {
        return Realm.getDefaultInstance().where(Expense.class).equalTo("reason", filterBy).findAll();
    }

    public synchronized void addExpense(float amount, Expense.Type type, Expense.Reason reason, String comment) {
        Realm.getDefaultInstance().executeTransaction((realm) -> {
            Balance balanceObj = realm.where(Balance.class).equalTo("id", 1L).findFirst();

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
                realm.copyToRealmOrUpdate(balanceObj);
            } else {
                balanceObj = new Balance(1L, balance, Calendar.getInstance().getTime());
                realm.copyToRealm(balanceObj);
            }

            Expense expense = new Expense(amount, balance, type, reason, comment);
            realm.copyToRealm(expense);
        });
    }

    public synchronized float getBalance() {
        Realm realm = Realm.getDefaultInstance();
        Balance balance = realm.where(Balance.class).equalTo("id", 1L).findFirst();
        if (balance == null)
            return 0.0f;
        return balance.getBalance();
    }

}

package com.suhel.expensetracker.engine;

import com.suhel.expensetracker.model.Expense;

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

    public List<Expense> getExpenses() {
        return Realm.getDefaultInstance().where(Expense.class).findAll();
    }

    public void addExpense(float amount, float cumulativeBalance, Expense.Type type, Expense.Reason reason, String comment) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Expense expense = new Expense(amount, cumulativeBalance, type, reason, comment);
        realm.copyToRealm(expense);
        realm.commitTransaction();
    }

}

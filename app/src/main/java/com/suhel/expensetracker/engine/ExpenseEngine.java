package com.suhel.expensetracker.engine;

import com.suhel.expensetracker.model.Balance;
import com.suhel.expensetracker.model.Expense;
import com.suhel.expensetracker.model.TotalCredit;
import com.suhel.expensetracker.model.TotalDebit;

import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ExpenseEngine {

    private static final ExpenseEngine INSTANCE = new ExpenseEngine();
    private static final String FIELD_REASON = "reason";

    private ExpenseEngine() {

    }

    public static ExpenseEngine getInstance() {
        return INSTANCE;
    }

    public synchronized List<Expense> getAllExpenses() {
        return Realm.getDefaultInstance().where(Expense.class).findAll();
    }

    public synchronized List<Expense> getAllExpenses(String reason) {
        return Realm.getDefaultInstance().where(Expense.class).equalTo(FIELD_REASON, reason).findAll();
    }

    public synchronized void addExpense(float amount, Expense.Type type, Expense.Reason reason, String comment) {
        Realm.getDefaultInstance().executeTransaction(realm -> {
            float balance = updateBalance(realm, type, Expense.Reason.All, amount);

            updateBalance(realm, type, reason, amount);

            if (type == Expense.Type.Credit) {
                updateTotalCredit(realm, Expense.Reason.All, amount);
                updateTotalCredit(realm, reason, amount);
            } else {
                updateTotalDebit(realm, Expense.Reason.All, amount);
                updateTotalDebit(realm, reason, amount);
            }

            Expense expense = new Expense(amount, balance, type, reason, comment);
            realm.copyToRealm(expense);
        });
    }

    public synchronized void removeExpense(float amount, Expense.Type type, Expense.Reason reason, String comment) {
        Realm.getDefaultInstance().executeTransaction(realm -> {
            float balance = revertBalance(realm, type, Expense.Reason.All, amount);

            revertBalance(realm, type, reason, amount);

            if (type == Expense.Type.Credit) {
                revertTotalCredit(realm, Expense.Reason.All, amount);
                revertTotalCredit(realm, reason, amount);
            } else {
                revertTotalDebit(realm, Expense.Reason.All, amount);
                revertTotalDebit(realm, reason, amount);
            }

            Expense expense = new Expense(amount, balance, type, reason, comment);
            realm.copyToRealm(expense);
        });
    }

    public synchronized float getBalance(Expense.Reason reason) {
        Realm realm = Realm.getDefaultInstance();
        Balance balance = realm.where(Balance.class).equalTo(FIELD_REASON, reason.toString()).findFirst();
        if (balance == null)
            return 0.0f;
        return balance.getBalance();
    }

    public synchronized float getTotalCredit(Expense.Reason reason) {
        Realm realm = Realm.getDefaultInstance();
        TotalCredit totalCredit = realm.where(TotalCredit.class).equalTo(FIELD_REASON, reason.toString()).findFirst();
        if (totalCredit == null)
            return 0.0f;
        return totalCredit.getAmount();
    }

    public synchronized float getTotalDebit(Expense.Reason reason) {
        Realm realm = Realm.getDefaultInstance();
        TotalDebit totalDebit = realm.where(TotalDebit.class).equalTo(FIELD_REASON, reason.toString()).findFirst();
        if (totalDebit == null)
            return 0.0f;
        return totalDebit.getAmount();
    }

    private float updateBalance(Realm realm, Expense.Type type, Expense.Reason reason, float amount) {
        Balance balanceObj = realm.where(Balance.class).equalTo(FIELD_REASON,
                reason.toString()).findFirst();

        float balance = 0.0f;

        if (balanceObj != null)
            balance = balanceObj.getBalance();

        if (type == Expense.Type.Credit)
            balance += amount;
        else
            balance -= amount;

        if (balanceObj != null) {
            balanceObj.setBalance(balance);
            balanceObj.setLastUpdated(Calendar.getInstance().getTime());
        } else {
            balanceObj = new Balance(reason.toString(), balance, Calendar.getInstance().getTime());
            realm.copyToRealm(balanceObj);
        }

        return balance;
    }

    private float revertBalance(Realm realm, Expense.Type type, Expense.Reason reason, float amount) {
        Balance balanceObj = realm.where(Balance.class).equalTo(FIELD_REASON,
                reason.toString()).findFirst();

        float balance = 0.0f;

        if (balanceObj != null)
            balance = balanceObj.getBalance();

        if (type == Expense.Type.Credit)
            balance -= amount;
        else
            balance += amount;

        if (balanceObj != null) {
            balanceObj.setBalance(balance);
            balanceObj.setLastUpdated(Calendar.getInstance().getTime());
        } else {
            balanceObj = new Balance(reason.toString(), balance, Calendar.getInstance().getTime());
            realm.copyToRealm(balanceObj);
        }

        return balance;
    }

    private void updateTotalCredit(Realm realm, Expense.Reason reason, float amount) {
        TotalCredit totalCreditObj = realm.where(TotalCredit.class).equalTo(FIELD_REASON,
                reason.toString()).findFirst();

        float totalCredit = 0.0f;

        if (totalCreditObj != null)
            totalCredit = totalCreditObj.getAmount();

        totalCredit += Math.abs(amount);

        if (totalCreditObj != null) {
            totalCreditObj.setAmount(totalCredit);
            totalCreditObj.setLastUpdated(Calendar.getInstance().getTime());
        } else {
            totalCreditObj = new TotalCredit(reason.toString(), totalCredit,
                    Calendar.getInstance().getTime());
            realm.copyToRealm(totalCreditObj);
        }

    }

    private void revertTotalCredit(Realm realm, Expense.Reason reason, float amount) {
        TotalCredit totalCreditObj = realm.where(TotalCredit.class).equalTo(FIELD_REASON,
                reason.toString()).findFirst();

        float totalCredit = 0.0f;

        if (totalCreditObj != null)
            totalCredit = totalCreditObj.getAmount();

        totalCredit -= Math.abs(amount);

        if (totalCreditObj != null) {
            totalCreditObj.setAmount(totalCredit);
            totalCreditObj.setLastUpdated(Calendar.getInstance().getTime());
        } else {
            totalCreditObj = new TotalCredit(reason.toString(), totalCredit,
                    Calendar.getInstance().getTime());
            realm.copyToRealm(totalCreditObj);
        }

    }

    private void updateTotalDebit(Realm realm, Expense.Reason reason, float amount) {
        TotalDebit totalDebitObj = realm.where(TotalDebit.class).equalTo(FIELD_REASON,
                reason.toString()).findFirst();

        float totalDebit = 0.0f;

        if (totalDebitObj != null)
            totalDebit = totalDebitObj.getAmount();

        totalDebit += Math.abs(amount);

        if (totalDebitObj != null) {
            totalDebitObj.setAmount(totalDebit);
            totalDebitObj.setLastUpdated(Calendar.getInstance().getTime());
        } else {
            totalDebitObj = new TotalDebit(reason.toString(), totalDebit,
                    Calendar.getInstance().getTime());
            realm.copyToRealm(totalDebitObj);
        }

    }

    private void revertTotalDebit(Realm realm, Expense.Reason reason, float amount) {
        TotalDebit totalDebitObj = realm.where(TotalDebit.class).equalTo(FIELD_REASON,
                reason.toString()).findFirst();

        float totalDebit = 0.0f;

        if (totalDebitObj != null)
            totalDebit = totalDebitObj.getAmount();

        totalDebit -= Math.abs(amount);

        if (totalDebitObj != null) {
            totalDebitObj.setAmount(totalDebit);
            totalDebitObj.setLastUpdated(Calendar.getInstance().getTime());
        } else {
            totalDebitObj = new TotalDebit(reason.toString(), totalDebit,
                    Calendar.getInstance().getTime());
            realm.copyToRealm(totalDebitObj);
        }

    }

    public synchronized void reset() {
        Realm.getDefaultInstance().executeTransaction(realm -> realm.deleteAll());
    }

}

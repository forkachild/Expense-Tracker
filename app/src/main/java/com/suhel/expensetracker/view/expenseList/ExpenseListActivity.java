package com.suhel.expensetracker.view.expenseList;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.suhel.expensetracker.R;
import com.suhel.expensetracker.databinding.ActivityExpenseListBinding;
import com.suhel.expensetracker.engine.ExpenseEngine;
import com.suhel.expensetracker.model.Expense;
import com.suhel.expensetracker.view.addExpense.AddExpenseActivity;

import java.util.List;
import java.util.Locale;

public class ExpenseListActivity extends AppCompatActivity {

    private ActivityExpenseListBinding binding;
    private ExpenseListAdapter adapter = new ExpenseListAdapter();

    private String[] filterBy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_expense_list);
        setSupportActionBar(binding.toolbar);
        binding.list.setLayoutManager(new LinearLayoutManager(this));
        binding.list.setAdapter(adapter);
        binding.btnAddExpense.setOnClickListener(v -> startActivity(
                new Intent(ExpenseListActivity.this, AddExpenseActivity.class)));

        Expense.Reason[] reasons = Expense.Reason.values;
        filterBy = new String[reasons.length + 1];
        filterBy[0] = "All";
        for (int i = 0; i < reasons.length; i++)
            filterBy[i + 1] = reasons[i].toString();
        ArrayAdapter<String> filterByAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, filterBy);
        filterByAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.sortBy.setAdapter(filterByAdapter);
        binding.sortBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reloadData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadData();
    }

    private void reloadData() {
        List<Expense> data;

        int filterSelection = binding.sortBy.getSelectedItemPosition();
        float currentBalance;

        switch (filterSelection) {

            case 0:

                data = ExpenseEngine.getInstance().getExpenses();
                currentBalance = ExpenseEngine.getInstance().getMainBalance();
                break;

            default:

                data = ExpenseEngine.getInstance().getExpenses(filterBy[filterSelection]);
                currentBalance = ExpenseEngine.getInstance().getBalance(filterBy[filterSelection]);
                break;

        }

        binding.tvPlaceholder.setVisibility((data == null || data.isEmpty())
                ? View.VISIBLE : View.GONE);
        adapter.setData(data);
        binding.tvCurrentBalance.setText(String.format(Locale.getDefault(), "%,.2f", currentBalance));
    }

}

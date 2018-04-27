package com.suhel.expensetracker.view.expenseList;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.jaredrummler.materialspinner.MaterialSpinnerAdapter;
import com.suhel.expensetracker.R;
import com.suhel.expensetracker.databinding.ActivityExpenseListBinding;
import com.suhel.expensetracker.engine.ExpenseEngine;
import com.suhel.expensetracker.model.Expense;
import com.suhel.expensetracker.view.addExpense.AddExpenseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ExpenseListActivity extends AppCompatActivity {

    private ActivityExpenseListBinding binding;
    private ExpenseListAdapter adapter = new ExpenseListAdapter();

    private List<String> filterByList;

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
        filterByList = new ArrayList<>(reasons.length + 1);
        filterByList.add("All");
        for (int i = 0; i < reasons.length; i++)
            filterByList.add(reasons[i].toString());

        binding.filterBy.setItems(filterByList);
        binding.filterBy.setSelectedIndex(0);
        binding.filterBy.setOnItemSelectedListener((view, position, id, item) -> reloadData());
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadData();
    }

    private void reloadData() {

        int filterSelection = binding.filterBy.getSelectedIndex();

        List<Expense> data = ExpenseEngine.getInstance().getExpenses(filterByList.get(filterSelection));
        float currentBalance = ExpenseEngine.getInstance().getBalance(filterByList.get(filterSelection));

        binding.tvPlaceholder.setVisibility((data == null || data.isEmpty())
                ? View.VISIBLE : View.GONE);
        adapter.setData(data);
        binding.tvCurrentBalance.setText(String.format(Locale.getDefault(), "Balance %,.2f", currentBalance));
    }

}

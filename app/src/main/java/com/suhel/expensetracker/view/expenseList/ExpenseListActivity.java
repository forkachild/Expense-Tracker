package com.suhel.expensetracker.view.expenseList;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.suhel.expensetracker.R;
import com.suhel.expensetracker.databinding.ActivityExpenseListBinding;
import com.suhel.expensetracker.engine.ExpenseEngine;
import com.suhel.expensetracker.model.Expense;
import com.suhel.expensetracker.view.addExpense.AddExpenseActivity;

import java.util.List;

public class ExpenseListActivity extends AppCompatActivity {

    private ActivityExpenseListBinding binding;
    private ExpenseListAdapter adapter = new ExpenseListAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_expense_list);
        setSupportActionBar(binding.toolbar);
        binding.list.setLayoutManager(new LinearLayoutManager(this));
        binding.list.setAdapter(adapter);
        binding.btnAddExpense.setOnClickListener(v -> startActivity(
                new Intent(ExpenseListActivity.this, AddExpenseActivity.class)));
        List<Expense> data = ExpenseEngine.getInstance().getExpenses();
        binding.tvPlaceholder.setVisibility((data == null || data.isEmpty())
                ? View.VISIBLE : View.GONE);
        adapter.setData(data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Expense> data = ExpenseEngine.getInstance().getExpenses();
        binding.tvPlaceholder.setVisibility((data == null || data.isEmpty())
                ? View.VISIBLE : View.GONE);
        adapter.setData(data);
    }

}

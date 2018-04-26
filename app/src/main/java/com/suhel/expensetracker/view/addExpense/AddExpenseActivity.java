package com.suhel.expensetracker.view.addExpense;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.MenuItem;

import com.suhel.expensetracker.R;
import com.suhel.expensetracker.databinding.ActivityAddExpenseBinding;
import com.suhel.expensetracker.engine.ExpenseEngine;
import com.suhel.expensetracker.model.Expense;

public class AddExpenseActivity extends AppCompatActivity {

    private ActivityAddExpenseBinding binding;
    private ReasonAdapter adapter = new ReasonAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_expense);
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.btnCancel.setOnClickListener(v -> onBackPressed());
        binding.btnSave.setOnClickListener(v -> save());
        binding.reasonList.setLayoutManager(new GridLayoutManager(this, 2));
        binding.reasonList.setAdapter(adapter);
        adapter.setData(Expense.Reason.values);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    private String getAmountString() {
        return binding.txtAmount.getText().toString().trim();
    }

    private float getAmount() {
        return Float.parseFloat(getAmountString());
    }

    @NonNull
    private Expense.Type getType() {
        int id = binding.radioType.getCheckedRadioButtonId();
        if (id == R.id.radioCredit)
            return Expense.Type.CREDIT;
        else
            return Expense.Type.DEBIT;
    }

    @NonNull
    private Expense.Reason getReason() {
        return adapter.getSelected();
    }

    @NonNull
    private String getComment() {
        return binding.txtComment.getText().toString().trim();
    }

    private boolean validate() {
        boolean flag = true;

        if (getAmountString().isEmpty()) {
            binding.errorAmount.setError(getString(R.string.error_amount));
            flag = false;
        } else {
            binding.errorAmount.setError(null);
        }

        return flag;
    }

    private void save() {
        if (validate()) {
            ExpenseEngine.getInstance().addExpense(getAmount(), getType(), getReason(),
                    getComment());
            onBackPressed();
        }
    }

}

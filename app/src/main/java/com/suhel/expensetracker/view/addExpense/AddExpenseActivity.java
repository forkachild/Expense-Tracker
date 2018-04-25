package com.suhel.expensetracker.view.addExpense;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.suhel.expensetracker.R;
import com.suhel.expensetracker.databinding.ActivityAddExpenseBinding;

public class AddExpenseActivity extends AppCompatActivity {

    private ActivityAddExpenseBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_expense);
        binding.btnCancel.setOnClickListener(v -> onBackPressed());
        binding.btnSave.setOnClickListener(v -> {

        });
    }

    private void save() {

    }

}

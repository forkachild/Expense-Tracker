package com.suhel.expensetracker.view.expenseList;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.suhel.expensetracker.Constants;
import com.suhel.expensetracker.R;
import com.suhel.expensetracker.databinding.ActivityExpenseListBinding;
import com.suhel.expensetracker.engine.ExpenseEngine;
import com.suhel.expensetracker.model.Expense;
import com.suhel.expensetracker.view.addExpense.AddExpenseActivity;
import com.suhel.expensetracker.view.expenseList.list.ExpenseListPagerAdapter;
import com.suhel.expensetracker.view.settings.SettingsActivity;

import java.util.Locale;

public class ExpenseListActivity extends AppCompatActivity {

    private ActivityExpenseListBinding binding;
    private ExpenseListPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_expense_list);
        setSupportActionBar(binding.toolbar);
        binding.btnAddExpense.setOnClickListener(v -> startActivityForResult(
                new Intent(ExpenseListActivity.this, AddExpenseActivity.class), 101));
        pagerAdapter = new ExpenseListPagerAdapter(getSupportFragmentManager());
        binding.pager.setAdapter(pagerAdapter);
        binding.tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        binding.tabs.setupWithViewPager(binding.pager);
        binding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateTotal(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });
        updateTotal(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_expense_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itemSettings) {
            startActivityForResult(new Intent(this, SettingsActivity.class), 100);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == Constants.RequestCode.Reset) {
                ExpenseEngine.getInstance().reset();
                recreate();
            } else if (requestCode == Constants.RequestCode.Add) {
                updateTotal(binding.pager.getCurrentItem());
            }

        }

    }

    private void updateTotal(int position) {
        Expense.Reason reason = Expense.Reason.values[position];

        float currentBalance = ExpenseEngine.getInstance().getBalance(reason);
        float totalCredit = ExpenseEngine.getInstance().getTotalCredit(reason);
        float totalDebit = ExpenseEngine.getInstance().getTotalDebit(reason);

        binding.tvCurrentBalance.setText(String.format(Locale.getDefault(),
                "Balance %,.2f", currentBalance));
        binding.tvTotalCredit.setText(String.format(Locale.getDefault(),
                "Credit %,.2f", totalCredit));
        binding.tvTotalDebit.setText(String.format(Locale.getDefault(),
                "Debit %,.2f", totalDebit));
    }

}

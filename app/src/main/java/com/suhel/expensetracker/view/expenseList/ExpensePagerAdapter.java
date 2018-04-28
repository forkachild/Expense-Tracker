package com.suhel.expensetracker.view.expenseList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.suhel.expensetracker.model.Expense;

public class ExpensePagerAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments = new Fragment[getCount()];

    public ExpensePagerAdapter(FragmentManager fm) {
        super(fm);

        for (int i = 0; i < fragments.length; i++) {
            fragments[i] = new ExpenseListFragment();

            if (i == 0)
                continue;

            Bundle args = new Bundle();
            args.putString("FILTER", Expense.Reason.exhaustiveString[i]);
            fragments[i].setArguments(args);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return Expense.Reason.exhaustiveString.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return Expense.Reason.exhaustiveString[position];
    }

}

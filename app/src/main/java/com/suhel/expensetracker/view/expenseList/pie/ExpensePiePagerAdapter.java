package com.suhel.expensetracker.view.expenseList.pie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.suhel.expensetracker.Constants;
import com.suhel.expensetracker.model.Expense;
import com.suhel.expensetracker.view.expenseList.pie.ExpensePieFragment;

public class ExpensePiePagerAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments = new Fragment[getCount()];

    public ExpensePiePagerAdapter(FragmentManager fm) {
        super(fm);

        for (int i = 0; i < fragments.length; i++) {
            fragments[i] = new ExpensePieFragment();

            if (i == 0)
                continue;

            Bundle args = new Bundle();
            args.putString(Constants.Key.Filter, Expense.Reason.values[i].toString());
            fragments[i].setArguments(args);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return Expense.Reason.values.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return Expense.Reason.values[position].toString();
    }

}

package com.suhel.expensetracker.view.expenseList.pie;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.realm.implementation.RealmPieDataSet;
import com.suhel.expensetracker.Constants;
import com.suhel.expensetracker.databinding.FragmentExpensePieBinding;
import com.suhel.expensetracker.engine.ExpenseEngine;
import com.suhel.expensetracker.model.Expense;

import java.util.List;

import io.realm.RealmResults;

public class ExpensePieFragment extends Fragment {

    private FragmentExpensePieBinding binding;
    private String filter = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentExpensePieBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null)
            filter = getArguments().getString(Constants.Key.Filter, null);
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Expense> data;
        if (filter == null || filter.isEmpty())
            data = ExpenseEngine.getInstance().getAllExpenses();
        else
            data = ExpenseEngine.getInstance().getAllExpenses(filter);

        binding.tvPlaceholder.setVisibility((data == null || data.isEmpty()) ?
                View.VISIBLE : View.GONE);
        binding.pie.setVisibility((data == null || data.isEmpty()) ?
                View.GONE : View.VISIBLE);

        if (data != null && !data.isEmpty()) {
            RealmPieDataSet<Expense> dataSet = new RealmPieDataSet<>((RealmResults<Expense>) data,
                    "amount", "reason");
            binding.pie.setData(new PieData(dataSet));
            binding.pie.invalidate();
        }

    }

}

package com.suhel.expensetracker.view.expenseList;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suhel.expensetracker.databinding.FragmentExpenseListBinding;
import com.suhel.expensetracker.engine.ExpenseEngine;
import com.suhel.expensetracker.model.Expense;

import java.util.List;

public class ExpenseListFragment extends Fragment {

    private FragmentExpenseListBinding binding;
    private ExpenseListAdapter adapter = new ExpenseListAdapter();
    private String filter = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentExpenseListBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.list.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.list.setAdapter(adapter);
        if (getArguments() != null)
            filter = getArguments().getString("FILTER", null);
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
        adapter.setData(data);

    }

}

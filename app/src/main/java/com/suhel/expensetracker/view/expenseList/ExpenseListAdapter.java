package com.suhel.expensetracker.view.expenseList;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.suhel.expensetracker.databinding.ItemExpenseBinding;
import com.suhel.expensetracker.model.Expense;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ExpenseListAdapter extends RecyclerView.Adapter<ExpenseListAdapter.ExpenseListViewHolder> {

    private DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    private List<Expense> data;

    @NonNull
    @Override
    public ExpenseListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExpenseListViewHolder(
                ItemExpenseBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseListViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    private Expense getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    public void setData(List<Expense> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class ExpenseListViewHolder extends RecyclerView.ViewHolder {

        ItemExpenseBinding binding;

        ExpenseListViewHolder(ItemExpenseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Expense expense) {
            binding.tvAmount.setText(String.format(Locale.getDefault(), "%,.2f", expense.getAmount()));
            binding.tvReason.setText(expense.getReason());
            binding.tvDate.setText(dateFormat.format(expense.getDate()));
        }

    }

}

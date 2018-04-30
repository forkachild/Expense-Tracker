package com.suhel.expensetracker.view.addExpense;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.suhel.expensetracker.R;
import com.suhel.expensetracker.databinding.ItemReasonBinding;
import com.suhel.expensetracker.model.Expense;

public class ReasonAdapter extends RecyclerView.Adapter<ReasonAdapter.ReasonViewHolder> {

    private int selection = 0;
    private Expense.Reason[] data;

    ReasonAdapter() {
        data = Expense.Reason.values;
    }

    @NonNull
    @Override
    public ReasonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReasonViewHolder(ItemReasonBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReasonViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    private Expense.Reason getItem(int position) {
        return data[position + 1];
    }

    @Override
    public int getItemCount() {
        return data != null ? data.length - 1 : 0;
    }

    @NonNull
    public Expense.Reason getSelected() {
        return getItem(selection);
    }

    class ReasonViewHolder extends RecyclerView.ViewHolder {

        private ItemReasonBinding binding;

        ReasonViewHolder(ItemReasonBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(v -> {
                selection = getAdapterPosition();
                notifyDataSetChanged();
            });
        }

        void bind(Expense.Reason reason) {
            boolean isSelected = getAdapterPosition() == selection;
            binding.root.setBackgroundResource(isSelected ? R.drawable.bg_rounded_corner_blue
                    : R.drawable.bg_rounded_corner_grey);
            binding.tvReason.setTextColor(isSelected ? Color.WHITE : Color.BLACK);
            binding.tvReason.setText(reason.toString());
        }

    }


}

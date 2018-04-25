package com.suhel.expensetracker.view.addExpense;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suhel.expensetracker.databinding.ItemReasonBinding;
import com.suhel.expensetracker.model.Expense;

public class ReasonAdapter extends RecyclerView.Adapter<ReasonAdapter.ReasonViewHolder> {

    private int selection = 0;
    private Expense.Reason[] data;

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

    public void setData(Expense.Reason[] data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public Expense.Reason getItem(int position) {
        return data[position];
    }

    @Override
    public int getItemCount() {
        return data != null ? data.length : 0;
    }

    @NonNull
    public Expense.Reason getSelected() {
        return data[selection];
    }

    class ReasonViewHolder extends RecyclerView.ViewHolder {

        private ItemReasonBinding binding;

        public ReasonViewHolder(ItemReasonBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(v -> {
                selection = getAdapterPosition();
                notifyDataSetChanged();
            });
        }

        void bind(Expense.Reason reason) {
            binding.imgCheck.setVisibility(getAdapterPosition() == selection ? View.VISIBLE : View.INVISIBLE);
            binding.tvReason.setText(reason.toString());
        }

    }


}

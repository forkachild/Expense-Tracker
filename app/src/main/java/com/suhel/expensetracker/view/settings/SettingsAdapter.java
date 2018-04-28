package com.suhel.expensetracker.view.settings;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suhel.expensetracker.databinding.ItemSettingsBinding;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder> {

    private OnClickListener onClickListener;
    private OnLongClickListener onLongClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    @NonNull
    @Override
    public SettingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SettingsViewHolder(ItemSettingsBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsViewHolder holder, int position) {
        holder.bind(SettingsType.values[position]);
    }

    @Override
    public int getItemCount() {
        return SettingsType.values.length;
    }

    public interface OnClickListener {

        void onClick(SettingsType settingsType);

    }

    public interface OnLongClickListener {

        void onLongClick(SettingsType settingsType);

    }

    class SettingsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        ItemSettingsBinding binding;

        SettingsViewHolder(ItemSettingsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
            binding.getRoot().setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null)
                onClickListener.onClick(SettingsType.values[getAdapterPosition()]);
        }

        @Override
        public boolean onLongClick(View v) {
            if (onLongClickListener != null) {
                onLongClickListener.onLongClick(SettingsType.values[getAdapterPosition()]);
                return true;
            }
            return false;
        }

        void bind(SettingsType settingsType) {
            binding.tvSetting.setText(settingsType.toString());
            boolean isDangerous = (settingsType == SettingsType.ResetEverything);
            binding.tvSetting.setTextColor(isDangerous ? Color.RED : Color.BLACK);
        }

    }

}

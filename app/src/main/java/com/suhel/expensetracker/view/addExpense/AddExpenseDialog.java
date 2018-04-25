package com.suhel.expensetracker.view.addExpense;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suhel.expensetracker.R;
import com.suhel.expensetracker.databinding.DialogAddExpenseBinding;

public class AddExpenseDialog extends BottomSheetDialogFragment implements View.OnClickListener {

    private DialogAddExpenseBinding binding;
    private OnClickListener onClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return (binding = DialogAddExpenseBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnAdd.setOnClickListener(this);
        binding.btnAdd.setOnClickListener(this);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnAdd:

                if (onClickListener != null)
                    onClickListener.onAdd(getAmount(), getReason());
                dismiss();
                break;

            case R.id.btnCancel:

                if (onClickListener != null)
                    onClickListener.onCancel();
                dismiss();
                break;

        }
    }

    public float getAmount() {
        return Float.parseFloat(binding.txtAmount.getText().toString().trim());
    }

    @NonNull
    public String getReason() {
        return binding.txtReason.getText().toString().trim();
    }

    public interface OnClickListener {

        void onAdd(float amount, String reason);

        void onCancel();

    }

}

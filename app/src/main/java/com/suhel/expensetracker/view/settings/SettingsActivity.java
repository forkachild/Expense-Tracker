package com.suhel.expensetracker.view.settings;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.widget.Toast;

import com.suhel.expensetracker.R;
import com.suhel.expensetracker.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity implements SettingsAdapter.OnClickListener, SettingsAdapter.OnLongClickListener {

    private ActivitySettingsBinding binding;
    private SettingsAdapter adapter = new SettingsAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.list.setLayoutManager(new LinearLayoutManager(this));
        binding.list.setAdapter(adapter);
        adapter.setOnClickListener(this);
        adapter.setOnLongClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(SettingsType settingsType) {
        if (settingsType == SettingsType.ResetEverything) {
            new AlertDialog.Builder(this)
                    .setMessage(R.string.text_reset_warning)
                    .setPositiveButton(getString(R.string.text_yes), (dialog, which) ->
                            Toast.makeText(this,
                                    R.string.text_funny,
                                    Toast.LENGTH_SHORT).show())
                    .setNegativeButton(getString(R.string.text_no), null)
                    .show();
        }
    }

    @Override
    public void onLongClick(SettingsType settingsType) {
        if (settingsType == SettingsType.ResetEverything) {
            new AlertDialog.Builder(this)
                    .setMessage(R.string.text_reset_warning)
                    .setPositiveButton(getString(R.string.text_yes), (dialog, which) -> {
                        setResult(RESULT_OK);
                        onBackPressed();
                    })
                    .setNegativeButton(getString(R.string.text_no), null)
                    .show();
        }
    }

}
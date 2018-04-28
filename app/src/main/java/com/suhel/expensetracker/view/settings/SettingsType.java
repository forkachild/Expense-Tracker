package com.suhel.expensetracker.view.settings;

public enum SettingsType {

    ResetEverything;

    public static final SettingsType[] values = values();

    @Override
    public String toString() {
        switch (this) {

            case ResetEverything:
                return "Reset Everything";

        }
        return null;
    }

}

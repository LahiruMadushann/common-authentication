package com.ctn.commonauthentication.util.enums;

public enum Accident {
    なし("なし"),
    あり修復済("あり(修復済)"),
    あり修復済み("あり(修復済み)"),
    あり未修理("あり(未修理)"),
    不明("不明");

    private final String displayName;

    Accident(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Accident fromDisplayName(String displayName) {
        for (Accident accident : Accident.values()) {
            if (accident.displayName.equals(displayName)) {
                return accident;
            }
        }
        throw new IllegalArgumentException("No enum constant with display name: " + displayName);
    }
}

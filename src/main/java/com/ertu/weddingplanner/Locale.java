package com.ertu.weddingplanner;

public enum Locale {
    TR("tr"),
    RU("ru"),
    JP("jp"),
    GB("gb"),
    DE("de"),
    AR("ar");

    private final String displayName;

    Locale(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}

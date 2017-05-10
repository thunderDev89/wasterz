package com.acemen.android.wasterz.domain.model;

/**
 * Created by Audrik ! on 09/05/2017.
 */

public enum WasteType {
    BUTT("butt", "Mégot"),
    EXCREMENT("excrement", "Excrément"),
    TAG("tag", "Tag"),
    ANY_WASTE("any-waste", "Déchet quelconque"),
    FULL_TRASH("full-trash", "Poubelle pleine");

    private String type;
    private String name;

    WasteType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}

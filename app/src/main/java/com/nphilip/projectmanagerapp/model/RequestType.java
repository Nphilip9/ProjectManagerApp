package com.nphilip.projectmanagerapp.model;

import androidx.annotation.NonNull;

public enum RequestType {
    NEW_ITEM_CREATION,
    ITEM_DELETION,
    GET_JSON_DATA;

    @NonNull
    @Override
    public String toString() {
        return this.name();
    }
}

package com.nphilip.projectmanagerapp.model;

import androidx.annotation.NonNull;

public class ProjectItem {
    private final String title;
    private final String subtitle;

    private final String creationDate;

    public ProjectItem(String title, String subtitle, String creationDate) {
        this.title = title;
        this.subtitle = subtitle;
        this.creationDate = creationDate;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getCreationDate() {
        return creationDate;
    }

    @NonNull
    @Override
    public String toString() {
        return title + " " + subtitle + " " + creationDate;
    }
}

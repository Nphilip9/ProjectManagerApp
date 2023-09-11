package com.nphilip.projectmanagerapp.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nphilip.projectmanagerapp.model.ProjectItem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JSONDataManager {

    Context context;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferncesEditor;

    public JSONDataManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("ItemsSharedPreferences", Context.MODE_PRIVATE);
        sharedPreferncesEditor = sharedPreferences.edit();
    }

    public void appendItemToSharedPreferences(ProjectItem projectItem) {
        ArrayList<ProjectItem> items = loadItemsFromSharedPreferences();
        items.add(projectItem);
        sharedPreferncesEditor.putString("Items", new Gson().toJson(items));
    }

    public void deleteItemFromSharedPreferences(ProjectItem projectItem) {
        ArrayList<ProjectItem> items = loadItemsFromSharedPreferences();
        items.remove(projectItem);
        sharedPreferncesEditor.putString("Items", new Gson().toJson(items));
    }

    public String loadJSONStringFromSharedPreferences() {
        return sharedPreferences.getString("Items", "{}");
    }

    public ArrayList<ProjectItem> loadItemsFromSharedPreferences() {
        Type listType = new TypeToken<List<ProjectItem>>() {
        }.getType();
        return new Gson().fromJson(loadJSONStringFromSharedPreferences(), listType);
    }
}


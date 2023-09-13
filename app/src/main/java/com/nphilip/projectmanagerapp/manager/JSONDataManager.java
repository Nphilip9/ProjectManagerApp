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

    private static final String ITEMS_KEY = "Items";
    private static final String REQUEST_QUEUE_KEY = "Request_Queue";

    public JSONDataManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("ItemsSharedPreferences", Context.MODE_PRIVATE);
        sharedPreferncesEditor = sharedPreferences.edit();
        sharedPreferncesEditor.putString(ITEMS_KEY, "[]");
    }

    public void appendItemToSharedPreferences(ProjectItem projectItem) {
        ArrayList<ProjectItem> items = loadItemsFromSharedPreferences();
        items.add(projectItem);
        sharedPreferncesEditor.putString(ITEMS_KEY, new Gson().toJson(items));
    }

    public void deleteItemFromSharedPreferences(ProjectItem projectItem) {
        ArrayList<ProjectItem> items = loadItemsFromSharedPreferences();
        items.remove(projectItem);
        sharedPreferncesEditor.putString(ITEMS_KEY, new Gson().toJson(items));
    }

    public String loadJSONStringFromSharedPreferences() {
        return sharedPreferences.getString(ITEMS_KEY, "[]");
    }

    public ArrayList<ProjectItem> loadItemsFromSharedPreferences() {
        Type listType = new TypeToken<List<ProjectItem>>() {}.getType();
        return new Gson().fromJson(loadJSONStringFromSharedPreferences(), listType);
    }

    public void handleRequestOnServerOffline(String request) {
        String requestQueue = sharedPreferences.getString(REQUEST_QUEUE_KEY, "");
        sharedPreferncesEditor.putString(REQUEST_QUEUE_KEY, requestQueue + request + "###");
    }

    public void checkRequestQueue() {
        String requestQueue = sharedPreferences.getString(REQUEST_QUEUE_KEY, "");
        if (!requestQueue.equals("")) {
            for (String request : requestQueue.split("###")) {
                new RequestAndResponseManager(context).createRequest(request);
            }
            sharedPreferncesEditor.remove(REQUEST_QUEUE_KEY);
        }
    }
}


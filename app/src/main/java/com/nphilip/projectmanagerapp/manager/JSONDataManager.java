package com.nphilip.projectmanagerapp.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nphilip.projectmanagerapp.MainActivity;
import com.nphilip.projectmanagerapp.model.ProjectItem;
import com.nphilip.projectmanagerapp.model.RequestType;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JSONDataManager {

    Context context;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;

    private static final String ITEMS_KEY = "Items";
    private static final String REQUEST_QUEUE_KEY = "Request_Queue";

    public JSONDataManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("ItemsSharedPreferences", Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
        if (!sharedPreferences.getAll().containsKey(ITEMS_KEY)) {
            sharedPreferencesEditor.putString(ITEMS_KEY, "[]");
            sharedPreferencesEditor.apply();
        } if (!sharedPreferences.getAll().containsKey(REQUEST_QUEUE_KEY)) {
            sharedPreferencesEditor.putString(REQUEST_QUEUE_KEY, "[]");
            sharedPreferencesEditor.apply();
        }
    }

    public void appendItemToSharedPreferences(ProjectItem projectItem) {
        ArrayList<ProjectItem> items = loadItemsFromSharedPreferences();
        items.add(projectItem);
        sharedPreferencesEditor.putString(ITEMS_KEY, new Gson().toJson(items));
        sharedPreferencesEditor.apply();
        MainActivity.getActivity().runOnUiThread(MainActivity::init);
    }

    public void appendItemsToSharedPreferences(ArrayList<ProjectItem> newItems) {
        ArrayList<ProjectItem> items = loadItemsFromSharedPreferences();
        items.addAll(newItems);
        sharedPreferencesEditor.putString(ITEMS_KEY, new Gson().toJson(items));
        sharedPreferencesEditor.apply();
        MainActivity.getActivity().runOnUiThread(MainActivity::init);
    }

    public ProjectItem toProjectItem(String gsonString) {
        return new Gson().fromJson(gsonString, ProjectItem.class);
    }

    public ArrayList<ProjectItem> toProjectItems(String gsonString) {
        Type listType = new TypeToken<List<ProjectItem>>() {}.getType();
        return new Gson().fromJson(gsonString, listType);
    }

    public void deleteItemFromSharedPreferences(ProjectItem projectItem) {
        ArrayList<ProjectItem> items = loadItemsFromSharedPreferences();
        items.remove(projectItem);
        sharedPreferencesEditor.putString(ITEMS_KEY, new Gson().toJson(items));
        sharedPreferencesEditor.apply();
        MainActivity.getActivity().runOnUiThread(MainActivity::init);
    }

    public String loadJSONStringFromSharedPreferences() {
        return sharedPreferences.getString(ITEMS_KEY, "[]");
    }

    public ArrayList<ProjectItem> loadItemsFromSharedPreferences() {
        Type listType = new TypeToken<List<ProjectItem>>() {}.getType();
        return new Gson().fromJson(loadJSONStringFromSharedPreferences(), listType);
    }

    public void handleRequestOnServerOffline(String request) {
        String requestQueue = sharedPreferences.getString(REQUEST_QUEUE_KEY, "[]");
        sharedPreferencesEditor.putString(REQUEST_QUEUE_KEY, requestQueue + request + "###");
        sharedPreferencesEditor.apply();
    }

    public void checkRequestQueue() {
        String requestQueue = sharedPreferences.getString(REQUEST_QUEUE_KEY, "");
        if (!requestQueue.equals("[]")) {
            for (String request : requestQueue.split("###")) {
                new RequestAndResponseManager(context).createRequest(request);
            }
            sharedPreferencesEditor.remove(REQUEST_QUEUE_KEY);
            sharedPreferencesEditor.apply();
        }
    }
}


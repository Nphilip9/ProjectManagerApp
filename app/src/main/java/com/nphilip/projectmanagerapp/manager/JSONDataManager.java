package com.nphilip.projectmanagerapp.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nphilip.projectmanagerapp.client.Client;
import com.nphilip.projectmanagerapp.model.ProjectItem;
import com.nphilip.projectmanagerapp.model.RequestType;

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

    public ArrayList<String> loadItemsFromSharedPrefernces() {
        Type listType = new TypeToken<List<ProjectItem>>() {}.getType();
        return new Gson().fromJson(sharedPreferences.getString("Items", "{}"), listType);
    }
}


package com.nphilip.projectmanagerapp.manager;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nphilip.projectmanagerapp.MainActivity;
import com.nphilip.projectmanagerapp.client.Client;
import com.nphilip.projectmanagerapp.model.ProjectItem;
import com.nphilip.projectmanagerapp.model.RequestType;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RequestAndResponseManager {
    Context context;

    public RequestAndResponseManager(Context context) {
        this.context = context;
    }

    public void createRequest(String request) {
        new Client(context).sendMessage(request);
    }

    public void createRequest(RequestType requestType) {
        new Client(context).sendMessage(requestType.toString());
    }

    public void createRequest(String requestType, ProjectItem projectItem) {
        new Client(context).sendMessage(requestType + new Gson().toJson(projectItem));
    }

    public void handleIncomingRequests(String request) {
        JSONDataManager jsonDataManager = new JSONDataManager(context);
        if (request.startsWith(RequestType.GET_JSON_DATA.toString())) {
            request = request.replace(RequestType.GET_JSON_DATA.toString(), "");
            jsonDataManager.appendItemsToSharedPreferences(jsonDataManager.toProjectItems(request));
        } else if (request.startsWith(RequestType.NEW_ITEM_CREATION.toString())) {
            request = request.replace(RequestType.NEW_ITEM_CREATION.toString(), "");
            jsonDataManager.appendItemToSharedPreferences(jsonDataManager.toProjectItem(request));
        } else if (request.startsWith(RequestType.ITEM_DELETION.toString())) {
            request = request.replace(RequestType.ITEM_DELETION.toString(), "");
            jsonDataManager.deleteItemFromSharedPreferences(jsonDataManager.toProjectItem(request));
        }
    }
}

package com.nphilip.projectmanagerapp.manager;

import android.content.Context;
import android.widget.MultiAutoCompleteTextView;

import com.google.gson.Gson;
import com.nphilip.projectmanagerapp.client.Client;
import com.nphilip.projectmanagerapp.model.ProjectItem;

public class JSONDataManager {

    Context context;

    public JSONDataManager(Context context) {
        this.context = context;
    }

    public void createRequest(String requestType) {
        new Client(context).sendMessage(requestType);
    }

    public void createRequest(String requestType, ProjectItem projectItem) {
        new Client(context).sendMessage(requestType + new Gson().toJson(projectItem));
    }

    public void handleIncomingRequests(String request) {
        if (request.startsWith(RequestType.REQUEST_NEW_ITEM_CREATION)) {

        } else if (request.startsWith(RequestType.REQUEST_ITEM_DELETION)) {

        } else if (request.startsWith(RequestType.REQUEST_ITEMS_JSON_DATA)) {

        }
    }
}


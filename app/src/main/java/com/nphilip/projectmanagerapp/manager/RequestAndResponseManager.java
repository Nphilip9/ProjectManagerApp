package com.nphilip.projectmanagerapp.manager;

import android.content.Context;

import com.google.gson.Gson;
import com.nphilip.projectmanagerapp.client.Client;
import com.nphilip.projectmanagerapp.model.ProjectItem;
import com.nphilip.projectmanagerapp.model.RequestType;

public class RequestAndResponseManager {
    Context context;

    public RequestAndResponseManager(Context context) {
        this.context = context;
    }

    public void createRequest(String requestType) {
        new Client(context).sendMessage(requestType);
    }

    public void createRequest(String requestType, ProjectItem projectItem) {
        new Client(context).sendMessage(requestType + new Gson().toJson(projectItem));
    }

    public void handleIncomingRequests(String request) {
        JSONDataManager jsonDataManager = new JSONDataManager(context);
        Client client = new Client(context);
        if (request.startsWith(RequestType.GET_JSON_DATA.toString())) {
            client.sendMessage(jsonDataManager.loadJSONStringFromSharedPreferences());
        } else if (request.startsWith(RequestType.NEW_ITEM_CREATION.toString())) {
            request = request.replace(RequestType.NEW_ITEM_CREATION.toString(), "");
            jsonDataManager.appendItemToSharedPreferences(new Gson().fromJson(request, ProjectItem.class));
        } else if (request.startsWith(RequestType.ITEM_DELETION.toString())) {
            request = request.replace(RequestType.ITEM_DELETION.toString(), "");
            jsonDataManager.deleteItemFromSharedPreferences(new Gson().fromJson(request, ProjectItem.class));
        }
    }

    public void createRequest() {

    }

    // TODO: ADD REQUEST QUEUE
}

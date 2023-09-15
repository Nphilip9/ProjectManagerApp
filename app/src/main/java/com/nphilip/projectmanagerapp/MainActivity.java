package com.nphilip.projectmanagerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import com.nphilip.projectmanagerapp.adapter.ProjectItemsListAdapter;
import com.nphilip.projectmanagerapp.client.Client;
import com.nphilip.projectmanagerapp.manager.JSONDataManager;
import com.nphilip.projectmanagerapp.manager.RequestAndResponseManager;
import com.nphilip.projectmanagerapp.model.ProjectItem;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class MainActivity extends AppCompatActivity {

    SwipeRefreshLayout activityMain_swipeRefreshLayout_refresh;
    static ListView activityMain_listView_projectItems;

    Client client;

    ArrayList<ProjectItem> projectItems = new ArrayList<>();
    public static ProjectItemsListAdapter adapter;
    public static MainActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;

        client = new Client(this);
        client.connectToServer();

        activityMain_swipeRefreshLayout_refresh = findViewById(R.id.activityMain_swipeRefreshLayout_refresh);
        activityMain_listView_projectItems = findViewById(R.id.activityMain_listView_projectItems);

        init(this);
    }

    public static void init(Context context) {
        adapter = new ProjectItemsListAdapter(getActivity(), new JSONDataManager(getActivity().getApplicationContext()).loadItemsFromSharedPreferences());
        activityMain_listView_projectItems.setAdapter(adapter);
    }

    public static MainActivity getActivity() {
        return activity;
    }
}
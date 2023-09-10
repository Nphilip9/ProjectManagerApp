package com.nphilip.projectmanagerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.nphilip.projectmanagerapp.adapter.ProjectItemsListAdapter;

public class MainActivity extends AppCompatActivity {

    SwipeRefreshLayout activityMain_swipeRefreshLayout_refresh;
    ListView activityMain_listView_projectItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMain_swipeRefreshLayout_refresh = findViewById(R.id.activityMain_swipeRefreshLayout_refresh);
        activityMain_listView_projectItems = findViewById(R.id.activityMain_listView_projectItems);

        ProjectItemsListAdapter adapter = new ProjectItemsListAdapter(this, new String[]{"Text", "Text", "Text"});
        activityMain_listView_projectItems.setAdapter(adapter);
    }
}
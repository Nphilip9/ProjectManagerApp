package com.nphilip.projectmanagerapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nphilip.projectmanagerapp.R;
import com.nphilip.projectmanagerapp.model.ProjectItem;

import java.sql.Array;
import java.util.ArrayList;

public class ProjectItemsListAdapter extends ArrayAdapter<String> {

    Activity context;
    ArrayList<ProjectItem> projectItems;

    public ProjectItemsListAdapter(Activity context, ArrayList<ProjectItem> projectItems) {
        super(context, R.layout.list_view_item);
        this.context = context;
        this.projectItems = projectItems;
    }

    @Override
    public int getCount() {
        return projectItems.size();
    }

    @Override
    public void add(@Nullable String object) {
        super.add(object);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View row = inflater.inflate(R.layout.list_view_item, null, true);
        TextView listViewItem_textView_title = row.findViewById(R.id.listViewItem_textView_title);
        TextView listViewItem_textView_subtitle = row.findViewById(R.id.listViewItem_textView_subtitle);
        listViewItem_textView_title.setText(projectItems.get(position).getTitle());
        listViewItem_textView_subtitle.setText(projectItems.get(position).getCreationDate());
        return row;
    }
}

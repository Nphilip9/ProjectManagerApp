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

public class ProjectItemsListAdapter extends ArrayAdapter<String> {

    Activity context;
    String[] str;

    public ProjectItemsListAdapter(Activity context, String[] str) {
        super(context, R.layout.list_view_item);
        this.context = context;
        this.str = str;
    }

    @Override
    public int getCount() {
        return str.length;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View row = inflater.inflate(R.layout.list_view_item, null, true);
        TextView listViewItem_textView_title = row.findViewById(R.id.listViewItem_textView_title);
        TextView listViewItem_textView_subtitle = row.findViewById(R.id.listViewItem_textView_subtitle);
        listViewItem_textView_title.setText(str[position]);
        listViewItem_textView_subtitle.setText(str[position]);
        return row;
    }
}

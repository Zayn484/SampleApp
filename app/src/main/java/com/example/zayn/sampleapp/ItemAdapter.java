package com.example.zayn.sampleapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter {

    public ItemAdapter(Context context, ArrayList<Item> items, int resource) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listView = convertView;
        if (listView == null){
            listView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false
            );
        }

        Item item = (Item) getItem(position);

        TextView titleTextView = (TextView)listView.findViewById(R.id.title_text_view);
        titleTextView.setText(item.getTitle());

        TextView descriptionTextView = (TextView)listView.findViewById(R.id.description_text_view);
        descriptionTextView.setText(item.getDescription());

        return listView;






    }
}

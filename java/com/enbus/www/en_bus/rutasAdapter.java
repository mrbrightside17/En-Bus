package com.enbus.www.en_bus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class rutasAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<String> names;

    public rutasAdapter( Context context, int layout, List<String> names){
        this.context =context;
        this.layout = layout;
        this.names = names;
    }

    public void updateList(ArrayList<String> results){
        names = new ArrayList<>();
        names.addAll(results);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.names.size();
    }

    @Override
    public Object getItem(int position) {
        return names.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View v = convertView;

        LayoutInflater inflater = LayoutInflater.from(this.context);
        v = inflater.inflate(this.layout,null);

        String currentName = this.names.get(position);

        TextView textView = v.findViewById(R.id.txtRuta);

        textView.setText(currentName);


        return v;
    }
}

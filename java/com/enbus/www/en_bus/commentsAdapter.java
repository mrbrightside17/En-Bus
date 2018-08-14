package com.enbus.www.en_bus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class commentsAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<String> users;
    private List<String> comments;
    private List<String> rates;

    public commentsAdapter(Context context, int layout, List<String> users, List<String> comments, List<String> rates){
        this.context=context;
        this.layout=layout;
        this.comments=comments;
        this.users=users;
        this.rates=rates;
    }

    @Override
    public int getCount() {
        return this.comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v= convertView;

        LayoutInflater inflater = LayoutInflater.from(this.context);
        v = inflater.inflate(this.layout, null);

        String currentUser = this.users.get(position);
        String currentComment = this.comments.get(position);
        String currentRate = this.rates.get(position);

        TextView user = v.findViewById(R.id.textView_user);
        TextView comment = v.findViewById(R.id.textView_comment);
        TextView rate = v.findViewById(R.id.textView_rate);

        user.setText(currentUser);
        comment.setText(currentComment);
        rate.setText(currentRate);

        return v;
    }
}

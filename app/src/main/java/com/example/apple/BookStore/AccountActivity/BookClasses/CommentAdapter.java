package com.example.apple.BookStore.AccountActivity.BookClasses;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apple.BookStore.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by eoghancasey on 16/04/2018.
 */


public class CommentAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<CommentModel> comments;


    public CommentAdapter(Activity activity, List<CommentModel> comments) {
        this.activity = activity;
        this.comments = comments;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int location) {
        return comments.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.listview_comments, null);

        TextView this_comment = (TextView) convertView.findViewById(R.id.old_comments);

        CommentModel comment = comments.get(position);

        this_comment.setText(comment.getComment());

        return convertView;
    }



}

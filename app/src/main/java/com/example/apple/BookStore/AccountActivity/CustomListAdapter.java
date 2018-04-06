package com.example.apple.BookStore.AccountActivity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apple.BookStore.AccountActivity.BookClasses.Book;
import com.example.apple.BookStore.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by eoghancasey on 26/03/2018.
 */

public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Book> bookItems;


    public CustomListAdapter(Activity activity, List<Book> bookItems) {
        this.activity = activity;
        this.bookItems = bookItems;
    }

    @Override
    public int getCount() {
        return bookItems.size();
    }

    @Override
    public Object getItem(int location) {
        return bookItems.get(location);
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
            convertView = inflater.inflate(R.layout.listview_layout, null);

        TextView title = (TextView) convertView.findViewById(R.id.bookTitle);
        TextView author = (TextView) convertView.findViewById(R.id.bookAuthor);

        Book book = bookItems.get(position);

        ImageView image = (ImageView) convertView.findViewById(R.id.imageView);

        String url = (book.getImageURL());

        Picasso.with(activity)
                .load(url)
                .placeholder(R.drawable.common_google_signin_btn_icon_dark_normal_background) // optional
                .error(R.drawable.common_full_open_on_phone)         // optional
                .into(image);

        title.setText(book.getTitle());
        author.setText(book.getAuthor());

        return convertView;
    }



}

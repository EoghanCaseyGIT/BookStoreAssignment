package com.example.apple.BookStore.AccountActivity.BookClasses;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apple.BookStore.R;


/**
 * Created by eoghancasey on 23/03/2018.
 */

public class BookListView extends ArrayAdapter<String>{

    private String[] title;
    private String[] author;
    private Integer[] imageID;
    private Activity context;


    public BookListView(Activity context, String[]title, String[]author, Integer[]imageID){
        super(context, R.layout.listview_layout, title);

        this.context = context;
        this.title = title;
        this.author = author;
        this.imageID = imageID;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        if(convertView == null){

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_layout, parent, false);


        }

        ((TextView)convertView.findViewById(R.id.bookTitle)).setText(title[position]);
        ((TextView)convertView.findViewById(R.id.bookAuthor)).setText(author[position]);
        ((ImageView)convertView.findViewById(R.id.imageView)).setImageResource(imageID[position]);


        return  convertView;
    }


}

package com.example.apple.BookStore.AccountActivity.BookClasses;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.apple.BookStore.AccountActivity.MainActivity;
import com.example.apple.BookStore.R;


import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by eoghancasey on 06/04/2018.
 */

public class SearchBookAdapter extends RecyclerView.Adapter<SearchBookAdapter.SearchViewHolder> {
    Context context;
    ArrayList<String> bookTitleList;
    ArrayList<String> authorNameList;
    ArrayList<String> imageList;

    private List<Book> bookModelList = new ArrayList<Book>();

    class SearchViewHolder extends RecyclerView.ViewHolder {
        ImageView bookImage;
        TextView full_title, author_name;

        public SearchViewHolder(View itemView) {
            super(itemView);
            bookImage = (ImageView) itemView.findViewById(R.id.bookImage);
            full_title = (TextView) itemView.findViewById(R.id.full_title);
            author_name = (TextView) itemView.findViewById(R.id.author_name);
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {

        }
    }

    public SearchBookAdapter(Context context, ArrayList<String> bookTitleList, ArrayList<String> authorNameList, ArrayList<String> imageList, List<Book> bookModelList) {
        this.context = context;
        this.bookTitleList = bookTitleList;
        this.authorNameList = authorNameList;
        this.imageList = imageList;
        this.bookModelList = bookModelList;
    }

    @Override
    public SearchBookAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_book_items, parent, false);
        return new SearchBookAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        holder.full_title.setText(bookTitleList.get(position));
        holder.author_name.setText(authorNameList.get(position));
        Glide.with(context).load(imageList.get(position)).into(holder.bookImage);

        holder.full_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Title Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return bookTitleList.size();
    }
}
package com.example.apple.BookStore.AccountActivity.BookClasses;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.apple.BookStore.R;


import java.util.ArrayList;
import static java.sql.DriverManager.println;

/**
 * Created by eoghancasey on 06/04/2018.
 */

public class SearchBookAdapter extends RecyclerView.Adapter<SearchBookAdapter.SearchViewHolder> {
    Context context;
    ArrayList<String> bookTitleList;
    ArrayList<String> authorNameList;
    ArrayList<String> imageList;
    ArrayList<String> priceList;
    ArrayList<String> stockList;
    ArrayList<String> categoryList;
    ArrayList<String> infoList;



    class SearchViewHolder extends RecyclerView.ViewHolder {
        ImageView bookImage;
        TextView full_title, author_name;

        public SearchViewHolder(View itemView) {
            super(itemView);
            bookImage = (ImageView) itemView.findViewById(R.id.bookImage);
            full_title = (TextView) itemView.findViewById(R.id.full_title);
            author_name = (TextView) itemView.findViewById(R.id.author_name);
        }

    }

    public SearchBookAdapter(Context context, ArrayList<String> bookTitleList, ArrayList<String> authorNameList, ArrayList<String> imageList, ArrayList<String> priceList, ArrayList<String> stockList, ArrayList<String> categoryList, ArrayList<String> infoList ) {
        this.context = context;
        this.bookTitleList = bookTitleList;
        this.authorNameList = authorNameList;
        this.imageList = imageList;
        this.priceList = priceList;
        this.stockList = stockList;
        this.categoryList = categoryList;
        this.infoList = infoList;

        println("stockList");


    }

    @Override
    public SearchBookAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_book_items, parent, false);
        return new SearchBookAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, final int position) {
        holder.full_title.setText(bookTitleList.get(position));
        holder.author_name.setText(authorNameList.get(position));
        Glide.with(context).load(imageList.get(position)).into(holder.bookImage);

        holder.full_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(v.getContext(), BookIndexed.class);
                i.putExtra("ValueKey", bookTitleList.get(position));
                i.putExtra("ValueKey2", authorNameList.get(position));
                i.putExtra("ValueKey3", priceList.get(position));
                i.putExtra("ValueKey4", categoryList.get(position));
                i.putExtra("ValueKey5", stockList.get(position));
                i.putExtra("ValueKey6", infoList.get(position));
                i.putExtra("ValueKey7", imageList.get(position));

                context.startActivity(i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return bookTitleList.size();
    }
}
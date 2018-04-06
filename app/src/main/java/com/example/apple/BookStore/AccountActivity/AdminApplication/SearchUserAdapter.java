package com.example.apple.BookStore.AccountActivity.AdminApplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.apple.BookStore.R;

import java.util.ArrayList;

/**
 * Created by eoghancasey on 06/04/2018.
 */

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.SearchViewHolder> {
    Context context;
    ArrayList<String> userNameList;

    class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView user_name;

        public SearchViewHolder(View itemView) {
            super(itemView);

            user_name = (TextView) itemView.findViewById(R.id.user_name);
        }
    }

    public SearchUserAdapter(Context context, ArrayList<String> userNameList) {
        this.context = context;
        this.userNameList = userNameList;
    }

    @Override
    public SearchUserAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_list_items, parent, false);
        return new SearchUserAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        holder.user_name.setText(userNameList.get(position));

        holder.user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "User Name Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userNameList.size();
    }
}


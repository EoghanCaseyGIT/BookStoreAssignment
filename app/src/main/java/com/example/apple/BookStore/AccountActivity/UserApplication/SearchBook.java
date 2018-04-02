package com.example.apple.BookStore.AccountActivity.UserApplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.apple.BookStore.AccountActivity.BookClasses.Book;
import com.example.apple.BookStore.AccountActivity.Login;
import com.example.apple.BookStore.AccountActivity.MainActivity;
import com.example.apple.BookStore.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import static com.example.apple.BookStore.R.layout.*;
import static com.example.apple.BookStore.R.layout.activity_searchbook;

/**
 * Created by eoghancasey on 02/04/2018.
 */

public class SearchBook extends AppCompatActivity {

    private DatabaseReference bookDatabase;

    private RecyclerView mResultList;
    private Button search;
    private EditText searchField;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_searchbook);




        bookDatabase = FirebaseDatabase.getInstance().getReference("All_Books");

        searchField = (EditText) findViewById(R.id.book_Title);
        search = (Button) findViewById(R.id.searchBookButton);

        mResultList = (RecyclerView) findViewById(R.id.result_List);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));


        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                searchBooks();

            }
        });
    }

    private void searchBooks(){
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("All_Books");
        Query query = ref.orderByChild("All_Books").equalTo(searchField.toString());

        FirebaseRecyclerOptions<Book> options =
                new FirebaseRecyclerOptions.Builder<Book>()
                        .setQuery(query, Book.class)
                        .build();

        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Book, BookViewHolder>(options) {
            @Override
            public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listview_booksearchresults, parent, false);

                return new BookViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(BookViewHolder holder, int position, Book model) {
                holder.setDetails(model.getTitle(), model.getAuthor(), model.getImageURL());

            }
        };

        mResultList.setAdapter(adapter);
    }


    public class BookViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public BookViewHolder(View itemView){
            super(itemView);

            mView = itemView;
        }

        public void setDetails(String title, String author, String imageURL){

            TextView bookTitle = (TextView) findViewById(R.id.book_title);
            TextView bookAuthor = (TextView) findViewById(R.id.book_author);

            ImageView bookImage = (ImageView) findViewById(R.id.book_image);

            bookTitle.setText(title);
            bookAuthor.setText(author);

            Glide.with(getApplicationContext()).load(imageURL).into(bookImage);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.main:

                Intent mainFeed = new Intent(this, MainActivity.class);
                this.startActivity(mainFeed);
                return true;
            case R.id.profile:
                Intent profileIntent = new Intent(this, UserAccount.class);
                this.startActivity(profileIntent);
                return true;
            case R.id.accountInformation:
                Intent detailsIntent = new Intent(this, UserDetails.class);
                this.startActivity(detailsIntent);
                return true;
            case R.id.logout:
                Intent logoutIntent = new Intent(this, Login.class);
                this.startActivity(logoutIntent);
                return true;



            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


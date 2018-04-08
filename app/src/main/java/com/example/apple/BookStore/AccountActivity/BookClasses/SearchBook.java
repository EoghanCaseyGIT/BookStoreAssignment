
package com.example.apple.BookStore.AccountActivity.BookClasses;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.apple.BookStore.AccountActivity.AdminApplication.SearchUser;
import com.example.apple.BookStore.AccountActivity.AdminApplication.SearchUserAdapter;
import com.example.apple.BookStore.AccountActivity.BookClasses.Book;
import com.example.apple.BookStore.AccountActivity.Login;
import com.example.apple.BookStore.AccountActivity.MainActivity;
import com.example.apple.BookStore.AccountActivity.UserApplication.UserAccount;
import com.example.apple.BookStore.AccountActivity.UserApplication.UserDetails;
import com.example.apple.BookStore.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.apple.BookStore.R.layout.*;
import static com.example.apple.BookStore.R.layout.activity_searchbook;

/**
 * Created by eoghancasey on 02/04/2018.
 */

public class SearchBook extends AppCompatActivity {

    EditText search_edit_text;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    ArrayList<String> bookTitleList;
    ArrayList<String> authorNameList;
    ArrayList<String> imageList;
    SearchBookAdapter searchAdapter;

    private List<Book> bookModelList = new ArrayList<Book>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchbook);

        search_edit_text = (EditText) findViewById(R.id.search_edit_text);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        /*
        * Create a array list for each node you want to use
        * */
        bookTitleList = new ArrayList<>();
        authorNameList = new ArrayList<>();
        imageList = new ArrayList<>();

        search_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    setAdapter(s.toString());
                } else {
                    bookTitleList.clear();
                    authorNameList.clear();
                    imageList.clear();
                    recyclerView.removeAllViews();
                }
            }
        });
    }

    private void setAdapter(final String searchedString) {
        databaseReference.child("All_Books").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                bookTitleList.clear();
                authorNameList.clear();
                imageList.clear();
                recyclerView.removeAllViews();

                int counter = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uid = snapshot.getKey();
                    String full_title = snapshot.child("title").getValue(String.class);
                    String author_name = snapshot.child("author").getValue(String.class);
                    String bookImage = snapshot.child("imageURL").getValue(String.class);

                    if (full_title.toLowerCase().contains(searchedString.toLowerCase())) {
                        bookTitleList.add(full_title);
                        authorNameList.add(author_name);
                        imageList.add(bookImage);
                        counter++;
                    } else if (full_title.toLowerCase().contains(searchedString.toLowerCase())) {
                        bookTitleList.add(full_title);
                        authorNameList.add(author_name);
                        imageList.add(bookImage);
                        counter++;
                    }
                    if (counter == 15)
                        break;
                }

                searchAdapter = new SearchBookAdapter(SearchBook.this, bookTitleList, authorNameList, imageList, bookModelList);
                recyclerView.setAdapter(searchAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

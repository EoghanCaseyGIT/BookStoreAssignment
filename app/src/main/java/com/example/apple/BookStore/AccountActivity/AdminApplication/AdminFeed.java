package com.example.apple.BookStore.AccountActivity.AdminApplication;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;


import com.example.apple.BookStore.AccountActivity.BookClasses.Book;
import com.example.apple.BookStore.AccountActivity.BookClasses.BookListView;
import com.example.apple.BookStore.AccountActivity.CustomListAdapter;
import com.example.apple.BookStore.AccountActivity.Login;
import com.example.apple.BookStore.AccountActivity.UserApplication.UserAccount;
import com.example.apple.BookStore.R;
import com.google.android.gms.common.api.Response;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eoghancasey on 21/03/2018.
 */

public class AdminFeed extends AppCompatActivity {


    ListView list;
    String[] bookTitle = {"Harry Potter", "Lord of The Rings", "The Bible"};
    String[] bookAuthor = {"J.K Rowling", "J.R.R Tolkien", "Jesus"};
    Integer[] imageID = {R.drawable.harrypotterimage, R.drawable.lordoftherings, R.drawable.gandalf};

    private CustomListAdapter adapter;
    private List<Book> bookModelList = new ArrayList<Book>();




    final FirebaseDatabase database = FirebaseDatabase.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminfeed);

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://bookstore-30213.firebaseio.com/All_Books");

        final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(this, R.layout.listview_layout);

        populateList();


        }

    public void populateList() {


        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference allBooksRef = rootRef.child("All_Books");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Book> bookModelList = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Book book = ds.getValue(Book.class);
                    bookModelList.add(book);
                }
                ListView list = (ListView) findViewById(R.id.listView);
                CustomListAdapter adapter = new CustomListAdapter(AdminFeed.this, bookModelList);
                list.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        allBooksRef.addListenerForSingleValueEvent(valueEventListener);
    }



//        BookListView bookListView = new BookListView(AdminFeed.this, bookTitle, bookAuthor, imageID);
//        list.setAdapter(bookListView);


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.main:
                Toast.makeText(this, "Test", Toast.LENGTH_LONG).show();
                Intent adminFeed = new Intent(this, AdminFeed.class);
                this.startActivity(adminFeed);
                return true;
            case R.id.addBook:
                Intent addBookIntent = new Intent(this, BookUpload.class);
                this.startActivity(addBookIntent);
                return true;

            case R.id.updateBook:
                Intent updateBookIntent = new Intent(this, UpdateBook.class);
                this.startActivity(updateBookIntent);
                return true;
            case R.id.searchUser:
                Intent searchUserIntent = new Intent(this, SearchUser.class);
                this.startActivity(searchUserIntent);
                return true;

            case R.id.account:
                Intent profileIntent = new Intent(this, UserAccount.class);
                this.startActivity(profileIntent);
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

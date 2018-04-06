package com.example.apple.BookStore.AccountActivity.AdminApplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


import com.example.apple.BookStore.AccountActivity.BookClasses.Book;
import com.example.apple.BookStore.AccountActivity.BookClasses.BookIndexed;
import com.example.apple.BookStore.AccountActivity.CustomListAdapter;
import com.example.apple.BookStore.AccountActivity.Login;
import com.example.apple.BookStore.AccountActivity.UserApplication.UserAccount;
import com.example.apple.BookStore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by eoghancasey on 21/03/2018.
 */

public class AdminFeed extends AppCompatActivity {

    String title, author;

    private CustomListAdapter adapter;
    private List<Book> bookModelList = new ArrayList<Book>();
    private ListView list;


    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminfeed);

        list = (ListView) findViewById(R.id.bookListView);
        adapter = new CustomListAdapter(AdminFeed.this, bookModelList);

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
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Book book = ds.getValue(Book.class);

                    bookModelList.add(book);
                    list.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        allBooksRef.addListenerForSingleValueEvent(valueEventListener);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent i = new Intent(AdminFeed.this, AdminBookIndex.class);
                i.putExtra("ValueKey", bookModelList.get(position).getTitle());
                i.putExtra("ValueKey2", bookModelList.get(position).getAuthor());
                i.putExtra("ValueKey3", bookModelList.get(position).getPrice());
                i.putExtra("ValueKey4", bookModelList.get(position).getCategory());
                i.putExtra("ValueKey5", bookModelList.get(position).getStock());
                i.putExtra("ValueKey6", bookModelList.get(position).getInfo());
                i.putExtra("ValueKey7", bookModelList.get(position).getImageURL());
                startActivity(i);

                title = bookModelList.get(position).getTitle();
                author = bookModelList.get(position).getAuthor();
            }
        });
    }

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
                Intent adminFeed = new Intent(this, AdminFeed.class);
                this.startActivity(adminFeed);
                return true;
            case R.id.addBook:
                Intent addBookIntent = new Intent(this, BookUpload.class);
                this.startActivity(addBookIntent);
                return true;

            case R.id.searchUser:
                Intent searchUserIntent = new Intent(this, SearchUser.class);
                this.startActivity(searchUserIntent);
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

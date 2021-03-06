package com.example.apple.BookStore.AccountActivity.UserApplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apple.BookStore.AccountActivity.AdminApplication.AdminFeed;
import com.example.apple.BookStore.AccountActivity.AdminApplication.SearchUser;
import com.example.apple.BookStore.AccountActivity.BookClasses.Book;
import com.example.apple.BookStore.AccountActivity.CustomListAdapter;
import com.example.apple.BookStore.AccountActivity.Login;
import com.example.apple.BookStore.AccountActivity.OrderClasses.OrderListAdapter;
import com.example.apple.BookStore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by eoghancasey on 10/04/2018.
 */

public class UserIndex extends AppCompatActivity {

    private TextView username, address, cardNum;
    private OrderListAdapter adapter;
    private List<Book> orders = new ArrayList<Book>();
    private ListView list;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userindex);
        list = (ListView) findViewById(R.id.orderListView);
        adapter = new OrderListAdapter(UserIndex.this, orders);
        Intent intent = getIntent();
        final String userName = intent.getExtras().getString("ValueKey");
        final String userAddress = intent.getExtras().getString("ValueKey2");
        final String cardNumber = intent.getExtras().getString("ValueKey3");

        username = (TextView) findViewById(R.id.username_textfield);
        address = (TextView) findViewById(R.id.address_textfield);
        cardNum = (TextView) findViewById(R.id.card_number_textfield);
        username.setText(userName);
        address.setText(userAddress);
        cardNum.setText(cardNumber);

        //Connecting to my DB and retrieving the correct orders
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference allBooksRef = rootRef.child("All_Orders").child(userName).child("orders").child("bookList");
        allBooksRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(final com.google.firebase.database.DataSnapshot dataSnapshot) {
                orders.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String title = postSnapshot.child("title").getValue(String.class);
                    String imageURL = postSnapshot.child("imageURL").getValue(String.class);
                    Book bookList = new Book();
                    bookList.setTitle(title);
                    bookList.setImageURL(imageURL);
                    orders.add(bookList);
                }
                if (orders.size() == 0) {
                    Toast.makeText(getApplicationContext(), "This user has no orders.", Toast.LENGTH_LONG).show();
                }
                OrderListAdapter customListAdapter = new OrderListAdapter(UserIndex.this, orders);
                list.setAdapter(customListAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

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

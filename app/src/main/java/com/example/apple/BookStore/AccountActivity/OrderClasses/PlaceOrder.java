package com.example.apple.BookStore.AccountActivity.OrderClasses;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apple.BookStore.AccountActivity.BookClasses.Book;
import com.example.apple.BookStore.AccountActivity.BookClasses.SearchBook;
import com.example.apple.BookStore.AccountActivity.CustomListAdapter;
import com.example.apple.BookStore.AccountActivity.Login;
import com.example.apple.BookStore.AccountActivity.MainActivity;
import com.example.apple.BookStore.AccountActivity.UserApplication.UserAccount;
import com.example.apple.BookStore.AccountActivity.UserApplication.UserDetails;
import com.example.apple.BookStore.AccountActivity.UserApplication.UserDetailsModel;
import com.example.apple.BookStore.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import static com.example.apple.BookStore.AccountActivity.MainActivity.bookList;

/**
 * Created by eoghancasey on 28/03/2018.
 */

public class PlaceOrder extends AppCompatActivity {


    private CustomListAdapter adapter;
    private ListView list;
    Button purchase, clear;
    private EditText username;
    String name;
    boolean success = true;
    String Database_Path = "All_Orders";
    private FirebaseAuth auth;
    List<Book> books = new ArrayList<Book>();
    Order order = new Order(books);

    ProgressDialog progressDialog;

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);


        clear = (Button) findViewById(R.id.clearOrder);


        progressDialog = new ProgressDialog(PlaceOrder.this);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        final ArrayList<Book> bookList = (ArrayList<Book>) args.getSerializable("ARRAYLIST");

        list = (ListView) findViewById(R.id.bookListView);
        adapter = new CustomListAdapter(PlaceOrder.this, bookList);


        username = (EditText) findViewById(R.id.usernameText);
        list.setAdapter(adapter);
        purchase = (Button) findViewById(R.id.confirm_button);


        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                name = username.getText().toString().trim();

                if(name.isEmpty() || name.length() == 0 || name.equals("") || name == null){
                    success = false;
                    Toast.makeText(getApplicationContext(), "You must enter a username.", Toast.LENGTH_LONG).show();
                } else {
                    String name = username.getText().toString().trim();

                    Order order = new Order(bookList);
                    databaseReference.child(name).child("orders").setValue(order);
                    progressDialog.dismiss();

                    Toast.makeText(getApplicationContext(), "Your order has been placed, thank you!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(PlaceOrder.this, MainActivity.class));
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookList.clear();
                Toast.makeText(getApplicationContext(), "Your cart has been cleared, start shopping again!", Toast.LENGTH_LONG).show();
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

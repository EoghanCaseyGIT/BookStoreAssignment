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
import android.widget.Toast;

import com.example.apple.BookStore.AccountActivity.BookClasses.Book;
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

/**
 * Created by eoghancasey on 28/03/2018.
 */

public class PlaceOrder extends AppCompatActivity {


    private CustomListAdapter adapter;
    private ListView list;
    Button purchase;

    String username;

    Integer orderNum;


    String Database_Path = "All_Orders";
    private FirebaseAuth auth;

    ProgressDialog progressDialog;

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);
        final String user = FirebaseAuth.getInstance().getCurrentUser().getEmail();


        progressDialog = new ProgressDialog(PlaceOrder.this);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        final ArrayList<Book> cartBooks = (ArrayList<Book>) args.getSerializable("ARRAYLIST");

        list = (ListView) findViewById(R.id.bookListView);
        adapter = new CustomListAdapter(PlaceOrder.this, cartBooks);

        final ArrayList<Book> orderedBooksList = (ArrayList<Book>)cartBooks.clone();

        list.setAdapter(adapter);

        purchase = (Button) findViewById(R.id.confirm_button);

        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String UserID = FirebaseAuth.getInstance().getCurrentUser().getUid();



                Order order = new Order(UserID, orderedBooksList);

                databaseReference.child("orders").child(UserID).setValue(order);
                progressDialog.dismiss();

                Toast.makeText(getApplicationContext(), "Your order has been placed, thank you!", Toast.LENGTH_LONG).show();
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

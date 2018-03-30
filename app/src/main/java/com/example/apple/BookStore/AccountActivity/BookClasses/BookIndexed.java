package com.example.apple.BookStore.AccountActivity.BookClasses;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apple.BookStore.AccountActivity.AdminApplication.AdminFeed;
import com.example.apple.BookStore.AccountActivity.AdminApplication.BookUpload;
import com.example.apple.BookStore.AccountActivity.AdminApplication.SearchUser;
import com.example.apple.BookStore.AccountActivity.AdminApplication.UpdateBook;
import com.example.apple.BookStore.AccountActivity.Login;
import com.example.apple.BookStore.AccountActivity.MainActivity;
import com.example.apple.BookStore.AccountActivity.OrderClasses.PlaceOrder;
import com.example.apple.BookStore.AccountActivity.UserApplication.UserAccount;
import com.example.apple.BookStore.AccountActivity.UserApplication.UserDetails;
import com.example.apple.BookStore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eoghancasey on 27/03/2018.
 */

public class BookIndexed extends AppCompatActivity {

    private TextView title, author, price, category, stock, info;
    private Button addToCart, checkout;
    public ArrayList<Book> cartBooks = new ArrayList<Book>();

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookindex);

        Intent intent = getIntent();

        final String bookTitle = intent.getExtras().getString("ValueKey");
        final String bookAuthor = intent.getExtras().getString("ValueKey2");
        final String bookPrice = intent.getExtras().getString("ValueKey3");
        final String bookCategory = intent.getExtras().getString("ValueKey4");
        final String bookStock = intent.getExtras().getString("ValueKey5");
        final String bookInfo = intent.getExtras().getString("ValueKey6");
        final String bookImage = intent.getExtras().getString("ValueKey7");

        addToCart = (Button) findViewById(R.id.buy_button);
        checkout = (Button) findViewById(R.id.checkout_Button);

        progressDialog = new ProgressDialog(BookIndexed.this);



        final ImageView image = (ImageView) findViewById(R.id.book_image);

        Picasso.with(this)
                .load(bookImage)
                .into(image);


        title = (TextView) findViewById(R.id.book_title);
        author = (TextView) findViewById(R.id.book_author);
        price = (TextView) findViewById(R.id.book_price);
        category = (TextView) findViewById(R.id.book_category);
        stock = (TextView) findViewById(R.id.book_stock);
        info = (TextView) findViewById(R.id.book_information);


        title.setText(bookTitle);
        author.setText(bookAuthor);
        price.setText(bookPrice);
        category.setText(bookCategory);
        stock.setText(bookStock);
        info.setText(bookInfo);


        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Book book = new Book(bookTitle, bookAuthor, bookCategory, bookPrice, bookStock, bookInfo, bookImage);

                cartBooks.add(book);

                Toast.makeText(getApplicationContext(), "This book has been added to your cart!", Toast.LENGTH_LONG).show();


            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent;
                intent = new Intent(BookIndexed.this, PlaceOrder.class);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable) cartBooks);
                intent.putExtra("BUNDLE",args);


                startActivity(intent);


            }
        });


    }




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


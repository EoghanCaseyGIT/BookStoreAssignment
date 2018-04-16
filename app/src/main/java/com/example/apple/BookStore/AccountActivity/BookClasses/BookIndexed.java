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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apple.BookStore.AccountActivity.Login;
import com.example.apple.BookStore.AccountActivity.MainActivity;
import com.example.apple.BookStore.AccountActivity.OrderClasses.Order;
import com.example.apple.BookStore.AccountActivity.OrderClasses.PlaceOrder;
import com.example.apple.BookStore.AccountActivity.UserApplication.UserAccount;
import com.example.apple.BookStore.AccountActivity.UserApplication.UserDetails;
import com.example.apple.BookStore.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.example.apple.BookStore.AccountActivity.MainActivity.bookList;

/**
 * Created by eoghancasey on 27/03/2018.
 */

public class BookIndexed extends AppCompatActivity {

    private TextView title, author, price, category, stock, info;
    String userComment, ratingValue;
    private Button addToCart, checkout, review;
    private EditText comment, rating;


    String Database_Path = "All_Comments";
    private FirebaseAuth auth;

    ProgressDialog progressDialog;

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookindex);

        comment = (EditText) findViewById(R.id.comment_Text);
        rating = (EditText) findViewById(R.id.book_rating);
        review = (Button) findViewById(R.id.review_Button);


        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        //Below this is getting the book information and populating the textView

        Intent intent = getIntent();

        final String bookTitle = intent.getExtras().getString("ValueKey");
        final String bookAuthor = intent.getExtras().getString("ValueKey2");
        final String bookPrice = intent.getExtras().getString("ValueKey3");
        final String bookCategory = intent.getExtras().getString("ValueKey4");
        final String bookStock = intent.getExtras().getString("ValueKey5");
        final String bookInfo = intent.getExtras().getString("ValueKey6");
        final String bookImage = intent.getExtras().getString("ValueKey7");

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

        addToCart = (Button) findViewById(R.id.buy_button);
        checkout = (Button) findViewById(R.id.checkout_Button);

        progressDialog = new ProgressDialog(BookIndexed.this);
        final ImageView image = (ImageView) findViewById(R.id.book_image);

        Picasso.with(this)
                .load(bookImage)
                .into(image);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Book book = new Book(bookImage, bookTitle, bookAuthor, bookCategory, bookPrice, bookStock, bookInfo);

                bookList.add(book);
                Toast.makeText(getApplicationContext(), "This book has been added to your cart!", Toast.LENGTH_LONG).show();
            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent;
                intent = new Intent(BookIndexed.this, PlaceOrder.class);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable) bookList);
                intent.putExtra("BUNDLE",args);
                startActivity(intent);
            }
        });
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userComment = comment.getText().toString().trim();
                ratingValue = rating.getText().toString().trim();

                databaseReference.child(bookTitle).child("Comment:").setValue("Comment: " + userComment + ". Rating: " + ratingValue);
                Toast.makeText(getApplicationContext(), "Your Comment & Rating have been saved and added!", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
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


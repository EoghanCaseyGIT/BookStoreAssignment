package com.example.apple.BookStore.AccountActivity.AdminApplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apple.BookStore.AccountActivity.Login;
import com.example.apple.BookStore.AccountActivity.UserApplication.UserAccount;
import com.example.apple.BookStore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Created by eoghancasey on 28/03/2018.
 */

public class AdminBookIndex extends AppCompatActivity {


    private TextView title, author, price, category, stock, info;
    private Button purchase, edit, delete;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminbookindex);

        Intent intent = getIntent();

        String bookTitle = intent.getExtras().getString("ValueKey");
        String bookAuthor = intent.getExtras().getString("ValueKey2");
        String bookPrice = intent.getExtras().getString("ValueKey3");
        String bookCategory = intent.getExtras().getString("ValueKey4");
        String bookStock = intent.getExtras().getString("ValueKey5");
        String bookInfo = intent.getExtras().getString("ValueKey6");
        String bookImage = intent.getExtras().getString("ValueKey7");

        purchase = (Button) findViewById(R.id.buy_button);
        edit = (Button) findViewById(R.id.edit_button);
        delete = (Button) findViewById(R.id.delete_Button);


        ImageView image = (ImageView) findViewById(R.id.book_image);

        Picasso.with(this)
                .load(bookImage)
//                .placeholder(R.drawable.common_google_signin_btn_icon_dark_normal_background) // optional
//                .error(R.drawable.common_full_open_on_phone)         // optional
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


        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //              startActivity(new Intent(EventIndexActivity.this, EventFeed.class));
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

          deleteItem();

            }
        });
    }

    public void deleteItem(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query bookQuery = ref.child("All_Books").orderByChild("title").equalTo(title.getText().toString());

        bookQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot bookSnapshot: dataSnapshot.getChildren()) {
                    bookSnapshot.getRef().removeValue();
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        Toast.makeText(AdminBookIndex.this, "This book has been deleted and all stock levels.", Toast.LENGTH_LONG).show();
//        Intent adminFeed = new Intent(this, AdminFeed.class);
//        this.startActivity(adminFeed);
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
package com.example.apple.BookStore.AccountActivity.AdminApplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.apple.BookStore.AccountActivity.BookClasses.BookListView;
import com.example.apple.BookStore.AccountActivity.Login;
import com.example.apple.BookStore.AccountActivity.UserApplication.UserAccount;
import com.example.apple.BookStore.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by eoghancasey on 21/03/2018.
 */

public class AdminFeed extends AppCompatActivity {


    ListView list;
    String[] bookTitle = {"Harry Potter", "Lord of The Rings", "The Bible"};
    String[] bookAuthor = {"J.K Rowling", "J.R.R Tolkien", "Jesus"};
    Integer[] imageID = {R.drawable.harrypotterimage, R.drawable.lordoftherings, R.drawable.gandalf};

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("server/saving-data/fireblog/posts");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminfeed);


        list = (ListView) findViewById(R.id.listView);
        BookListView bookListView = new BookListView(AdminFeed.this, bookTitle, bookAuthor, imageID);
        list.setAdapter(bookListView);

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

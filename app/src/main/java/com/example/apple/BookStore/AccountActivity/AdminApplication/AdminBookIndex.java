package com.example.apple.BookStore.AccountActivity.AdminApplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apple.BookStore.AccountActivity.BookClasses.Book;
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

    String oldTitle, oldImage, oldAuthor, oldPrice, oldCategory, oldInformation, oldStock;


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

        oldTitle = bookTitle;
        oldImage = bookImage;
        oldAuthor = bookAuthor;
        oldPrice = bookPrice;
        oldCategory = bookCategory;
        oldInformation = bookInfo;
        oldStock = bookStock;


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

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showUpdateDialog(title.getText().toString().trim());

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

    private boolean update(String imageURL, String title, String author, String stock, String price, String category, String info){


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("All_Books").child(oldTitle);

        Book book = new Book(oldImage, title, author, stock, price, category, info);

        databaseReference.setValue(book);

        Toast.makeText(this, "Book Updated.", Toast.LENGTH_LONG).show();

        return true;

    }

    private void showUpdateDialog(String title){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.layout_updatedialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTitle = (EditText) dialogView.findViewById(R.id.newTitle);
        final EditText editAuthor = (EditText) dialogView.findViewById(R.id.newAuthor);
        final EditText editStock = (EditText) dialogView.findViewById(R.id.newStock);
        final EditText editPrice = (EditText) dialogView.findViewById(R.id.newPrice);
        final EditText editCategory = (EditText) dialogView.findViewById(R.id.newCategory);
        final EditText editInformation = (EditText) dialogView.findViewById(R.id.newInformation);

        editTitle.setText(oldTitle);
        editAuthor.setText(oldAuthor);
        editStock.setText(oldStock);
        editPrice.setText(oldPrice);
        editCategory.setText(oldCategory);
        editInformation.setText(oldInformation);




        Button buttonUpdate = (Button) dialogView.findViewById(R.id.newUpdateButton);


//        dialogBuilder.setTitle("Update Book:");

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newTitle = editTitle.getText().toString().trim();
                String newAuthor = editAuthor.getText().toString().trim();
                String newStock = editStock.getText().toString().trim();
                String newPrice = editPrice.getText().toString().trim();
                String newCategory = editCategory.getText().toString().trim();
                String newInfo = editInformation.getText().toString().trim();

                if(!TextUtils.isEmpty(newTitle)){
                    update(oldImage, newTitle, newAuthor, newPrice, newCategory, newStock, newInfo);
                    alertDialog.dismiss();
                }



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

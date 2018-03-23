package com.example.apple.BookStore.AccountActivity.AdminApplication;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.apple.BookStore.AccountActivity.Login;
import com.example.apple.BookStore.AccountActivity.UserApplication.UserAccount;
import com.example.apple.BookStore.AccountActivity.BookClasses.Book;
import com.example.apple.BookStore.R;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.IOException;


/**
 * Created by eoghancasey on 20/03/2018.
 */

public class BookUpload extends AppCompatActivity {

    String Storage_Path = "All_Book_Images";

    String Database_Path = "All_Books";

    Uri FilePathUri;

    StorageReference storageReference;
    DatabaseReference databaseReference;

    int Image_Request_Code = 71;

    ProgressDialog progressDialog;

    Button imageButton, saveButton;
    EditText title, author, price, category, additionalInfo;

    ImageView SelectImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookupload);

        storageReference = FirebaseStorage.getInstance().getReference();

        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);

        //Assigning ID's to our buttons/text fields
        SelectImage = (ImageView) findViewById(R.id.book_image);

        imageButton = (Button) findViewById(R.id.image_button);
        saveButton = (Button) findViewById(R.id.add_button);

        title = (EditText) findViewById(R.id.title_text);
        author = (EditText) findViewById(R.id.author_text);
        price = (EditText) findViewById(R.id.price_text);
        category = (EditText) findViewById(R.id.category_text);
        additionalInfo = (EditText) findViewById(R.id.info_text);

        progressDialog = new ProgressDialog(BookUpload.this);


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent();


                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);

            }
        });



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                UploadImageFileToFirebaseStorage();

            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);


                SelectImage.setImageBitmap(bitmap);

            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }


    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }


    public void UploadImageFileToFirebaseStorage() {

        // Checking whether FilePathUri Is empty or not.
        if (FilePathUri != null) {

            // Setting progressDialog Title.
            progressDialog.setTitle("Saving Book");

            // Showing progressDialog.
            progressDialog.show();

            // Creating second StorageReference.
            StorageReference storageReference2nd = storageReference.child(Storage_Path).child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));


            // Adding addOnSuccessListener to second StorageReference.
            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // Getting image name from EditText and store into string variable.
                            String Title = title.getText().toString().trim();
                            String Author = author.getText().toString().trim();
                            String Price = price.getText().toString().trim();
                            String Category = category.getText().toString().trim();
                            String info = additionalInfo.getText().toString().trim();

                            // Hiding the progressDialog after done uploading.
                            progressDialog.dismiss();

                            // Showing toast message after done uploading.
                            Toast.makeText(getApplicationContext(), "Book has been added successfully ", Toast.LENGTH_LONG).show();

                            @SuppressWarnings("VisibleForTests")
                            Book book = new Book(taskSnapshot.getDownloadUrl().toString(), Title, Author, Price, Category, info);

                            // Getting image upload ID.
                            String ImageUploadId = databaseReference.push().getKey();

                            // Adding image upload id s child element into databaseReference.
                            databaseReference.child(ImageUploadId).setValue(book);
                        }
                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            // Hiding the progressDialog.
                            progressDialog.dismiss();

                            // Showing exception erro message.
                            Toast.makeText(BookUpload.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })

                    // On progress change upload time.
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            // Setting progressDialog Title.
                            progressDialog.setTitle("Saving Book");

                        }
                    });
        }
        else {

            Toast.makeText(BookUpload.this, "Please fill out all relevant fields.", Toast.LENGTH_LONG).show();

        }
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

            case R.id.profile:
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






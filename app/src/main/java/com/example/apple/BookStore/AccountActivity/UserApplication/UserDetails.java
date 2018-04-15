package com.example.apple.BookStore.AccountActivity.UserApplication;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apple.BookStore.AccountActivity.Login;
import com.example.apple.BookStore.AccountActivity.MainActivity;
import com.example.apple.BookStore.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by eoghancasey on 23/03/2018.
 */

public class UserDetails extends AppCompatActivity{

    String Database_Path = "User_Information";
    private FirebaseAuth auth;
    ProgressDialog progressDialog;
    boolean success = true;
    EditText username, address, cardNum, cardDate;

    DatabaseReference databaseReference;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails);

        auth = FirebaseAuth.getInstance();


        address = (EditText) findViewById(R.id.address);
        cardNum = (EditText) findViewById(R.id.cardNumber);
        cardDate = (EditText) findViewById(R.id.cardDate);
        username = (EditText) findViewById(R.id.username_Text);


        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);



        progressDialog = new ProgressDialog(UserDetails.this);


        Button Save = (Button) findViewById(R.id.saveDetails);


        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setTitle("Saving your information..");
                progressDialog.show();

                saveUserDetails();
            }
        });

    }

    public void saveUserDetails(){


        String UserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String Username = username.getText().toString().trim();
        String Address = address.getText().toString().trim();
        String CardNum = cardNum.getText().toString().trim();
        String CardDate = cardDate.getText().toString().trim();

        if(CardNum.isEmpty() || CardNum.length() == 0 || CardNum.equals("") || CardNum == null){
            success = false;
            Toast.makeText(getApplicationContext(), "You must enter a card number.", Toast.LENGTH_LONG).show();
        }
        if(CardDate.isEmpty() || CardDate.length() == 0 || CardDate.equals("") || CardDate == null){
            success = false;
            Toast.makeText(getApplicationContext(), "You must enter a card expiry date.", Toast.LENGTH_LONG).show();
        }
        if(CardNum.length() < 12) {
            success = false;
            Toast.makeText(getApplicationContext(), "Enter a 16 digit card number.", Toast.LENGTH_LONG).show();
        }
        if(CardDate.length() != 5){
            success = false;
            Toast.makeText(getApplicationContext(), "Enter a valid date. XX/XX format only.", Toast.LENGTH_LONG).show();
        }

        else {
            progressDialog.setTitle("Saving your information..");
            progressDialog.show();
            UserDetailsModel user = new UserDetailsModel(UserID, Username, Address, CardNum, CardDate);
            databaseReference.child(UserID).setValue(user);
            progressDialog.dismiss();

            startActivity(new Intent(UserDetails.this, MainActivity.class));
        }
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

package com.example.apple.BookStore.AccountActivity.UserApplication;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    EditText address, cardNum, cardDate;

    DatabaseReference databaseReference;





    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails);

        auth = FirebaseAuth.getInstance();


        address = (EditText) findViewById(R.id.address);
        cardNum = (EditText) findViewById(R.id.cardNumber);
        cardDate = (EditText) findViewById(R.id.cardDate);


        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);



        progressDialog = new ProgressDialog(UserDetails.this);


        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        final EditText Address = (EditText) findViewById(R.id.address);
        final EditText CardNumber = (EditText) findViewById(R.id.cardNumber);
        final EditText CardDate = (EditText) findViewById(R.id.cardDate);

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
        String Address = address.getText().toString().trim();
        String CardNum = cardNum.getText().toString().trim();
        String CardDate = cardDate.getText().toString().trim();

        progressDialog.dismiss();

        UserDetailsModel user = new UserDetailsModel(UserID, Address, CardNum, CardDate);


        databaseReference.child(UserID).setValue(user);


        startActivity(new Intent(UserDetails.this, MainActivity.class));


    }



}

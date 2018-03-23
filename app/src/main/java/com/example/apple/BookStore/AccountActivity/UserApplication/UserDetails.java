package com.example.apple.BookStore.AccountActivity.UserApplication;

import android.content.Intent;

import android.os.Bundle;

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

    String Database_Path = "UserInformation";
    private FirebaseAuth auth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails);

        auth = FirebaseAuth.getInstance();


        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        final EditText Address = (EditText) findViewById(R.id.address);
        final EditText CardNumber = (EditText) findViewById(R.id.cardNumber);
        final EditText CardDate = (EditText) findViewById(R.id.cardDate);

        Button Save = (Button) findViewById(R.id.saveDetails);


        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = Address.getText().toString();
                String cardNumber = CardNumber.getText().toString();
                String cardDate = CardDate.getText().toString();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("User Information").child("Customer Information: " + FirebaseAuth.getInstance().getUid());
                myRef.setValue(address + cardNumber + cardDate);
                startActivity(new Intent(UserDetails.this, MainActivity.class));
            }
        });

    }



}

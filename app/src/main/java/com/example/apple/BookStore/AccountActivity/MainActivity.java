package com.example.apple.BookStore.AccountActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.apple.BookStore.AccountActivity.UserApplication.UserAccount;
import com.example.apple.BookStore.AccountActivity.UserApplication.UserDetails;
import com.example.apple.BookStore.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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

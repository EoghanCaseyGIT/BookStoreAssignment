package com.example.apple.BookStore.AccountActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.apple.BookStore.AccountActivity.BookClasses.Book;
import com.example.apple.BookStore.AccountActivity.BookClasses.BookIndexed;
import com.example.apple.BookStore.AccountActivity.BookClasses.SearchBook;
import com.example.apple.BookStore.AccountActivity.UserApplication.UserAccount;
import com.example.apple.BookStore.AccountActivity.UserApplication.UserDetails;
import com.example.apple.BookStore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private CustomListAdapter adapter;
    private List<Book> bookModelList = new ArrayList<Book>();
    private ListView list;

    private Button search;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = (Button) findViewById(R.id.goToSearchPage);

        list = (ListView) findViewById(R.id.bookListView);
        adapter = new CustomListAdapter(MainActivity.this, bookModelList);

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://bookstore-30213.firebaseio.com/All_Books");

        final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(this, R.layout.listview_layout);




        populateList();

        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                startActivity(new Intent(MainActivity.this, SearchBook.class));

            }
        });
    }


    public void populateList() {

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference allBooksRef = rootRef.child("All_Books");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Book book = ds.getValue(Book.class);

                    bookModelList.add(book);

                    list.setAdapter(adapter);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        allBooksRef.addListenerForSingleValueEvent(valueEventListener);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, BookIndexed.class);
                i.putExtra("ValueKey", bookModelList.get(position).getTitle());
                i.putExtra("ValueKey2", bookModelList.get(position).getAuthor());
                i.putExtra("ValueKey3", bookModelList.get(position).getPrice());
                i.putExtra("ValueKey4", bookModelList.get(position).getCategory());
                i.putExtra("ValueKey5", bookModelList.get(position).getStock());
                i.putExtra("ValueKey6", bookModelList.get(position).getInfo());
                i.putExtra("ValueKey7", bookModelList.get(position).getImageURL());

                startActivity(i);


            }
        });
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

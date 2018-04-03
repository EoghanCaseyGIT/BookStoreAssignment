package com.example.apple.BookStore.AccountActivity.BookClasses;

import android.widget.EditText;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by eoghancasey on 03/04/2018.
 */

public class CommentModel {


    String comment;
    String bookID;
    String user;

    public CommentModel(){}

    public CommentModel(String comment, String bookID, String user){
        this.comment = comment;
        this.bookID = bookID;
        this.user = user;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

}

package com.example.apple.BookStore.AccountActivity.BookClasses;

import android.widget.EditText;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by eoghancasey on 03/04/2018.
 */

public class CommentModel {


    String comment;


    public CommentModel(){}

    public CommentModel(String comment){
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

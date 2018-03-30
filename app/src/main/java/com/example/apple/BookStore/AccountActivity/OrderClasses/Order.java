package com.example.apple.BookStore.AccountActivity.OrderClasses;

import com.example.apple.BookStore.AccountActivity.BookClasses.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eoghancasey on 28/03/2018.
 */

public class Order {

    String userID;
    List<Book> orderedBooksList = new ArrayList<Book>();

    public Order(){

    }

    public Order(String userID, List orderedBooksList){
        this.userID = userID;
        this.orderedBooksList = orderedBooksList;
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public List<Book> getOrderedBooksList() {
        return orderedBooksList;
    }

    public void setOrderedBooksList(List<Book> orderedBooksList) {
        this.orderedBooksList = orderedBooksList;
    }
}

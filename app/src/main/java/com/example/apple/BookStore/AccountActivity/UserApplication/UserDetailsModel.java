package com.example.apple.BookStore.AccountActivity.UserApplication;


import com.google.firebase.auth.FirebaseUser;

/**
 * Created by eoghancasey on 25/03/2018.
 */

public class UserDetailsModel {

    String userID;

    String address;
    String cardNumber;
    String cardDate;


    public UserDetailsModel(){

    }


    public UserDetailsModel(String userID, String address, String cardNumber, String cardDate){

        this.userID = userID;
        this.address = address;
        this.cardNumber = cardNumber;
        this.cardDate = cardDate;
    }

    public String getUserID() {
        return userID;
    }

    public String getAddress() {
        return address;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardDate() {
        return cardDate;
    }
}

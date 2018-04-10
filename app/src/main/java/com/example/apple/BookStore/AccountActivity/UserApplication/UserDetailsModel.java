package com.example.apple.BookStore.AccountActivity.UserApplication;


import com.google.firebase.auth.FirebaseUser;

/**
 * Created by eoghancasey on 25/03/2018.
 */

public class UserDetailsModel {

    String userID;

    String username;

    String address;
    String cardNumber;
    String cardDate;


    public UserDetailsModel(){

    }


    public UserDetailsModel(String userID, String username, String address, String cardNumber, String cardDate){

        this.username = username;
        this.userID = userID;
        this.address = address;
        this.cardNumber = cardNumber;
        this.cardDate = cardDate;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCardDate(String cardDate) {
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

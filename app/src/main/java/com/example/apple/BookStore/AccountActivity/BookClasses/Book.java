package com.example.apple.BookStore.AccountActivity.BookClasses;

/**
 * Created by eoghancasey on 20/03/2018.
 */

public class Book {

    String imageURL;
    String title;
    String author;
    String price;
    String category;
    String stock;
    String info;

    public Book(){

    }


    public Book(String imageURL, String title, String author, String price, String category, String stock, String info){

        this.imageURL = imageURL;
        this.title = title;
        this.author = author;
        this.price = price;
        this.category = category;
        this.stock = stock;
        this.info = info;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getStock() {
        return stock;
    }

    public String getInfo() {
        return info;
    }
}

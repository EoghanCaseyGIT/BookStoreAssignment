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

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

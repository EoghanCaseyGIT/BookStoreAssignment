package com.example.apple.BookStore.AccountActivity.OrderClasses;

import com.example.apple.BookStore.AccountActivity.BookClasses.Book;

import java.io.Serializable;
import java.util.List;

/**
 * Created by eoghancasey on 28/03/2018.
 */
public class Order implements Serializable{

    private static final long serialVersionUID = 1L;
    private String total;
    private List<Book> bookList;
    public Order(){
    }


    public Order(List<Book> bookList) {
        this.bookList = bookList;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}






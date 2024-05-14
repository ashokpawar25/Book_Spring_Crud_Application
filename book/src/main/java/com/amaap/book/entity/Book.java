package com.amaap.book.entity;

import org.springframework.data.annotation.Id;

public class Book {
    @Id
    private String name;
    private String author;
    private String publication;
    private int price;
    public Book(String name, String author, String publication, int price) {
        this.name = name;
        this.author = author;
        this.publication = publication;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}

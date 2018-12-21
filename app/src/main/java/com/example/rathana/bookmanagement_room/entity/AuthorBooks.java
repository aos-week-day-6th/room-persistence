package com.example.rathana.bookmanagement_room.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import com.example.rathana.bookmanagement_room.entity.Author;
import com.example.rathana.bookmanagement_room.entity.Book;

import java.util.List;

public class AuthorBooks {

    @Embedded
    public Author author;
    @Relation(parentColumn = "id",entityColumn = "author_id")
    public List<Book> books;

    @Override
    public String toString() {
        return "AuthorBooks{" +
                "author=" + author +
                ", books=" + books +
                '}';
    }
}

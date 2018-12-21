package com.example.rathana.bookmanagement_room.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.rathana.bookmanagement_room.entity.Book;

import java.util.List;

@Dao
public interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(Book ... books);

    @Query("select * from book order by id asc")
    List<Book> getBooks();

    @Delete
    void delete(Book book);
}

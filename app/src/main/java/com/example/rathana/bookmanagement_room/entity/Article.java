package com.example.rathana.bookmanagement_room.entity;

import java.util.List;

public class Article {

    String CODE;
    String MESSAGE;
    List<Data> DATA;

    Pagination PAGINATION;

    class Data{
        int ID;
        String TITLE;
        String DESCRIPTION;

    }

    class  Pagination{

    }
}

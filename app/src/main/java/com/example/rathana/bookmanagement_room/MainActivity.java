package com.example.rathana.bookmanagement_room;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rathana.bookmanagement_room.adapter.BookAdapter;
import com.example.rathana.bookmanagement_room.entity.AuthorBooks;
import com.example.rathana.bookmanagement_room.data.dao.AuthorDao;
import com.example.rathana.bookmanagement_room.data.dao.BookDao;
import com.example.rathana.bookmanagement_room.data.database.AuthorDatabase;
import com.example.rathana.bookmanagement_room.entity.Author;
import com.example.rathana.bookmanagement_room.entity.Book;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
implements BookAdapter.ItemClickCallback {

    FloatingActionButton btnCreateAuthor,btnCreateBook;
    private Author newAuthor;
    private Book book;
    AuthorDatabase database;
    AuthorDao authorDao;
    BookDao bookDao;

    EditText authorId;
    TextView bookAuthorName;
    List<Author> authorList;

    //recyclerView
    RecyclerView recyclerView;
    BookAdapter adapter;
    List<Book> books=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreateAuthor =findViewById(R.id.createAuthor);
        btnCreateBook=findViewById(R.id.createBook);
        //create room database
        database=AuthorDatabase.getAuthorDatabase(this);
        authorDao=database.authorDao();
        bookDao=database.bookDao();

        // get all authors
        authorList=authorDao.getAuthors();

        //setup book recycler View
        setupBookRecyclerView();
        getBooks();
        btnCreateAuthor.setOnClickListener(v->{
            createAuthorDialog();
        });

        btnCreateBook.setOnClickListener(v->{
            createBookDialog();
        });

        //get Author books
        List<AuthorBooks> authorBooks= authorDao.getAuthorBooks();
        Log.e("Test", "onCreate: "+authorBooks.toString() );

    }

    private void getBooks() {
        List<Book> books=bookDao.getBooks();
        List<AuthorBooks> authorBooks =authorDao.getAuthorBooks();
        adapter.setBooks(books);
        adapter.setAuthorBooks(authorBooks);
        Log.e("tag", "getBooks: "+this.books.toString() );
    }

    private void setupBookRecyclerView() {
        recyclerView=findViewById(R.id.bookRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new BookAdapter(books,this);
        recyclerView.setAdapter(adapter);


    }

    //create Book dialog
    private void createBookDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Add new Author");

        //body
        View view=LayoutInflater.from(this).inflate(
                R.layout.add_book_dialog_layout,null
        );
        builder.setView(view);
        //create ui control objects
        EditText bookName=view.findViewById(R.id.title);
        EditText bookPage=view.findViewById(R.id.totalPage);
        EditText bookDate=view.findViewById(R.id.dateCreate);
        EditText bookDesc=view.findViewById(R.id.desc);
        EditText bookThumb=view.findViewById(R.id.thumb);
        authorId=view.findViewById(R.id.authorId);
        bookAuthorName=view.findViewById(R.id.author);
        book=new Book();
        //get Author
        setupAuthorDropdown();
        builder.setCancelable(false);
        //footer
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                book.title=bookName.getText().toString();
                book.page=Integer.parseInt(bookPage.getText().toString());
                book.dateCreated=bookDate.getText().toString();
                book.thumb=bookThumb.getText().toString() ;
                book.desc=bookDesc.getText().toString();
                book.authorId=Integer.parseInt(authorId.getText().toString());
                AuthorBooks authorBooks=new AuthorBooks();
                Author author=new Author();
                author.name=bookAuthorName.getText().toString();
                author.id=Integer.parseInt(authorId.getText().toString());
                authorBooks.author=author;
                //add new book to book adapter

                List<AuthorBooks> authorBooksList=new ArrayList<>();
                authorBooksList.add(authorBooks);
                adapter.setAuthorBooks(authorBooksList);
                adapter.setBook(book);
                //save book to database
                bookDao.add(book);


                dialog.dismiss();
                Log.e("MainActivity", "onClick: "+book.toString() );
                Toast.makeText(MainActivity.this, "save success", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    //setup Author for book Didalog
    private void setupAuthorDropdown() {
        //create authors array of String

        CharSequence[] authorNames=new CharSequence[authorList.size()];
        for(int i=0;i<authorList.size();i++)
            authorNames[i]=authorList.get(i).name;

        bookAuthorName.setOnClickListener(v->{
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setItems(authorNames, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    bookAuthorName.setText(authorList.get(which).name);
                    authorId.setText(authorList.get(which).id+"");
                    dialog.dismiss();
                }
            });
            builder.show();
        });

    }

    private void createAuthorDialog(){

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Add new Author");

        //body
        View view=LayoutInflater.from(this).inflate(
                R.layout.add_author_dialog_layout,null
        );
        builder.setView(view);
        //create ui control objects
        EditText authorName=view.findViewById(R.id.name);
        EditText authorAge=view.findViewById(R.id.age);
        RadioGroup rdGroup=view.findViewById(R.id.rdGroup);
        newAuthor=new Author();
        //get data from radio button
        rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rdButton= group.findViewById(checkedId);

                if(rdButton.isChecked())
                    newAuthor.gender=rdButton.getText().toString();
                    //Toast.makeText(MainActivity.this,rdButton.getText().toString()+" checked",Toast.LENGTH_SHORT).show();

            }
        });


        builder.setCancelable(false);
        //footer
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                newAuthor.name=authorName.getText().toString();
                newAuthor.age=Integer.parseInt(authorAge.getText().toString());
                //insert author into table
                authorDao.add(newAuthor);
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "save success", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case  R.id.author:
                startActivity(new Intent(this,AuthorActivity.class));
                return true;

            default : return  false;
        }
    }

    @Override
    public void onItemClicked(Book book,int pos) {
        //delete data
        // delete adapter
        this.books.remove(book);
        adapter.notifyItemRemoved(pos);
        //delete from table
        bookDao.delete(book);
    }
}

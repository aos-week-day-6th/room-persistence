package com.example.rathana.bookmanagement_room;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.rathana.bookmanagement_room.entity.Author;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton btnCreateAuthor;
    private Author newAuthor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreateAuthor =findViewById(R.id.createAuthor);


        btnCreateAuthor.setOnClickListener(v->{
            createAuthorDialog();
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

        builder.setCancelable(false);
        //footer
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newAuthor=new Author();

                dialog.dismiss();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

}

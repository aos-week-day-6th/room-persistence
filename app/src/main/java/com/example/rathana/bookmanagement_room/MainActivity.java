package com.example.rathana.bookmanagement_room;

import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.rathana.bookmanagement_room.data.dao.AuthorDao;
import com.example.rathana.bookmanagement_room.data.database.AuthorDatabase;
import com.example.rathana.bookmanagement_room.entity.Author;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton btnCreateAuthor;
    private Author newAuthor;
    AuthorDatabase database;
    AuthorDao authorDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreateAuthor =findViewById(R.id.createAuthor);
        //create room database
        database=AuthorDatabase.getAuthorDatabase(this);
        authorDao=database.authorDao();

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
}

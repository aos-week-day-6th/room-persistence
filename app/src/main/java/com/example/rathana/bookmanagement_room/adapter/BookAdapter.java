package com.example.rathana.bookmanagement_room.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rathana.bookmanagement_room.R;
import com.example.rathana.bookmanagement_room.entity.AuthorBooks;
import com.example.rathana.bookmanagement_room.entity.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {


    List<Book>  books;
    Context mContext;
    List<AuthorBooks> authorBooks=new ArrayList<>();
    public BookAdapter(List<Book> books, Context mContext) {
        this.books = books;
        this.mContext = mContext;
        callback= (ItemClickCallback) mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.book_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Book book= books.get(i);

        for(AuthorBooks authorBooks: authorBooks){
            if(authorBooks.author.id==book.authorId){
                viewHolder.authorName.setText(authorBooks.author.name);
                break;
            }
        }

        viewHolder.title.setText(book.title);
        viewHolder.date.setText(book.dateCreated);

        viewHolder.totalPage.setText(book.page+"");
        Picasso.get().load(book.thumb)
                .resize(120,150)
                .centerCrop()
                .into(viewHolder.thumb);

        viewHolder.setBtnDeleteClicked();
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void setBooks(List<Book> books) {
        this.books.addAll(books);
        notifyDataSetChanged();
    }

    public void setBook(Book book) {
        this.books.add(book);
        notifyItemInserted(this.books.size()-1);
    }

    public void setAuthorBooks(List<AuthorBooks> authorBooks) {
        this.authorBooks.addAll(authorBooks);
    }

    class  ViewHolder extends RecyclerView.ViewHolder{

        TextView title, totalPage,authorName,date;
        ImageView thumb,btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            totalPage=itemView.findViewById(R.id.totalPage);
            authorName=itemView.findViewById(R.id.authorName);
            date=itemView.findViewById(R.id.dateCreate);
            thumb=itemView.findViewById(R.id.thumb);
            btnDelete=itemView.findViewById(R.id.btbDelete);
        }

        public void setBtnDeleteClicked(){
            btnDelete.setOnClickListener(v->{
                callback.onItemClicked(books.get(getAdapterPosition()),getAdapterPosition());
            });
        }

    }

    ItemClickCallback callback;
    public interface ItemClickCallback{
        void onItemClicked(Book book,int pos);
    }
}

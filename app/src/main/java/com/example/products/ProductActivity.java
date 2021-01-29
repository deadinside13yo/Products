package com.example.products;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

public class ProductActivity extends AppCompatActivity {

    DataSaver dataSaver;
    Produkt product;
    ActivityBookBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_product);
        dataSaver = DataSaver.getInstance(this);

        dataSaver.getProducts().observe(this, new Observer<List<Produkt>>() {
            @Override
            public void onChanged(List<Book> books) {

                product = dataSaver.getProducts().getValue().get(getIntent().getIntExtra(POSITION,0));
                binding.setBook(book);

//                bookPhoto.setImageResource(book.getImgRes());
//                bookTitle.setText(book.getTitle());
//                bookDescription.setText(book.getDescription());
            }
        });

    }


    public void goToEitBookAction(View view) {
        Intent intent = new Intent(BookActivity.this,EditActivity.class);
        intent.putExtra(POSITION,dataSaver.getBooks().getValue().indexOf(book));
        startActivity(intent);
    }
}
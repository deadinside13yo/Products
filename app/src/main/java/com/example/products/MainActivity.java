package com.example.products;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";
    Button btnAdd, btnRead, btnClear;
    EditText etProductName, etCount, etProductType;

    DataAdapter adapter;
 DataSaver dataSaver;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        dataSaver = DataSaver.getInstance(this);
        etProductName =    findViewById(R.id.etProductName);
        etCount =   findViewById(R.id.etCount);
        etProductType = findViewById(R.id.etProductType);
        adapter = new DataAdapter(this, dataSaver.getProducts());
        RecyclerView recyclerView = findViewById(R.id.productList);
        recyclerView.setAdapter(adapter);

    }

    public void onClick(View v) {


        // получаем данные из полей ввода


        String product = etProductName.getText().toString();
        String count = etCount.getText().toString();
        String type = etProductType.getText().toString();


        switch (v.getId()) {
            case R.id.btnAdd:
                dataSaver.AddProduct(product, count, type);
                break;
            case R.id.btnRead:
                dataSaver.readDB();
                break;
            case R.id.btnClear:
                // удаляем все записи
                int clearCount = dataSaver.delete();
                break;
        }

        adapter.notifyDataSetChanged();
        // закрываем подключение к БД
    }






}
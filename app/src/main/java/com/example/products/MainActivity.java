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
    final List<Produkt> produkts = new ArrayList<>();
    Button btnAdd, btnRead, btnClear;
    EditText etProductName, etCount, etProductType;

    DataAdapter adapter;

    DBHelper dbHelper;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etProductName =    findViewById(R.id.etProductName);
        etCount =   findViewById(R.id.etCount);
        etProductType = findViewById(R.id.etProductType);
        adapter = new DataAdapter(this, produkts);

        RecyclerView recyclerView = findViewById(R.id.productList);
        recyclerView.setAdapter(adapter);

        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);
    }

    public void onClick(View v) {

        // создаем объект для данных
        ContentValues cv = new ContentValues();

        // получаем данные из полей ввода
        String product = etProductName.getText().toString();
        String count = etCount.getText().toString();
        String type = etProductType.getText().toString();


        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (v.getId()) {
            case R.id.btnAdd:
                Log.d(LOG_TAG, "--- Insert in mytable: ---");
                // подготовим данные для вставки в виде пар: наименование столбца - значение

                cv.put("product", product);
                cv.put("count", count);
                cv.put("type", type);
                // вставляем запись и получаем ее ID
                long rowID = db.insert("mytable", null, cv);
                Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                readBd(db);
                break;
            case R.id.btnRead:
                readBd(db);
                break;
            case R.id.btnClear:
                Log.d(LOG_TAG, "--- Clear mytable: ---");
                // удаляем все записи
                int clearCount = db.delete("mytable", null, null);
                Log.d(LOG_TAG, "deleted rows count = " + clearCount);
                produkts.clear();
                adapter.notifyDataSetChanged();
                break;
        }
        // закрываем подключение к БД
        dbHelper.close();
    }

    private void readBd(SQLiteDatabase db){
       produkts.clear();
        Log.d(LOG_TAG, "--- Rows in mytable: ---");
        // делаем запрос всех данных из таблицы mytable, получаем Cursor
        Cursor c = db.query("mytable", null, null, null, null, null, null);

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int productColIndex = c.getColumnIndex("product");
            int countColIndex = c.getColumnIndex("count");
            int typeColIndex = c.getColumnIndex("type");

            do {
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d(LOG_TAG,
                        "ID = " + c.getInt(idColIndex) +
                                ", product = " + c.getString(productColIndex) +
                                ", count = " + c.getString(countColIndex) +
                                ", type = " + c.getString(typeColIndex));
                produkts.add(new Produkt(c.getString(productColIndex), c.getString(typeColIndex), c.getString(countColIndex)));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
            adapter.notifyDataSetChanged();
        } else
            Log.d(LOG_TAG, "0 rows");
        c.close();
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            // конструктор суперкласса
            super(context, "myDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- onCreate database ---");
            // создаем таблицу с полями
            db.execSQL("create table mytable ("
                    + "id integer primary key autoincrement,"
                    + "product text,"
                    + "count text,"
                    + "type text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }


}
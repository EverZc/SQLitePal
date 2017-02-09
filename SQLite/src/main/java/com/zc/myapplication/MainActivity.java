package com.zc.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private  MyDatabaseHelper dbHelper;
    private Button button;
    private Button insertBtn;
    private Button update;
    private Button delete;
    private Button query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button= (Button) findViewById(R.id.creatSQL);
        dbHelper=new MyDatabaseHelper(this,"BookStore.db",null,5);

        //创建表
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });
        insertBtn= (Button) findViewById(R.id.insert);

        //添加数据
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //首先获取对象
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                //ContentValues对要添加的数据进行组装
                ContentValues values=new ContentValues();
                //开始组装第一条数据
                values.put("name","第一行代码");
                values.put("author","郭霖");
                values.put("pages",520);
                values.put("price",100);
                db.insert("Book",null,values);
                values.clear();
                //组装第二条数据
                values.put("name","群英传");
                values.put("author","徐医生");
                values.put("pages",250);
                values.put("price",100);
                db.insert("Book",null,values);
                Log.e("-----", "onClick:" );
                //SQL查询语句 select * form Book
            }
        });
        update= (Button) findViewById(R.id.update_data);

        //更新数据
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("price",10.99);
                db.update("Book",values,"name=?",new String[]{"群英传"});
            }
        });
        delete= (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                //后两个参数为约束条件
                db.delete("Book","pages > ?",new String[]{"500"});

            }
        });
        query= (Button) findViewById(R.id.query_data);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                //查询Book表中的所有数据
                Cursor cursor=db.query("Book",null,null,null,null,null,null);
                //将数据的指针移动到第一行的位置
                if (cursor.moveToFirst()){
                    do {
                        //遍历Cursor对象,取出并打印
                        String name=cursor.getString(cursor.getColumnIndex("name"));
                        String author=cursor.getString(cursor.getColumnIndex("author"));
                        int pages=cursor.getInt(cursor.getColumnIndex("pages"));
                        double price=cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.e("----",name);
                        Log.e("----",author);
                        Log.e("----", String.valueOf(pages));
                        Log.e("----", String.valueOf(price));
                    }while (cursor.moveToNext());
                }
                cursor.close();
            }
        });

    }
}

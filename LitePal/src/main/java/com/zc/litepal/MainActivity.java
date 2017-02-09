package com.zc.litepal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button creatTable;
    private Button insert;
    private Button update;
    private Button delete;
    private Button query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lite_pal);
        creatTable= (Button) findViewById(R.id.creatSQL);
        creatTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connector.getDatabase();
            }
        });
        insert= (Button) findViewById(R.id.insert);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book=new Book();
                book.setName("西游记");
                book.setAuthor("吴承恩");
                book.setPages(456);
                book.setPrice(16.96);
                book.setPress("未知");

                book.save();

            }
        });
        update= (Button) findViewById(R.id.update_data);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Book book=new Book();
                book.setName("钢铁是怎样练成的");
                book.setAuthor("奥斯托洛夫斯基");
                book.setPages(510);
                book.setPrice(19.95);
                book.setPress("未知");
                book.save();
                book.setPrice(1000);
                book.save();*/
                Book book=new Book();
                book.setPrice(19.666);
                book.setPress("中华出版社");
                //约束条件 类似于where
                book.updateAll("name=? and author=?","钢铁是怎样练成的","奥斯托洛夫斯基");
            }
        });
        delete= (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupport.deleteAll(Book.class,"price<?","18");
            }
        });
        query= (Button) findViewById(R.id.query_data);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*List<Book> books=DataSupport.findAll(Book.class);
                for (Book book:books){
                    Log.d("------",book.getName());
                    Log.d("------",book.getAuthor());
                    Log.d("------",book.getPress());
                    Log.d("------", String.valueOf(book.getPrice()));
                    Log.d("------", String.valueOf(book.getPages()));

                }*/
                //查询表第一个数据
                Book first=DataSupport.findFirst(Book.class);
                //查询表最后一个数据
                Book last=DataSupport.findLast(Book.class);
                Log.e("------", first.toString());
                Log.e("------", last.toString());

                //查询哪几列的数据,对应SQL关键字select
                List<Book> books=DataSupport.select("name","author").find(Book.class);
                //where方法用于指定查询的约束条件 对应SQL关键字where
                List<Book> books1=DataSupport.where("pages>?","400").find(Book.class);
                //order 用于指定结果的排序方式  desc表示降序  asc或者默认表示升序
                List<Book> books2=DataSupport.order("price desc").find(Book.class);
                //limit 方法用于指定查询结果的数量 例如如下查询3条
                List<Book> books3=DataSupport.limit(3).find(Book.class);
                //offset()用于指定查询结果的偏移量,比如查询第2 3 4条数据
                List<Book> books4=DataSupport.limit(3).offset(1).find(Book.class);

                //任意组合一个
                List<Book> books5=DataSupport.select("name","pages")
                                             .where("pages>?","400")
                                             .order("pages")
                                             .limit(10)
                                             .offset(10)
                                             .find(Book.class);

            }
        });
    }
}

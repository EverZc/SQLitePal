package com.zc.filesave;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SharedPreferencesActivity extends AppCompatActivity {
    private Button button,buttonread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);
        button= (Button) findViewById(R.id.sp_write);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sharedPreference 存储数据

                //指定的文件名为dadadata
                SharedPreferences.Editor editor=
                        getSharedPreferences("dadadata",MODE_PRIVATE).edit();
                editor.putBoolean("abc",true);
                editor.putString("string","为什么呢");
                //最后调用apply提交
                editor.apply();
            }
        });
        buttonread= (Button) findViewById(R.id.sp_read);
        buttonread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sharedPreference 读取数据
                SharedPreferences preferences=
                        getSharedPreferences("dadadata",MODE_PRIVATE);
                String name=preferences.getString("string","");
                boolean boo=preferences.getBoolean("abc",false);
                Log.d("-----",name);
                Log.d("-----", String.valueOf(boo));
            }
        });

    }
}

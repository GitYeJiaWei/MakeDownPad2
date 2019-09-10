#数据保存到本地TXT文件

```
package com.example.savedatademo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button saveBtn = null;
    private Button readBtn = null;
    private EditText input = null;
    private final static String FILE_NAME = "xth.txt"; // 设置文件的名称

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        input = (EditText) findViewById(R.id.input);
        saveBtn = (Button) findViewById(R.id.btn1);
        readBtn = (Button) findViewById(R.id.btn2);

        saveBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = input.getText().toString();
                save(data);
                Toast.makeText(MainActivity.this, "保存数据成功！", Toast.LENGTH_SHORT)
                        .show();
            }
        });
        readBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer sb = read();
                Toast.makeText(MainActivity.this, "读取数据为：" + sb,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private StringBuffer read() {
        FileInputStream in = null;
        Scanner s = null;
        StringBuffer sb = new StringBuffer();
        try {
            in = super.openFileInput(FILE_NAME);
            s = new Scanner(in);
            while (s.hasNext()) {
                sb.append(s.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb;
    }

    private void save(String data) {
        FileOutputStream out = null;
        PrintStream ps = null;
        try {
            out = super.openFileOutput(FILE_NAME, Activity.MODE_APPEND);
            ps = new PrintStream(out);
            ps.println(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                    ps.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
```
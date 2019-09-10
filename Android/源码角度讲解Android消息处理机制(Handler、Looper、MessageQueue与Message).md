#源码角度讲解Android消息处理机制(Handler、Looper、MessageQueue与Message)

##前言
上篇文章介绍了几种hanlder创建方式，其实这种使用方式大家都知道，但是为什么可以这么做，可能很多人不知道，至少不清楚，网上很多文章也是到处粘贴，听别说handler把Message发送到MessageQueue里面去，Looper通过死循环，不断从MessageQueue里面获取Message处理消息，因为Mesage.target就是当前hanlder，所以最后转到handleMessage（）方法中去处理，整个流程是这样。其实大概都是对的，之前面试的时候，我也都是这么说，也没有面试官深入问过，这次正好有时间深入源码系统学习下，毕竟还是要知其所以然。

##使用方法
```
package com.example.test.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == 1) {
                Toast.makeText(MainActivity.this, "hanlder1", Toast.LENGTH_SHORT).show();
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = handler1.obtainMessage();
                message.arg1 = 1;
                handler1.sendMessage(message);
            }
        }).start();
    }
}
```

##源码讲解
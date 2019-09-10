#Message创建方式：new Message和obtainMessage的区别

##前言
创建Message对象的时候，有三种方式，分别为： 
1.Message msg = new Message(); 
2.Message msg2 = Message.obtain(); 
3.Message msg1 = handler1.obtainMessage(); 
这三种方式有什么区别呢？

##使用方式
```
public class MainActivity extends Activity {

    private Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(MainActivity.this, "hanlder1", Toast.LENGTH_SHORT).show();
            super.handleMessage(msg);
        }
    };
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
//                Message msg = new Message();
//                Message msg2 = Message.obtain();
                Message msg1 = handler1.obtainMessage();
                msg1.arg1 = 1;
                handler1.sendMessage(msg1);
            }
        }).start();
    }
}
```

##区别
1）Message msg = new Message();这种就是直接初始化一个Message对象，没有什么特别的。 
2）Message msg2 = Message.obtain();
```
/**
     * Return a new Message instance from the global pool. Allows us to
     * avoid allocating new objects in many cases.
     */
    public static Message obtain() {
        synchronized (sPoolSync) {//同步锁
            if (sPool != null) {
                Message m = sPool;
                sPool = m.next;
                m.next = null;
                m.flags = 0; // clear in-use flag
                sPoolSize--;
                return m;
            }
        }
        return new Message();
    }
```
从注释可以得知，从整个Messge池中返回一个新的Message实例，通过obtainMessage能避免重复Message创建对象。 
3）Message msg1 = handler1.obtainMessage();
```
public final Message obtainMessage()
    {
        return Message.obtain(this);
    }


public static Message obtain(Handler h) {
        Message m = obtain();
        m.target = h;

        return m;
    }


public static Message obtain() {
        synchronized (sPoolSync) {
            if (sPool != null) {
                Message m = sPool;
                sPool = m.next;
                m.next = null;
                m.flags = 0; // clear in-use flag
                sPoolSize--;
                return m;
            }
        }
        return new Message();
    }

```

可以看到，第二种跟第三种其实是一样的，都可以避免重复创建Message对象，所以建议用第二种或者第三种任何一个创建Message对象。
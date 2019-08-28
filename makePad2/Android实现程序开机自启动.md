#Android实现程序开机自启动
- 在安卓中，想要实现app开机自动启动，需要实现拦截广播android.permission.RECEIVE_BOOT_COMPLETED，并且需要使用静态注册广播的方法（即在AndroidManifest.xml文件中定义广播）

1.. **先在AndroidManifest.xml文件中定义广播和声明权限**

```java
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"></uses-permission>

<receiver
            android:name=".MyReceiver"
            android:enabled="true"
            android:exported="true">
            <intent-filter android:priority="1000">
                <action android:name="android.intent.action.BOOT_COMPLETED"></action>
            </intent-filter>
</receiver>

 

上面的MyReceiver是我自己写的类，继承了BroadcastReceiver的，代码如下：

public class MyReceiver extends BroadcastReceiver
{
    public MyReceiver()
    {
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}
上面的 i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 非常重要，如果缺少的话，程序将在启动时报错
```

2.. **以下是MainActivity类的代码**

```java
public class MainActivity extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "哈哈，我成功启动了！", Toast.LENGTH_LONG).show();
        Log.e("AutoRun","哈哈，我成功启动了！");
    }
}
```

3.. **安装app到手机上，然后启动一次程序（据说安卓4.0以后，必须先启动一次程序才能接收到开机完成的广播，目的是防止恶意程序）**

4.. **重启手机，测试app有没有自动启动。如果有，那么恭喜你。如果没有，请往下看。**

  如果按照上面的全部步骤后操作后，重启没有自动启动程序，怎么办呢？是怎么回事呢？那么首先请检查一下你的手机是不是安装了360之类的安全软件，如果有，请在软件的自启动软件管理中将app设置为【允许】（我的手机没有安装这些软件，但是系统设置里面自带了自启动软件管理的功能 ，所以我在这里将我的app设置为【允许开机启动】），重启手机，测试是否成功。如果还是失败，那么请检查你的手机是不是设置了app安装首选位置是sd卡，据说安装到sd卡的话，因为手机启动成功后（发送了启动完成的广播后）才加载sd卡，所以app接收不到广播。如果是的话，把app安装到内部存储试试。如果不懂得设置的话，那么直接在AndroidManifest.xml文件中设置安装路径，android:installLocation="internalOnly"。比如：
```java


<manifest
    package="cn.weixq.autorun"
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:installLocation="internalOnly">
```